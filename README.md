# gift-card-api-sample-app

## **Overview**

This sample web application integrates the Square [Gift Cards API](https://developer.squareup.com/reference/square/gift-cards-api)and showcases the functionality, including:

*   Creating a gift card.
*   Activating a gift card.
*   Linking and unlinking a gift card to and from a customer. 
*   Adding funds to a gift card.
*   Viewing gift card activities.

For an overview of the API, see [](https://developer.squareup.com/docs/gift-cards/using-gift-cards-api). 

As an added feature, the application creates a bar code for each gift card. The functionality is not related to the Gift Cards API but illustrates a useful scenario: making payments directly with a gift card’s barcode on the Square POS.

The example application is fine for testing in the Square Sandbox. It does not implement features that are essential in the production environment, such as login authentication. For login, the application simply lists customers in the seller’s account (identified by the credentials you provide). You choose a customer and the application tracks the customer as logged in. 

In addition to the Gift Cards API and the Gift Card Activities API, the application uses the following Square APIs for an integrated experience:

* The [Orders API](https://developer.squareup.com/reference/square/orders-api) to create a gift card order.
* The [Payments API](https://developer.squareup.com/reference/square/payments-api) to pay for the order.
* The [Customers API](https://developer.squareup.com/reference/square/customers-api) to create customer profiles in the seller's Customer Directory. 
* The [Locations API](https://developer.squareup.com/reference/square/locations-api) to find the seller’s currency to be used throughout the application.

Before you begin, note the following:

* **Application framework.** This sample uses Express (a web framework for Node.js). Node.js is used primarily because it is easy to set up and test. Otherwise, you can use any other Square-provided SDK. 

* **Sandbox testing.** Application configuration allows you to test the application both in the Square Sandbox and in the production environment. For testing, the Sandbox is great because you do not charge your real credit card. Instead, you use a fake card that Square provides for the Sandbox environment.

## Setup

1. Ensure that you have npm installed with Node.js version v10 or later (run `npm -v` in your terminal). If not, follow the instructions for your OS: [https://www.npmjs.com/get-npm](https://www.npmjs.com/get-npm) 

2. Set your credentials: 
    1. You need an .env file to provide credentials. Square provides an .env.example file. You should make a copy and name it .env. 
    2. In the file, set the following:
        1. Environment to Sandbox (for testing).
        2. Provide other credentials for the Sandbox environment. 
 
    For more information, see [Getting Started](https://developer.squareup.com/docs/get-started#step-2-create-an-application). The Create an Application section explains introductory credentials information. 

    **WARNING:** Remember to use your own credentials only for testing the sample application. If you plan to make a version of this sample application available for your own purposes, use the Square [OAuth API](https://developer.squareup.com/docs/oauth-api/overview) to safely manage access to Square accounts. 

3. Open your terminal and install the sample application's dependencies with the following command: 
 
   `npm install`
4. Test the application and run the server. Depending on the content of the .env file, the application runs in the Square Sandbox or production environment. 

   `npm start` 

5. Enter **localhost:3000** in your browser. Initially, you get a login page. You choose one of the customers to log in. If the customer has previously created gift cards, the dashboard shows those gift cards. You can then explore the application and Gift Cards API. It is assumed that you have tested customers with cards on file for Sandbox testing. If not, see the next step.


## Project organization

This Express.js project is organized as follows:

*   **.env.** Square provides an .env.example. You make a copy of this file and save it as .env. You provide credentials in the .env file.
*   **/public.** Provides images, JavaScript, and CSS files used to render the pages.
*   **/routes.** The following JavaScript files define the routes to handle requests:
    *   **index.js.** Provides routes to handle all the login or logout requests for the initial page. 
    *   **dashboard.js.** Provides a route to display customer gift cards. 
    *   **gift-card.js.** Provides routes to manage gift cards. This includes gift card creation and managing gift card activities.
    *   **seed.js.** Provides endpoints that are used only when you test this application in the Square Sandbox. The endpoints are used to create customers to the seller’s Customer Directory and to add cards (credit or debit) on files that are used to pay for gift cards. It also provides an endpoint to clear the test data you create during this application testing.
*   **/util.** Includes the following:
    *   **square_client.js.** The utility code initializes the Square SDK client. 
    *   **middleware.js.** Provides functions that are called in a router before the controller code. These functions verify that the customer has permissions to perform actions that the controller code performs.
*   **/views.** Provides the view (.ejs) files.

## Sample data

You need a customer in the Customer Directory with a card on file to pay for a gift card order. When testing in the Square Sandbox, the application can provide sample data for you:

*   The login page provides the following:
    *   The **Select customer** drop-down menu provides an option to create a customer.
    *   The page also provides the **Reset now** link to clear any previously created test data.
*   As you test the application, when you create a gift card, you get an option to add a card on file. You can then use the card to pay for a gift card order.

## Application flow

The application flow is explained with the assumption that you are familiar with [Express](https://expressjs.com/) (the web framework for Node.js).


### Login

The application relies on a logged in customer. For example:

* The dashboard shows gift cards on file for the logged in customer.
* When you add a new gift card, the application links it to the logged in customer.

However, the application does not perform any real login authentication. Instead, the application first retrieves all customer profiles from the seller’s account (the seller whose credentials you provided in the config file). The application then shows a login page with the customer list. You choose a customer to log in. The application saves the customer ID for the session as the logged in customer. 


**Note:** Do not copy this code in production, implement real authentication methods instead.

1. When you enter **localhost:3000** in the browser, the following handler (in [index.js](https://github.com/square/connect-api-examples/blob/master/connect-examples/v2/node_subscription/routes/index.js#L39)) executes: 

    ```
    router.get("/", async (req, res, next) => {
      if (req.session.loggedIn) {
        res.redirect('/dashboard');
      } else {
        res.redirect('/login');
      }
    });
    ```
    Depending on whether the customer is logged in, the handler redirects to one of the following paths:_ /dashboard_ (see next section) or _/login_. Assuming that the user is not logged in, the handler calls `res.redirect('/login'); `to render a login page. 

    ```
    router.get("/login", async (req, res, next) => {
     let customers;
     try {
       const response = await customersApi.listCustomers();
       customers = response.result.customers;

       res.render("pages/login", {customers})
     } catch(error) {
       console.log(error);
       next(error);
     }
    });
    ```
    This handler function calls `listCustomers` (Customers API) to get a list of customers using the Customers API and then renders the login page. An example screenshot is shown: 
    <img src="./bin/images/gift-cards-api-app-10.png" width="300"/>


2. If you are running the application for the first time, you first choose **Add customer** from the **Select customer** drop-down menu to create a new customer.

    Note that you can choose **Reset now** to delete previously created sample data that the application created.

3. You choose a customer from the drop-down menu and choose **Login**. The button click form action (see pages/login.ejs) calls the `router.post("/login", async (req, res, next))` router in index.js. The controller stores the customer ID in the session and redirects back to the handler `res.redirect("/") `to render the gift card dashboard.

**IMPORTANT:** The login code in this example application does not perform any real authentication. In production, you need to write the necessary code.

### Gift card dashboard

Now that the customer is logged in, the handler `res.redirect("/") `calls `.redirect('/dashboard')` to redirect the request to the following controller (in dashboard.js):

```
router.get("/", checkLoginStatus, async (req, res, next) => {
  // display a list of gift cards linked to the
  // customer's account
  try {
    // TODO: filter only active cards
    const {result : { giftCards } } = await giftCardsApi.listGiftCards(undefined, undefined, undefined, undefined, req.session.customerId);

    if (!giftCards) {
      res.render("pages/dashboard", { giftCards: [] });
    } else {
      res.render("pages/dashboard", { giftCards });
    }
  } catch (error) {
    console.log(error);
    next(error);
  }
});
```
After `checkLoginStatus` (see middleware.js) verifies that the customer is logged in, the handler calls `listGiftCards` (Gift Cards API) to retrieve gift cards linked to the customer. These cards are displayed on the dashboard view.  
 
The following example screenshot shows a dashboard with no gift cards and a button to create more. 

<img src="./bin/images/gift-cards-api-app-20.png" width="300"/>

The gift card dashboard enables the following: 

*   **Add a new gift card.** The application creates a gift card (see [CreateGiftCard](https://developer.squareupstaging.com/reference/square/gift-cards-api/create-gift-card)) and links it to the customer (see [LinkCustomerToGiftCard](https://developer.squareupstaging.com/reference/square/link-customer-to-gift-card)).
*   **View specific gift card details and manage gift card activities.** After you create one or more gift cards, you can view gift card details such as the balance amount (see [RetrieveGiftCardFromGAN](https://developer.squareupstaging.com/reference/square/gift-cards-api/retrieve-gift-card-from-gan)), view the transaction history (see [ListGiftCardActivities](https://developer.squareupstaging.com/reference/square/gift-card-activities-api/list-gift-card-activities)), and add funds to a gift card. Adding funds involves a series of steps, including creating an order using the Orders API, taking a payment using Payments API, and using the Gift Cards API (see [CreateGiftCardActivity](https://developer.squareupstaging.com/reference/square/gift-card-activities-api/create-gift-card-activity)) to load funds.

The following sections explain the application flow for these dashboard activities: creating a gift card and adding funds to it.

### Add a new gift card 
This section explains using the dashboard to add a gift card and link it to the customer profile.

1. Make sure the dashboard is open. If not, choose **Gift Card Dashboard** to open it. 

2. Choose **Add Gift Card**. The button click form action (see pages/dashboard.ejs) calls the following handler (in gift-cards.js):  

    ```
   router.post("/create", checkLoginStatus, async (req, res, next) => {
     try {
       // The following information will come from the request/session.
       const customerId = req.session.customerId;

       // Create an inactive gift card.
       const giftCardRequest = generateGiftCardRequest();
       const { result: { giftCard }} = await giftCardsApi.createGiftCard(giftCardRequest);

       // Now link it to the customer logged in!
       await giftCardsApi.linkCustomerToGiftCard(giftCard.id, {
         customerId
       });

       // Redirect to GET /gift-card/:gan, which will render the card-detail page.
       res.redirect("/gift-card/" + giftCard.gan);
     } catch (error) {
       console.error(error);
       next(error);
     }
   });
   ```
   The controller makes the following Gift Cards API calls: 

    *   `createGiftCard`. Create a gift card (with no funds and pending activation).
    *   `linkCustomerToGiftCard`. Links the gift card to the logged in customer.

    The controller then redirects back to the card details page (`pages/card-detail`).


     `router.get("/:gan", checkLoginStatus, checkCardOwner, async(...))` 

     <img src="./bin/images/gift-cards-api-app-30.png" width="300"/>

3. Choose **Dashboard** to return to the dashboard. The dashboard now shows the newly added card. At this time, the card is not activated and it has no funds. The next section explains the details.

### Add funds to a gift card 

This section explains the application flow for activating the gift card and adding funds.

1. Make sure the dashboard is open. If not, choose **Gift Card Dashboard** to open it.
2. Choose the card you created in the preceding step.    
   The button click action calls the following router (in gift-card.js).
   ```
   router.get("/:gan", checkLoginStatus, checkCardOwner, async (req, res, next) => {
      const giftCard = res.locals.giftCard;
      const payment = req.query.payment;

      res.render("pages/card-detail", { giftCard, payment });
    });
   ```
    After the functions `checkLoginStatus `and `checkCardOwner` (in middleware.js) verify that the customer is still logged in and the card requested is linked to the customer, the handler function executes. It calls `res.render("pages/card-detail")` to compile the template and render the gift card HTML. 

3. On the **Gift Card** page, choose **Add Funds**. The button click form action calls the following handler (in gift-cards.js.): 

    ```
    router.get("/:gan/add-funds", checkLoginStatus, checkCardOwner, async (req, res, next) => {
     const gan = req.params.gan;
     try {
       const { result: { customer } } = await customersApi.retrieveCustomer(req.session.customerId);
       const creditCardsOnFile = customer.cards.filter(card => card.cardBrand !== "SQUARE_GIFT_CARD");
       res.render("pages/add-funds", { cards: creditCardsOnFile, gan, giftCard: res.locals.giftCard });
     } catch (error) {
       console.error(error);
       next(error);
     }
   });
   ```

    This example application allows customers to use credit or debit cards on file to pay for adding funds to the gift card. The customer might have gift cards on file, but the application does not allow using a gift card on file to load funds on another gift card. 

    The controller code does the following:
    *   Calls `retrieveCustomer` (Customers API) to retrieve customer information that includes cards on file.
    *   Calls `res.render("pages/add-funds"...)` to compile the template and render the resulting **Add funds** HTML. An example is shown: 

    <img src="./bin/images/gift-cards-api-app-40.png" width="300"/>

4. You choose the amount to add and a card on file from the drop-down menu that you want charged. If there is no card on file, choose **Add card** to create a card on file. Then select the card on file and choose **Pay**.  
The button click form action calls the following handler (in gift-cards.js.):

   ```
   router.post("/:gan/add-funds", checkLoginStatus, checkCardOwner, async (req, res, next) => {
     try {
       // The following information will come from the request/session.
       const customerId = req.session.customerId;
       const amount = req.body.amount;
       const paymentSource = req.body.cardId;
       const giftCardState = req.body.state;
       const gan = req.params.gan;

       // Get the currency to be used for the order/payment.
       const currency = req.app.locals.currency;

       // The following code runs the order/payment flow.
       // Await order call, as payment needs order information.
       const orderRequest = generateOrderRequest(customerId, amount, currency);
       const { result: { order } } = await ordersApi.createOrder(orderRequest);

       // Extract useful information from the order.
       const orderId = order.id;
       const lineItemId = order.lineItems[0].uid;

       // We have the order response, we can move on to the payment.
       const paymentRequest = generatePaymentRequest(customerId, amount, currency, paymentSource, orderId);
       await paymentsApi.createPayment(paymentRequest);

       // Load or activate the gift card based on its current state.
       // If the gift card is inactive, activate it with the amount given.
       // Otherwise, if the card is already active, load it with the amount given.
       const giftCardActivity = giftCardState === "NOT_ACTIVE" ? "ACTIVATE" : "LOAD";
       const giftCardActivityRequest = generateGiftCardActivityRequest(giftCardActivity, gan, orderId, lineItemId);
       await giftCardActivitiesApi.createGiftCardActivity(giftCardActivityRequest);

       // Redirect to GET /gift-card/:gan, which will render the card-detail page, with a success message.
       res.redirect("/gift-card/" + gan + "/?payment=success");
     } catch (error) {
       console.error(error);
       next(error);
     }
   });
   ```
   After the `checkLoginStatus` and `checkCardOwner` functions verify that the customer is logged in and the card being loaded belongs to the logged in customer, the controller function executes. It makes the following API calls: 
   
   *   `createOrder (Orders API)`. Create an order with a line item that has `item_type` set to GIFT_CARD because you are creating this order to add money to the gift card. 
   *   `createPayment (Payments API)`. Take payment by charging the specified card on file. 
   *   `createGiftCardActivity (GiftCardActivities API)`. Load funds on the gift card by calling this endpoint. In the request, the activity type is set appropriately. For example, LOAD to load funds or ACTIVATE to activate the gift card.



