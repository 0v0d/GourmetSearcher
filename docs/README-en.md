[日本語はこちら](README.md)
## App Name

GourmetSearcher

### Project Repository (GitHub)

## App Specifications

This is an Android app that can search for restaurants near your current location using keywords.

<img src="app.gif" width="314" alt="App demo video">

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

Android Studio Koala Feature Drop 2024.1.2 Canary 5

- Compile SDK Version: 34
- Minimum SDK Version: 32
- Target SDK Version: 34
- Java：VERSION_17
- Gradle：8.8
- Gradle Plugin 8.5.0

### Development Language

- Kotlin 2.0.0

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
- androidx-core-ktx
- androidx-datastore-preferences
- coil
- detekt-formatting
- kotlinx-serialization-json
- material
- androidx-constraintlayout
- androidx-appcompat

#### Navigation
- androidx-navigation-fragment-ktx
- androidx-navigation-ui-ktx
- androidx-navigation-runtime-ktx

#### Dependency Injection
- dagger-hilt-android-compiler
- dagger-hilt-android

#### Networking
- retrofit
- retrofit-converter-moshi
- moshi-kotlin

#### Location
- play-services-location

#### Debug Tools
- leakcanary

#### Unit Test
- junit
- dagger-hilt-android-testing
- mockito-core
- androidx-runner
- kotlinx-coroutines-test
- androidx-core-testing

#### Android Test
- androidx-junit
- androidx-espresso-core
- androidx-rules
- androidx-espresso-contrib
- androidx-uiautomator-v18

### Plugins
- android-application
- jetbrains-kotlin-android
- androidx-navigation-safeargs
- kotlin-kapt
- dagger-hilt-android
- kotlin-parcelize
- secrets-gradle-plugin
- detekt
- serialization