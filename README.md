# News Android App

Welcome to the News Android App! This app allows users to stay updated with the latest news articles from various sources and categories. It utilizes modern Android development technologies to deliver a seamless user experience.

## Features

- Show news articles categorized by different categories.
- Display news articles from various sources.
- Search for specific news articles using keywords.
- Add news articles to favorites.
- Remove news articles from favorites.

## Technologies Used

- Room: A SQLite database library for local data storage.
- Retrofit: A type-safe HTTP client for making API calls.
- Coroutine: Kotlin's concurrency framework for managing asynchronous operations.
- Jetpack Compose: A modern UI toolkit for building native UIs.
- Dagger: A dependency injection framework for managing dependencies.
- MVVM: Model-View-ViewModel architecture pattern for separating concerns.
- Clean Architecture: A design pattern that promotes separation of concerns and maintainability.
- Unit Testing: Writing unit tests for business logic using JUnit and Mockito.

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on your preferred device or emulator.

## API Integration

The app fetches news articles from the [NewsAPI](https://newsapi.org) API. To make use of the API, you need to provide an API key which you can obtain by signing up on their website.

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern for structuring its components. It ensures a separation of concerns and facilitates testability and maintainability.

### Modules

The app is divided into different modules:

- **app**: The main Android application module.
- **data**: Data-related modules, including data sources and repositories.
- **domain**: Contains the business logic and use cases of the app.
- **presentation**: The UI layer using Jetpack Compose.

### Clean Architecture Layers

- **Data**: Manages data sources and repositories, including remote API and local database.
- **Domain**: Contains use cases and business logic, which interact with data sources.
- **Presentation**: Handles the UI using Jetpack Compose and ViewModel.

## Unit Testing

The app includes unit tests for the use cases, ensuring that the business logic works as expected. These tests can be found in the respective test directories under the "domain" module.
