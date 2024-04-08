# Node Subscriptions

## Prerequisites

1. Go make a developer account with [Square](https://squareup.com/signup/us?lang_code=en-us&return_to=https%3A%2F%2Fdeveloper.squareup.com%2Fapps&v=developers)

1. Create an [application](https://developer.squareup.com/apps)

1. Get your sandbox Application Id by clicking `open` on your application and getting the value under `Sandbox Application ID`

1. Create a new [sandbox test account](https://developer.squareup.com/console/en/sandbox-test-accounts)

1. Get the access token for that test account by clicking on the name of the test account, opening the drawer of one of that authorized apps, and grabbing the field that says access token


## Getting started

### Set up your REST Server
```
$ cd server
$ npm i 
$ cp .env.example .env
```

In `.env` Change the value of `SQ_ACCESS_TOKEN` to your sandbox access token you retrieved earlier from the sanbox test account you created
```
$ npm run seed
$ npm run dev
```

You will now have an Express server running and listening on [localhost:4000](http://localhost:4000)

### Set up your Webapp
Open another Terminal

```
$ cd app
$ cp .env.example .env
```

In `.env` change the value of `REACT_APP_SQUARE_APP_ID` to the `Sandbox Application ID` your copied earlier

```
$ npm i
$ npm run start
```

You will now have a webapp running on [localhost:3000](http://localhost:3000)

Use the app to create subscriptions

### Using the App
1. When the app starts, you will be presented with two users, one will have a card on file and the other will not. You can select a user and then click `+ New Subscription`

1. Follow the steps to build a subscription plan you would like to subscribe that user to

1. At the end of the flow, you will have the option to pay for the subscription with a card on file (if there is one), or with a new credit card, which will make a new card on file for that customer. See our available test credit card numbers [here](https://developer.squareup.com/docs/devtools/sandbox/payments)

1. After successfully creating a new subscription you can click `Manage User Subscriptions` on one of your users to see the new subscription in a table. You can view more details about the subscription, as well as Cancel or pause the subscription.


### Cleanup 
Delete the sandbox test account when you are done with this sample. If you would like to run it all again, create a new sandbox test account, update the access token in your `.env` and run the seed script again. Make sure the sandbox account is using the same APP Id as well, otherwise you will need to update the `REACT_APP_SQUARE_APP_ID` value as well.