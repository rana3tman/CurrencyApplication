# CurrencyApplication
# MVI-Clean-Architecture
<img src="https://miro.medium.com/max/4800/1*D1EvAeK74Gry46JMZM4oOQ.png" width="500">

This project is based on MVI (Model-View-Intent) architecture with the concepts of cleas archticture.

The MVI architecture pattern model is based on a similar thing. At each UI upgradation, a new state is initialized. Generally, when we want to keep a record of any activity, we take screenshots so we can go back and review things. These screenshot records help us to rectify and correct the mistakes.

MVI initiates the same approach to manage things in real-world UI development. The model asks us to debug, evaluate, test, and reconstruct the UI state events.
### Modules
Modules are the collection of source files and build settings that allow you to divide your project into discrete units of functionality.
- **Domain Module**
  The domain module is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels. 
- **Data Module**
   The data module contains application data and business logic.
- **Presentation Module**
  The role of the UI is to display the application data on the screen and also to serve as the primary point of user interaction.

Each module has its own test.
### Tech Stack
- [Kotlin](https://kotlinlang.org)
- [Jetpack](https://developer.android.com/jetpack)
	* [Android KTX](https://developer.android.com/kotlin/ktx)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [Data Binding](https://developer.android.com/topic/libraries/data-binding)
    * [View Binding](https://developer.android.com/topic/libraries/view-binding)
    *  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Coroutines - Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://github.com/square/okhttp)
- [KotlinX](https://github.com/Kotlin/kotlinx.serialization)
- [KotlinX Serialization Converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
