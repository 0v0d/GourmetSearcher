[日本語はこちら](https://github.com/0v0d/GourmetSearcher/blob/future-readme/docs/README-ja.md)
## App Name

GourmetSearcher

### Project Repository (GitHub)

## App Specifications

This is an Android app that can search for restaurants near your current location using keywords.

<img src="docs/app.gif" width="314" alt="App demo video">

### About APIKey

This app uses the Hot Pepper Gourmet Search API.
When using it, you need to register your email address here.
Once registered, an APIKey will be sent to you by email. You can use it as follows:

- Set the API key in the local.properties file in the root directory of the project in the following format:

```properties
API_KEY="YOUR_API_KEY"
```

### Bugs and Usage Notes

- There is a bug that GPS cannot be obtained when using a virtual terminal.</br>
  (It works without problems on actual devices.)

### Target Devices & OS

#### Target OS

Android 14

## Development Environment

Android Studio Jellyfish | 2023.3.1 Nightly 2024-02-17)

- Compile SDK Version: 34
- Minimum SDK Version: 32
- Target SDK Version: 34
- Java: VERSION_1_8
- Gradle: 8.6
- Gradle Plugin 7.0.4
- minSdk: 32
- targetSdk: 34

### Development Language

- Kotlin 1.9.22

### Actual Device Environment

- Redmi 12 (Android 14)

## Application Features

### Feature List

- Restaurant Search: Use the Hot Pepper Gourmet Search API to search for eateries around your current location.
- Restaurant Information Acquisition: Use the Hot Pepper Gourmet Search API to obtain detailed information about eateries.
- Map App Collaboration: Display the location of the eatery on the map app.
- Keyword Search: By entering a keyword, you can narrow down the search results of the Hot Pepper Gourmet Search API.

### Screen Overview

- Keyword Input Screen (InputSearchTermsFragment): When a keyword is entered and a radius is selected, it transitions to the location information search screen.
- Location Information Search Screen (LocationSearchFragment): When GPS acquisition is successful, it transitions to the store list screen.
- Restaurant Search Result Screen (RestaurantListViewFragment): Displays a list of search results for eateries, and when selected, transitions to the store detail screen.
- Restaurant Detail Screen (RestaurantDetailFragment): Displays details of the store, and when the Map button is pressed, it transitions to the map app. When the "To Hot Pepper" button is pressed, it displays a web page.

### APIs, SDKs, Libraries Used

- Hot Pepper Gourmet Search API
- dagger.hilt
- Retrofit
- OkHttp
- Moshi
- safeargs
- secrets-gradle-plugin
- parcelize
- ViewModel
- ViewBinding
- picasso
- navigation-ui
- play-services-location
- DataBinding
- Fragment
- RecyclerView
