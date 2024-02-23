# 簡易仕様書

## アプリ名

GourmetSearchApp

### [プロジェクトのリポジトリ (GitHub)](https://github.com/0v0d/GourmetSearchApp)


## アプリ仕様

現在位置付近のレストランをキーワードで検索できるAndroidアプリです。

<img src="docs/app.gif" width="320">

### 不具合及び使用上の注意

- 仮想端末を使用する際に、GPSが取得できないという不具合があります。</br>
(実機端末では、不具合なく動作します。)
- APIKeyはプロジェクトのルートディレクトリにある local.properties ファイルに

```properties
api_key=YOUR_API_KEY
```

の形式で、APIキーを設定して下さい。

### 動作対象端末・OS

#### 動作対象OS

Android 14

## 開発環境

Android Studio Jellyfish | 2023.3.1 Nightly 2024-02-17)

- コンパイルSDKバージョン: 34
- 最小SDKバージョン: 32
- ターゲットSDKバージョン: 34
- Java：VERSION_1_8
- Gradle：8.2
- Gradle Plugin 7.0.4
- minSdk：32
- targetSdk：34
- 
### 開発言語

- Kotlin 1.9.22

### 実機端末環境

- Redmi 12 (Android 14)

## アプリケーション機能

### 機能一覧

- レストラン検索：ホットペッパーグルメサーチAPIを使用して、現在地周辺の飲食店を検索する。
- レストラン情報取得：ホットペッパーグルメサーチAPIを使用して、飲食店の詳細情報を取得する。
- 地図アプリ連携：飲食店の所在地を地図アプリに表示する。
- キーワード検索：キーワードを入力することで、ホットペッパーグルメサーチAPIの検索結果を絞る。

### 画面概要

- キーワード検索画面 (InputSearchTermsFragment) ： キーワードを指定してGPSを取得し、検索半径が選択されると店舗一覧画面へ遷移する。
- 店舗一覧画面 (RestaurantListViewFragment) : 検索結果の飲食店を一覧表示し、選択されると店舗詳細画面へ遷移する。
- 店舗詳細画面 (RestaurantDetailFragment) : 店舗の詳細を表示し、Mapボタンを押された時にはマップアプリへ遷移
する。「Hot Pepperへ」ボタンを押されたときにWebページを表示へ遷移する。

### 使用しているAPI,SDK,ライブラリなど

- ホットペッパーグルメサーチAPI
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

### コンセプト

簡単キーワード検索ですぐにお店を見つけられる

### こだわったポイント

- キーワード検索機能を追加したことで、ユーザーが探したいお店を見つけやすくしました。</br>
- 画面ごとの入力を少なくしました。</br>
- RecyclerViewを使用することで、ListViewよりも早く店舗一覧を表示できるようにしました。

### デザイン面でこだわったポイント

店舗詳細画面や検索結果画面で表示項目を必要最低限の要素しました。</br>
このことで、ユーザーにとって、必要な情報がすぐに見つかる配置にすることができたと思います。

### アドバイスして欲しいポイント

- GPSで位置情報を得る際の処理が、助長的になっているので、アドバイスをいただきたいです。
- 現在非常にメモリ使用量が多いので、軽量化する方法をアドバイスいただきたいです。
- UIデザインについてアドバイスをいただきたいです。

### 自己評価

初めてのMVVMアーキテクチャを適用したプロジェクトでしたが、完成させることができてよかったと思っています。</br>
しかし、MVVMについての理解が浅く、全体の構造として助長的な箇所もあるので、アーキテクチャなどの学習を進めていかなければならないと思います。</br>
今後は、軽量化や検索条件を追加できる処理などを追加していきたいと考えています。</br>
