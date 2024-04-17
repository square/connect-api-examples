# Catalog Item Subscription Sample App

## Prerequisites

1. Go make a developer account with [Square](https://squareup.com/signup/us?lang_code=en-us&return_to=https%3A%2F%2Fdeveloper.squareup.com%2Fapps&v=developers)

1. Create an [application](https://developer.squareup.com/apps)

1. Get your sandbox Application Id by clicking `open` on your application and getting the value under `Sandbox Application ID`

1. Create a new [sandbox test account](https://developer.squareup.com/console/en/sandbox-test-accounts)

1. Get the access token for that test account by clicking on the name of the test account, opening the drawer of one of that authorized apps, and grabbing the field that says access token


## Getting started

### Seeding your Sandbox Test Account
```
$ cd server
$ npm i 
$ cp .env.example .env
```
In `.env` Change the value of `SQ_ACCESS_TOKEN` to your sandbox access token you retrieved earlier from the sanbox test account you created
```
$ npm run seed
```

### What does the seed script do?

Located in `server/bin/seed-catalog.js` this script will do the following:

1. Create 2 customers, one with a card on file and the other without using Square's [Create Customer Endpoint](https://developer.squareup.com/reference/square/customers-api/create-customer).
1. Create various Catalog objects, which are defined in `server/bin/sample-seed-data.json` - We read this data in and make one request to Square's [Batch Upsert Catalog endpoint](https://developer.squareup.com/reference/square/catalog-api/batch-upsert-catalog-objects)
    ```
    1. Creates 4 Catalog Categories - Breakfast, Lunch, Dinner, and Vegetarian

    2. Creates 21 Items for the catalog. These are the different meals for our meal plan. We assign each meal various categories during this step

    3. Creates 1 Discount Object - which is used on our subscriptions

    4. Creates Our 3 different Subscription Plans and 6 different Subscription Plan Variations. (2 variations per plan)
    ```
1. Lastly we will add image data to each of the 21 Items we created using Square's [Create Catalog Image Endpoint](https://developer.squareup.com/reference/square/catalog-api/create-catalog-image)

You can go to your sandbox test account and view this seeded data in the dashboard if you like.

### Set up your REST Server
After successfully seeding your sandbox test account, you can now start your REST server for the webapp

```
$ npm run dev
```

You will now have an Express server running and listening on [localhost:4000](http://localhost:4000)

The different endpoints available are defined in `server/routes` where you can see the code powered by the Square SDK that makes API calls to Square. 

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