 ![version](https://img.shields.io/badge/version-1.0.0-pink)
 
## 💄 Make-Up Android App

### Table of Contents:

- [General information](#general-information)
	 - [Description](#description)
	 - [Requeriments](#requeriments)
  - [Extras requeriments](#extra-requeriments)
- [Feature Demo](#feature-demo)
	 - [API Documentation](#api-documentation)
	 - [Built With](#built-with) 

___

### General information

### Description

Make-up App was created with Kotlin and Compose for Android. This app gives you access to a complete list of cosmetic products loaded from an API. You can also see the details of each product by tapping on them. This includes the name, brand, description, available colors, and tags.

The application also supports light and dark appearance modes. You can easily identify it on your device by looking for the lipstick icon.

⚠ *This application was developed for technical testing purposes. Therefore, the requested requirements are presented below, and all the characteristics of this App.*

This repository is divided into two branches:

 - [**RemoData:**](https://github.com/LeomarisReyes/FashionMakeUp/tree/RemoteData) It has the connection to an API
 - [**OfflineData:**](https://github.com/LeomarisReyes/FashionMakeUp/tree/OfflineData) In addition to the connection to the API, it supports offline data.

### Requeriments

Create an application with the following points:


|No.| Description |
|--|--|
| 1. | Displays a list of items. You can consume a public API of these [file](https://github.com/public-apis/public-apis). |
| 2. | The detail of each item in which yo uhave to play with different visual components. | 
| 3. | Points to review: MVI Clean Architecture + compose architecture - Good practices Clean - Solid - Error handling and follow the architecture |
 

### Extra requeriments

Here are the additional requirements added by the developer.

- Light and dark mode support
- Dependency injection with Hilt
- Offline data support with Room  *(This implementation has been developed in a separate branch called OfflineData.)*

___

### Feature Demo 

In this session you will see visual resources which will help you see each functionality in real life.

#### ➖ Product list and details

Demonstration of the display of the list consumed from the API and the display of product details. Additionally, support for light mode is shown.

<p align="left"><img src="https://github.com/LeomarisReyes/FashionMakeUp/blob/RemoteData/Images/LigthMode.png" width=860 height=450/></p> 

#### ➖ Dark mode 
	 
<p align="left"><img src="https://github.com/LeomarisReyes/FashionMakeUp/blob/RemoteData/Images/DarkMode.png" width=670 height=450/></p> 

### API Documentation 

This session shows the API selected to create the application. In this case it's a cosmetics API:

- http://cosmeticapi.somee.com/index.html


Contains the following endpoints:

- **GetAllProducts:** Responsible of obtaining all the products.
- **GetAllProducts with limit:** Responsible for obtaining all products, allowing the user to limit the number of items want to obtain.
- **GetProductById:** Returns the information of an specific product.



### Built With

- [Compose](https://developer.android.com/jetpack/compose/) 
- [Compose navigation](https://developer.android.com/jetpack/compose/navigation?hl=es-419) ![version](https://img.shields.io/badge/version-1.3.9-pink)
- [Material](https://m3.material.io) ![version](https://img.shields.io/badge/version-3-pink)
- [Coroutines](https://developer.android.com/kotlin/coroutines) ![version](https://img.shields.io/badge/version-3-pink)
- [Coil](https://github.com/coil-kt/coil) ![version](https://img.shields.io/badge/version-1.4.01-pink)
- [Retrofit](https://square.github.io/retrofit/) ![version](https://img.shields.io/badge/version-2.9.0-pink)
- [Moshi](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) ![version](https://img.shields.io/badge/version-1.9.3-pink)
- [Lottie](https://github.com/airbnb/lottie-android) ![version](https://img.shields.io/badge/version-6.0.0-pink)
- [Dagger Hilt](https://developer.android.com/jetpack/androidx/releases/hilt?hl=es-419) ![version](https://img.shields.io/badge/version-2.44-pink)
- [Room](https://developer.android.com/training/data-storage/room?hl=es-419) ![version](https://img.shields.io/badge/version-2.5.0-pink)
- [Gson](https://github.com/google/gson) ![version](https://img.shields.io/badge/version-2.8.8-pink)

___

Thanks for reading! 💚💕 <br />

*@Composable<br />
fun Thanks(){<br />
    Text(text = "Leomaris Reyes")<br />
}<br />*

