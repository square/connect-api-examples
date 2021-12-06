# Useful Links

- [Square Online Checkout](https://squareup.com/us/en/online-checkout)
- [Orders API Reference](https://developer.squareup.com/reference/square/orders-api)

## Setup

### Server

1. Create a Square application. If you haven't done this yet, you can quickly set one up in the [Developer Dashboard](https://developer.squareup.com/apps).
1. To start this server you will need NodeJS installed. A good way to install it is with Node Version Manager, which
 is a tool to manage multiple node versions on one machine. This will allow you to install the specific version this
  project needs. To get started, install [NVM](https://github.com/nvm-sh/nvm).
1. Next move into this folder and run `nvm use`.
2. Then install the node dependecies for the project with `npm install`.
1. Copy the example env file and add your Square Developer personal access token and location token. `cp .env.example
 .env`.
1. Run the server with `npm start`.

### Android

1. Open `android/build.gradle` in Android Studio version Arctic Fox or newer. 
2. Click run to run the demo on a selected device emulator.

### iOS

1. To view the project, open `iOS/SquareOnlineCheckoutDemo.xcodeproj` in Xcode 13.0 or newer. 
2. Click Run to run it on the iOS Simulator. 

# License

Copyright 2021 Square, Inc.
​
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
​
   http://www.apache.org/licenses/LICENSE-2.0
​
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```