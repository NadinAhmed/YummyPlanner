package com.nadin.yummy_planner.presentation.meal_details.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.databinding.FragmentMealDetailsBinding;
import com.nadin.yummy_planner.presentation.meal_details.presenter.MealDetailsPresenter;
import com.nadin.yummy_planner.presentation.meal_details.presenter.MealDetailsPresenterImpl;
import com.nadin.yummy_planner.utils.SuccessDialog;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import android.app.DatePickerDialog;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private FragmentMealDetailsBinding binding;
    private RecyclerView ingredientsRecyclerView;
    private StepsAdapter stepsAdapter;
    private Meal meal;
    private ImageView mealImageView;
    private TextView mealNameTextView;
    private Chip mealAreaChip;
    private Chip mealCategoryChip;
    private YouTubePlayerView mealYoutubePlayerView;
    private MaterialCardView favCard;
    private SuccessDialog successDialog;
    private MealDetailsPresenter presenter;
    private Button addToPlanButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            meal = (Meal) getArguments().getSerializable("meal");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientsRecyclerView = binding.ingredientsRecyclerView;
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        stepsAdapter = new StepsAdapter();
        binding.stepsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.stepsRecyclerView.setAdapter(stepsAdapter);


        mealImageView = binding.mealImage;
        mealNameTextView = binding.mealName;
        mealAreaChip = binding.chipArea;
        mealCategoryChip = binding.chipCategory;
        mealYoutubePlayerView = binding.youtubeVideo;
        favCard = binding.favoriteButtonContainer;
        addToPlanButton = binding.addToPlanButton;
        successDialog = new SuccessDialog();
        presenter = new MealDetailsPresenterImpl(requireContext(), this);

        if (meal != null) {
            Glide.with(this)
                    .load(meal.getImageUrl())
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(mealImageView);
            mealNameTextView.setText(meal.getName());
            mealAreaChip.setText(meal.getCountry());
            mealCategoryChip.setText(meal.getCategory());

            String instructions = meal.getInstructions();
            if (instructions != null && !instructions.isEmpty()) {
                List<String> steps = new ArrayList<>();
                if (instructions.contains("step 1")) {
                    String[] stepsArray = instructions.split("step \\d+\\r?\\n");
                    for (String step : stepsArray) {
                        String trimmedStep = step.trim();
                        if (!trimmedStep.isEmpty()) {
                            steps.add(trimmedStep.replaceAll("\\r?\\n\\r?\\n", "").replaceFirst("^\\d+\\.\\s*", ""));
                        }
                    }
                } else {
                    String[] stepsArray = instructions.split("\\r?\\n");
                    for (String step : stepsArray) {
                        if (!step.trim().isEmpty()) {
                            steps.add(step.trim().replaceFirst("^\\d+\\.\\s*", ""));
                        }
                    }
                }
                stepsAdapter.setSteps(steps);
            }

            getLifecycle().addObserver(mealYoutubePlayerView);

            mealYoutubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = "S0Q4gqBUs7c";
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });

            favCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.addMealToFav(meal);
                }
            });

            addToPlanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show date picker dialog and get selected date
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        long date = selectedDate.getTimeInMillis();
                        presenter.addMealToPlan(meal, date);
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });

            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(meal.getIngredients());
            ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        }
    }

    @Override
    public void onMealAddToFavSuccess() {
        successDialog.show(requireContext(), getString(R.string.add_to_fav_success_msg));
    }

    @Override
    public void onMealAddToPlanSuccess() {
        successDialog.show(requireContext(), getString(R.string.add_to_plan_success_msg));
    }
}