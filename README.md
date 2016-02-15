# Android-NewsImageSearch
An android app that lets users browse through nytimes articles and read them in a webview. The user can also share the web links.

Time spent: 20 hours

## User Stories


  * The following user stories must be completed:
    - [x]: User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API. (3 points)
    - [x]: User can click on "settings" which allows selection of advanced search options to filter results. (3 points)
    - [x]: User can configure advanced search filters such as: (points included above)
      - [x]: Begin Date (using a date picker)
      - [x]: News desk values (Arts, Fashion & Style, Sports)
      - [x]: Sort order (oldest or newest)
    - [x]: Subsequent searches will have any filters applied to the search results. (1 point)
    - [x]: User can tap on any article in results to view the contents in an embedded browser. (2 points)
    - [x]: User can scroll down "infinitely" to continue loading more news articles. The maximum number of articles is limited by the API search. (1 point)
  * The following advanced user stories are optional but recommended:
    - [x]: Advanced: Robust error handling, check if internet is available, handle error cases, network failures. (1 point)
    - [x]: Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText. (1 point)
    - [x]: Advanced: User can share a link to their friends or email it to themselves. (1 point)
    - [x]: Advanced: Replace Filter Settings Activity with a lightweight modal overlay. (2 points)
    - [x]: Advanced: Improve the user interface and experiment with image assets and/or styling and coloring (1 to 3 points depending on the difficulty of UI improvements)
    - [x]: Bonus: Use the RecyclerView with the StaggeredGridLayoutManager to display improve the grid of image results (see Picasso guide too). (2 points)
    - []: Bonus: For different news articles that only have text or only have images, use Heterogenous Layouts with RecyclerView. (2 points)
    - [x]: Bonus: Apply the popular ButterKnife annotation library to reduce view boilerplate. (1 point)
    - []: Bonus: Use Parcelable instead of Serializable using the popular Parceler library. (1 point)
    - [x]: Bonus: Leverage the popular GSON library to streamline the parsing of JSON data. (1 point)
    - [x]: Bonus: Replace Picasso with Glide for more efficient image rendering. (1 point)

  


## Video Walkthrough 

![General Functionality](https://github.com/aparnarsjain/Android-NewsImageSearch/blob/master/newsimagesearch.gif)


GIF created with [LiceCap](http://www.cockos.com/licecap/).



## Third party libraries

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Butterknife](http://jakewharton.github.io/butterknife/) - Removes boilerplate code by binding view IDs to objects
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library
- [GSON](https://github.com/google/gson) - Java serialization library for making JSON parsing easier
