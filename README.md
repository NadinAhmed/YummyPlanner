# Yummy Planner

Yummy Planner is a beautiful and user-friendly Android application for meal planning and recipe discovery. It helps users find new recipes, plan their meals for the week, and save their favorite dishes.

## Demo Video

<p align="center">
  <a href="https://youtube.com/shorts/mkEddEvElqA">
    <img src="https://github.com/NadinAhmed/YummyPlanner/blob/main/Cover%20Photo.png">
  </a>
</p>

## ✨ Features

-  **Meal of the Day:** Get a daily random meal suggestion to inspire your cooking.
-  **Popular Meals:** Browse a list of popular meals to see what's trending.
-  **Advanced Search:** Search for recipes by name, category, country, or main ingredient.
-  **Recipe Details:** View detailed information for each meal, including a list of ingredients and step-by-step instructions.
-  **Favorites:** Save your favorite meals for quick access later.
-  **Meal Planner:** Plan your meals for the week with an easy-to-use planner.
-  **User Accounts:** Sign in to sync your favorites and meal plans across devices. Guest mode is also available.
-  **Dark Theme:** Enjoy a beautiful dark theme that's easy on the eyes.
-  **Localization:** Available in both languages Arabic and English.

## 🛠️ Tech Stack & Architecture

- **Language:** [Java](https://www.java.com/)
- **Architecture:** Model-View-Presenter (MVP)
- **UI:** Android XML Layouts, Material Components
- **Asynchronous Programming:** RxJava
- **Networking:** [Retrofit](https://square.github.io/retrofit/) for REST API communication.
- **Image Loading:** [Glide](https://github.com/bumptech/glide) for efficient image loading and caching.
- **Database:** [Room](https://developer.android.com/training/data-storage/room) for local data persistence.
- **Authentication:** [Firebase Authentication](https://firebase.google.com/docs/auth) for user management.
- **Animations:** [Lottie](https://lottiefiles.com/) for beautiful, smooth animations.

## 🚀 Getting Started

### Prerequisites

- Android Studio
- Android SDK

### Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/your-username/yummy-planner.git
    ```
2.  Open the project in Android Studio.
3.  Add your own `google-services.json` file from your Firebase project to the `app/` directory.
4.  Build the project using Gradle.

### Building and Running

To build the project, you can use the following command:

```bash
./gradlew build
```

To install the app on a connected device or emulator, run:

```bash
./gradlew installDebug
```

Alternatively, you can build and run the app directly from Android Studio.
