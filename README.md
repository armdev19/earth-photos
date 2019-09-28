# Earth Photos Example

Earth Photos - This application uses NASA's API to display photos of the planet Earth. The user can select both the day of the photo and the time of the photo. The Earth of Photos allows the user to send favorite photos to friends using any social networks. And also allows you to set your favorite photos as wallpaper on your device.

You can use this application to study the architecture and third-party libraries used in this project.

## Project DEMO

![Alt Text](https://user-images.githubusercontent.com/55914555/65821768-34eeca80-e24b-11e9-86a0-c4c9242c5940.gif)


<b>minSdkVersion - 21 (Lollipop) /
targetSdkVersion - 29 (Android 10)</b>

<b>Architecture:</b> Observer

<b>To use the program you need to get a key on the NASA website, this key has a limit of 1000 requests per day.</b>
<b>Link to NASA website </b> <a href="NASA website">https://api.nasa.gov/</a>

### Used libraries

* <b>RxJava 2</b>
* <b>Retrofit 2</b>
* <b>Okhttp 3</b>
* <b>Subsampling scale image view</b>
* <b>Universal image loader</b>
* <b>Kenburnsview</b>


![1 1](https://user-images.githubusercontent.com/55914555/65821493-4df57c80-e247-11e9-8996-562ab0b72af5.png)
![1 2](https://user-images.githubusercontent.com/55914555/65821494-4df57c80-e247-11e9-8554-efff18013c21.png)
![1 3](https://user-images.githubusercontent.com/55914555/65821495-4e8e1300-e247-11e9-938b-32acbd571b40.png)
![1 4](https://user-images.githubusercontent.com/55914555/65821496-4e8e1300-e247-11e9-959f-9ea95fcb563e.png)

-------------------------------------------------------------------------------------------------

<b>implementation 'com.squareup.retrofit2:retrofit:2.4.0'</b> - Retrofit REST client

<b>implementation 'com.squareup.okhttp3:okhttp:4.0.0'</b> - okHttp HTTP client

<b>implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'</b> - RxJava adapter for Retrofit

<b>implementation 'com.squareup.okhttp3:logging-interceptor:4.0.0'</b> - Logger for okHttp

<b>implementation 'com.squareup.retrofit2:converter-gson:2.4.0'</b> - JSON converter for GSON

<b>implementation 'com.google.code.gson:gson:2.8.5'</b> - GSON - parser library to JSON

<b>implementation 'io.reactivex.rxjava2:rxjava:2.2.8'</b> - RxJava is a Reactive Programming library

<b>implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'</b> - RxJava for Android

<b>implementation 'com.davemorrissey.labs:subsampling-scale-image-view:3.10.0'</b> - Scalable ImageView

<b>implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'</b> - Image loader

<b>implementation 'com.flaviofaria:kenburnsview:1.0.7'</b> - ImageView Animated Effect
