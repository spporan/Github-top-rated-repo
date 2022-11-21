# Github-top-rated-repo app
This sample app that implements MVVM architecture using hilt, Room, Paging3, Retrofit, PlaceHolderView.

# App's Features
1. List of top rated android repo.
2. App supported to offline mode.
3. Show loading progress in network call and handle network related error.
4. Repository sorting capabilities such as by top rated, by repo creation and  others.
5. Show Repository Details

#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Hilt.
3. **ui**: View classes along with their corresponding ViewModel.
4. **utils**: Utility classes.

### Libraries
* [Android Architecture Components][arch]
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Paging 3][paging] for pagination
* [Data Store][data-store] for storing small amount of data like user preference
* [mockito][mockito] for mocking in tests
* [Mock Webserver][mockwebserver] for creating a mock server and test API implementation for tests


# Screenshots

<p align="center">
![Semantic description of image](https://gitlab.com/poran.cse/Github_top_rated_repo/-/raw/main/screenshots/github_repo_app.gif)
![ScreenShoot 1](https://gitlab.com/poran.cse/Github_top_rated_repo/-/raw/main/screenshots/github_repo_app_shot2.jpg)
![ScreenShoot 2](https://gitlab.com/poran.cse/Github_top_rated_repo/-/raw/main/screenshots/github_repo_app_shoot1.jpg)
</p>

# APK Download
[Download APK](https://gitlab.com/poran.cse/Github_top_rated_repo/-/raw/main/apk/app-dev-debug.apk)

[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[data-store]: https://developer.android.com/topic/libraries/architecture/datastore?gclid=Cj0KCQiA4OybBhCzARIsAIcfn9mSE2oMBKwkdd6hOmLMmpmPl9LbvmXBxjBfCd1Zl3gcvhlOAOehV80aAt-wEALw_wcB&gclsrc=aw.ds#preferences-datastore-dependencies
[arch]: https://developer.android.com/arch
[paging]: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[hilt]: https://developer.android.com/training/dependency-injection/hilt-android
[mockito]: http://site.mockito.org
[retrofit-mock]: https://github.com/square/retrofit/tree/master/retrofit-mock