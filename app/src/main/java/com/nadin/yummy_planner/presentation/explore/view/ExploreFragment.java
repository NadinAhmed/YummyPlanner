package com.nadin.yummy_planner.presentation.explore.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentExploreBinding;
import com.nadin.yummy_planner.presentation.explore.presenter.SearchPresenter;
import com.nadin.yummy_planner.presentation.explore.presenter.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ExploreFragment extends Fragment implements SearchView {

    private static final long SEARCH_DEBOUNCE_MILLIS = 400L;
    private static final String TAG = "ExploreFragment";

    private final PublishSubject<SearchRequest> searchSubject = PublishSubject.create();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private FragmentExploreBinding binding;
    private RecyclerView mealsResultRecyclerView;
    private SearchAdapter searchAdapter;
    private SearchPresenter presenter;
    private EditText searchEditText;
    private TextView searchTypeView;
    private MaterialButtonToggleGroup toggleGroup;
    private TextWatcher searchTextWatcher;
    private SearchType currentSearchType = SearchType.MEALS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsResultRecyclerView = binding.searchResultRecycleView;
        mealsResultRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        searchAdapter = new SearchAdapter();
        mealsResultRecyclerView.setAdapter(searchAdapter);
        presenter = new SearchPresenterImpl(this, getContext());

        searchEditText = binding.searchInput;
        searchTypeView = binding.searchingTypeTv;
        toggleGroup = binding.toggleGroup;
        toggleGroup.clearChecked();
        updateSearchTypeView(currentSearchType);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                currentSearchType = SearchType.MEALS;
                updateSearchTypeView(currentSearchType);
                toggleGroup.clearChecked();
                emitSearchQuery();
                return true;
            }
            return false;
        });

        setupSearchHandlers();
    }

    private void showLoadingState() {
        binding.loadingView.setVisibility(View.VISIBLE);
        binding.messageView.setVisibility(View.GONE);
        binding.searchResultRecycleView.setVisibility(View.GONE);
    }

    @Override
    public void displayMeals(List<Meal> meals) {
        binding.loadingView.setVisibility(View.GONE);
        if (meals != null && !meals.isEmpty()) {
            binding.searchResultRecycleView.setVisibility(View.VISIBLE);
            binding.messageView.setVisibility(View.GONE);
            searchAdapter.setMeals(meals);
        } else {
            binding.searchResultRecycleView.setVisibility(View.GONE);
            binding.messageView.setVisibility(View.VISIBLE);
            binding.messageView.setText("No meals found");
            searchAdapter.setMeals(new ArrayList<>());
        }
    }

    @Override
    public void displayError(String message) {
        binding.loadingView.setVisibility(View.GONE);
        binding.searchResultRecycleView.setVisibility(View.GONE);
        binding.messageView.setVisibility(View.VISIBLE);
        binding.messageView.setText(message);
        searchAdapter.setMeals(new ArrayList<>());
    }

    private void setupSearchHandlers() {
        searchTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emitSearchQuery();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        };

        searchEditText.addTextChangedListener(searchTextWatcher);

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            SearchType newSearchType = resolveSearchType(group.getCheckedButtonId());
            if (newSearchType != currentSearchType) {
                currentSearchType = newSearchType;
                updateSearchTypeView(currentSearchType);
                emitSearchQuery();
            }
        });

        disposables.add(
                searchSubject
                        .debounce(SEARCH_DEBOUNCE_MILLIS, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleSearchRequest, throwable -> Log.e(TAG, "Search stream error", throwable))
        );
    }

    private void emitSearchQuery() {
        if (searchEditText == null) {
            return;
        }
        CharSequence text = searchEditText.getText();
        String query = text != null ? text.toString() : "";
        searchSubject.onNext(new SearchRequest(query, currentSearchType));
    }

    private void handleSearchRequest(SearchRequest request) {
        String trimmedQuery = request.query.trim();
        if (trimmedQuery.isEmpty()) {
            clearListState();
            return;
        }
        updateSearchTypeView(request.type);
        showLoadingState();
        request.type.search(presenter, trimmedQuery);
    }

    private void updateSearchTypeView(SearchType type) {
        if (searchTypeView != null) {
            searchTypeView.setText(getString(type.getLabelRes()));
        }
    }

    private SearchType resolveSearchType(int checkedId) {
        if (checkedId == binding.categoriesButton.getId()) {
            return SearchType.CATEGORIES;
        }
        if (checkedId == binding.countriesButton.getId()) {
            return SearchType.COUNTRIES;
        }
        if (checkedId == binding.ingredientsButton.getId()) {
            return SearchType.INGREDIENTS;
        }
        return SearchType.MEALS;
    }

    private void clearListState() {
        binding.loadingView.setVisibility(View.GONE);
        binding.searchResultRecycleView.setVisibility(View.GONE);
        binding.messageView.setVisibility(View.GONE);
        searchAdapter.setMeals(new ArrayList<>());
    }

    @Override
    public void onDestroyView() {
        if (searchEditText != null && searchTextWatcher != null) {
            searchEditText.removeTextChangedListener(searchTextWatcher);
        }
        disposables.clear();
        super.onDestroyView();
    }

    private static final class SearchRequest {
        private final String query;
        private final SearchType type;

        private SearchRequest(String query, SearchType type) {
            this.query = query != null ? query : "";
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchRequest that = (SearchRequest) o;
            return Objects.equals(query, that.query) && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(query, type);
        }
    }

    private enum SearchType {
        MEALS(R.string.meals) {
            @Override
            void search(SearchPresenter presenter, String query) {
                presenter.searchMealsByName(query);
            }
        },
        CATEGORIES(R.string.categories) {
            @Override
            void search(SearchPresenter presenter, String query) {
                presenter.searchMealsByCategory(query);
            }
        },
        COUNTRIES(R.string.countries) {
            @Override
            void search(SearchPresenter presenter, String query) {
                presenter.searchMealsByCountry(query);
            }
        },
        INGREDIENTS(R.string.ingredients) {
            @Override
            void search(SearchPresenter presenter, String query) {
                presenter.searchMealsByIngredient(query);
            }
        };

        private final int labelRes;

        SearchType(@StringRes int labelRes) {
            this.labelRes = labelRes;
        }

        abstract void search(SearchPresenter presenter, String query);

        @StringRes
        int getLabelRes() {
            return labelRes;
        }
    }
}
