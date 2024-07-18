[English README is here](README-en.md)
## アプリ名

GourmetSearcher

### [プロジェクトのリポジトリ (GitHub)](https://github.com/0v0d/GourmetSearcher)

## アプリ仕様

現在位置付近のレストランをキーワードで検索できるAndroidアプリです。

<img src="app.gif" width="314" alt="アプリのデモ動画">

### APIKeyについて

本アプリは[ホットペッパーのグルメサーチAPI](https://webservice.recruit.co.jp/doc/hotpepper/reference.html)
を使用しています。
ご利用の際には[こちら](https://webservice.recruit.co.jp/register/)にメールアドレスを登録していただく必要がございます。
登録していただくと、メールにてAPIKeyが送信されますので、そちらを以下のようにしていただくことで、ご利用可能になります。

- APIKeyはプロジェクトのルートディレクトリにある local.properties ファイルに

```properties
API_KEY="YOUR_API_KEY"
```

の形式で、APIキーを設定して下さい。

### 不具合及び使用上の注意

- 仮想端末を使用する際に、GPSが取得できないという不具合があります。</br>
  (実機端末では、不具合なく動作します。)

### 動作対象端末・OS

#### 動作対象OS

Android 14

## 開発環境

Android Studio Koala Feature Drop 2024.1.2 Canary 5

- コンパイルSDKバージョン: 34
- 最小SDKバージョン: 32
- ターゲットSDKバージョン: 34
- Java：VERSION_17
- Gradle：8.8
- Gradle Plugin 8.5.0

### 開発言語

- Kotlin 2.0.0

### 実機端末環境

- Redmi 12 (Android 14)

## アプリケーション機能

### 機能一覧

- レストラン検索：ホットペッパーグルメサーチAPIを使用して、現在地周辺の飲食店を検索する。
- レストラン情報取得：ホットペッパーグルメサーチAPIを使用して、飲食店の詳細情報を取得する。
- 地図アプリ連携：飲食店の所在地を地図アプリに表示する。
- キーワード検索：キーワードを入力することで、ホットペッパーグルメサーチAPIの検索結果を絞る。

### 画面概要

- キーワード入力画面 (InputSearchTermsFragment) ： キーワードを入力し、半径が選択されると位置情報検索画面へ遷移する。
- 位置情報検索画面 (LocationSearchFragment) : GPSの取得に成功すると店舗一覧画面へ遷移する。
- レストラン検索結果画面 (RestaurantListViewFragment) : 検索結果の飲食店を一覧表示し、選択されると店舗詳細画面へ遷移する。
- レストラン詳細画面 (RestaurantDetailFragment) : 店舗の詳細を表示し、Mapボタンを押された時にはマップアプリへ遷移
  する。「Hot Pepperへ」ボタンを押されたときにWebページを表示する。

### 使用しているAPI, SDK, ライブラリなど
- ホットペッパーグルメサーチAPI
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
