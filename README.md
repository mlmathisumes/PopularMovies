# Getting Started
This project uses [themoviedb.org](https://www.themoviedb.org/movie) API to pull in the movie data. In order to use the API, you will need to [create an](https://www.themoviedb.org/account/signup) account and request an API key. 

Once you obtain your key, you append it to your HTTP request as a URL parameter like so:
http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
# Screenshots
# Libraries Used

* [Foundation](https://developer.android.com/jetpack) - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.
  * [AppCompat](https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat) - Degrade gracefully on older versions of Android.
* [Architecture](https://developer.android.com/topic/libraries/architecture) - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
* [UI](https://developer.android.com/guide/topics/ui) - Details on why and how to use UI Components in your apps - together or separate
  * [Fragment](https://developer.android.com/guide/components/fragments) - A basic unit of composable UI.
  * [FlowText](https://github.com/deano2390/FlowTextView) - A wrapping TextView for Android
* Networking
  * [Retrofit](https://square.github.io/retrofit/) - REST Client library (Helper Library) used in Android and Java to create an HTTP request and also to process the HTTP response from a REST API. 
* Third party
  * [Glide](https://bumptech.github.io/glide/) for image loading
# Upcoming features
Update Grid view to dynamically calculate the number of columns and the layout will adapt to the screen shize and orientation.
