# Square and GraphQL - Code Example

This sample program demonstrates how to work with the Square GraphQL endpoint from a Node.js program.

Noteworthy files are:

* `index.js` - the main program, written in Node.js.
* `queries.js` - GraphQL queries used in the program.
* `bin/script/test-data.json` - fictitious test data, for the GraphQL queries.
* `bin/script/seed-data.js` - a script to upload the test data to your Square account, or to delete the data when you're done testing.
  
## Prerequisites

Before you run this program, you'll need to do the following:

1. Create a Square account and an application.  For more information, see our [developer documentation](https://developer.squareup.com/docs/square-get-started#step-1-create-an-account-and-application).

2. Get your access token for the Square sandbox.  To do this, go to <https://developer.squareup.com/apps> and choose your application.  From the side navigation, click *Credentials*.  In the *Sandbox Access Token* field, click *Show*.

3. Get a *location ID* for your account.  To do this, go to the [API Explorer](https://developer.squareup.com/explorer/square/locations-api/list-locations)  and click *Run*.  From the response window, choose a location ID.

4. Get a *merchant ID* for your account.  To do this, go to the [API Explorer](https://developer.squareup.com/explorer/square/merchants-api/list-merchants) and click *Run*.  From the response window, choose a merchant ID.

You'll also need to download and install Node.js.  For more information, see <https://nodejs.org>.

Finally, use the Node package manager (`npm`) to install the dependencies you'll need for this program:
  
* `npm install`
  
## Set your Environment Variables

Before you begin, set the `SQUARE_ACCESS_TOKEN` and `LOCATION_ID` environment variables.  There are two different ways you can do this:

### 1. Create a `.env` file

We've provided a file named `.env.example` that you can use as a starting point.  Its contents are as follows:

```bash
LOCATION_ID=<enter in a location id>
SQUARE_ACCESS_TOKEN=your-access-token
```

Copy this `.env.example` to a new file, named `.env`.  In the `.env` file, replace the placeholder text with valid values for your *LOCATION_ID* and *SQUARE_ACCESS_TOKEN*.

### 2. Set the variables at the command line

If you prefer, you can set these environment variables from the command line instead.  For example:

* `export LOCATION_ID=yourLocationId`
* `export SQUARE_ACCESS_TOKEN=yourAccessToken`

Replace `yourLocationId` and `yourAccessToken` with valid values for each.

## Populate the Test Data

The `bin/script/test-data.json` file contains fictitious data for:

* Catalog
* Customers
* Merchants
* Orders
  
To load this data into your sandbox environment, run the following command:

`npm run seed`

The output should look similar to this:

```text
Successfully created customers [
  'N939C08CT8XVN9A1FT7WNRC56C',
  'MWXX2N5B9WSRHE13CZCNKN3898',
  '8YN5QH4TWMR57ABM22KS9ZTF38',
  ...remaining output omitted...
]
Successfully created catalog objects [
  'UMZLE5VBBUYUGYVZFBXJTJSD',
  'VSAYRK2IX6YDP6LPNMMPJPPG',
  'NT5QAS7HCBEB2C6ARSZRYGY4',
  ...
]
Successfully created orders [
  'hGIlQoUxwG7Etod9aRPAqOa20Z4F',
  'ndqv937wJPTjRdJPKyKMIA1tDh4F',
  'Ba4Wxsoaxch4lpZDP6WJ5gH2Yd4F',
  ...
]
...
```

## Run the sample program

After you've loaded the test data, you can use the sample program to run GraphQL queries.  Run the program as follows:

```bash
npm run start
```

The output should look similar to this:

```text
? Which GraphQL query would you like to execute? › - Use arrow-keys. Return to submit.
❯   catalog
    customers
    merchants
    orders
    payments
```

Choose `catalog` (the default).  

Next, you'll be prompted for one or more merchant IDs:

```text
? Enter in merchantIds. If multiple then separate them by a comma (ex. merchantId1, merchantId2) › 
```

Type your merchant ID (see the *Prerequisites* section above), and then press Enter.

The first part of the output shows the GraphQL query to be executed:

```text
Running the query: 
  query CATALOG_SAMPLE_APP_QUERY(
    $after: Cursor,
    $merchantIds: [ID!]!
  ) {
    catalog {
      all(
        after: $after
        filter: {
          merchants: { equalToAnyOf: $merchantIds }
        }
      ) {
        pageInfo {
          ...PageInfoFragment
        }
        ...remaining output omitted...
  }
```

Next, it shows the results from executing the query:

```text
Page 1:
===================================================
{
  __typename: 'CatalogItem',
  id: 'BJL4CIX426EBHWJJORFEIR35',
  version: 1635975950679,
  isDeleted: false,
  updatedAt: '2021-11-03T21:45:50.679Z',
  absentAt: null,
  presentAt: null,
  presentAtAll: true,
  abbreviation: 'CR',
  availableElectronically: null,
  availableForPickup: null,
  availableOnline: null,
  category: { id: 'LIMNRASQPONR5CM4SWY3B5I6' },
  description: 'Light and flaky buttery croissant',
  image: null,
  options: null,
  labelColor: null,
  modifierListInfos: null,
  name: 'Croissant',
  productType: 'REGULAR',
  skipModifierScreen: null,
  taxes: [ { id: '7ANO66GMV5XZ2W2YEMBVQHLH', name: 'Sales tax' } ],
  variations: [
    { id: '3Y4YKX4AHV2GMYBUERBZEFKJ', name: 'Regular' },
    { id: 'M443PFRBMCQOFDZKVMRFAEP4', name: 'Chocolate Croissant' },
    { id: 'UP5PT4QOI7RICUMQ3DEDVAY7', name: 'Ham & Swiss Croissant' }
  ]
}
===================================================
   ...remaining output omitted...
```

## Next steps

You've now run your first GraphQL query, to retrieve catalog data.  Try running the program again, and explore the queries for customers, merchants, orders and payments.

When you run a query, take note of this message:

```text
Query execution is complete. You can also execute queries & view the GraphQL schema at https://developer.squareup.com/explorer/graphql
```

The specified link (<https://developer.squareup.com/explorer/graphql>) takes you to the GraphQL Explorer for Square, where you can create and run your own GraphQL queries using the sample data.

### Clean up test data (optional)

After you've run the program, you can remove the test data from your sandbox environment:

`npm run clear`

You'll be asked for confirmation:

```text
Are you sure you want to clear all test data created by the seeding script? (y/n) 
```

Enter *y* if you're ready to proceed.  The output should look similar to this:

```text
Successfully deleted customers [
   ...output omitted...
]
Successfully deleted catalog items [
   ...
]
Successfully canceled orders [
   ...
]
...
```
