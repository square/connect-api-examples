# Order-Ahead Sample App
This project is written in [NodeJS](https://nodejs.org/en/) be sure to have NodeJS setup in your development environment before proceeding.

Follow the [Order-Ahead Sample App guide](https://developer.squareup.com/docs/orders-api/quick-start/start) 
to start taking pickup orders with the Square Orders API.

## Quick Start Guide

### Step 1: Set your credentials. 

Open `config.json`, you'll see that there are two sets of `squareApplicationId` 
and `squareAccessToken` variables. The first set is for your `sandbox` credentials 
and the second is for your `production` credentials. 

Replace the placeholders for `squareApplicationId` `squareAccessToken` with your 
own production or sandbox credentials. For more help, see our [guide on how to get 
your credentials](https://developer.squareup.com/docs/orders-api/quick-start/step-1). 

**WARNING**: Remember to use your own credentials only for testing the sample app. 
If you plan to make a version of this sample app available for your own purposes, 
use the Square [OAuth API](https://developer.squareup.com/docs/oauth-api/what-it-does) 
to safely manage access to Square accounts. 

### Step 2: Install dependencies

* Open your terminal and install the sample application's dependencies with the command:
```
  npm install
```

### Step 3: Test the app.

Run the server with your production credentials:
```
  npm start
```
Run the server in with your sandbox credentials:
```
   npm test
```

To see the sample app running, open `localhost:3000` in your browser. If your 
account has catalog items with images, the sample app will pull them in and display 
them. 

If your account does not have images (for example, if you are using a newly created  
account for testing), it will appear as a gray screen. 

## Create test items for your sandbox accounts

We provide a script you can use to quickly populate your sandbox store's catalog with test items.

1. Configure your app with `sandbox` credentials.
2. Run the script:
```
  npm run seed
```
3. Run the server in with your sandbox credentials:
```
   npm test
```

Navigate to `http://localhost:3000` to see the newly created items in the sample app. 

For more guidance, see the 
[Order-Ahead Sample App guide](https://developer.squareup.com/docs/orders-api/quick-start/start).
​
# License
Copyright 2019 Square, Inc.
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