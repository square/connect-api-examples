# Useful Links

* [Node.js SDK Page](https://developer.squareup.com/docs/sdks/nodejs)
* [Bookings API Overview](https://developer.squareup.com/docs/bookings-api/what-it-is)
* [Bookings API Reference](https://developer.squareup.com/reference/square/bookings-api)

# Overview

This sample web application integrates the Square [Bookings API](https://developer.squareup.com/reference/square/bookings-api) and showcases some of its functionality, including:

* Create booking
* Update booking
* Cancel booking
* Search availabilities
* List team member booking profiles
* Retrieve team member booking profile

In addition to using the Bookings API, the application demonstrates how to integrate the Boookings API with the following Square APIs:

* Calling the [Catalog API](https://developer.squareup.com/reference/square/catalog-api) to create and retrieve catalog objects of the appointment service type.
* Calling the [Customers API](https://developer.squareup.com/reference/square/customers-api) to create and retrieve customer profiles in the seller's customer directory.
* Calling the [Locations API](https://developer.squareup.com/reference/square/locations-api) to get information about the seller's business location used throughout the application.
* Calling the [Teams API](https://developer.squareup.com/reference/square/team-api) to retrieve the profile of a team member providing booked services in an appointment.

## Setup

### Set up the application

1. Ensure that you have npm installed (run `npm -v` in your terminal). If not, follow the instructions for your OS at [https://www.npmjs.com/get-npm](https://www.npmjs.com/get-npm).

2. Ensure that you have Node.js version v10 or later (run `node -v` in your terminal). If not, follow the instructions for your OS at [https://nodejs.org/en/download/](https://nodejs.org/en/download/).

3. Set your credentials:
    1. You need a *.env* file at the top directory to provide credentials. You can copy the content in the *.env.example* file provided in the project and use it as a template
    2. In the file:
        1. Set `ENVIRONMENT` to `sandbox` (for testing) or `production`
        2. Replace the placeholder texts of `SQUARE_ACCESS_TOKEN` and `SQUARE_LOCATION_ID` with your access token and your seller location ID, respectively, for the chosen environment.

    You can find your Square credentials in the Square Developer Dashboard. For more information, see [Getting Started](https://developer.squareup.com/docs/get-started#step-2-create-an-application).

    **IMPORTANT:** You can use your own credentials to test the sample application. If you plan to make a version of this sample application available for other users, you must use the Square [OAuth API](https://developer.squareup.com/docs/oauth-api/overview) to safely manage access to Square accounts.

4. Open a terminal and run the following command to install the sample application's dependencies:

   `npm install`

### Set up your seller account

To run this app, your seller account must have appointments enabled. The appointments service is free in the `sandbox` environment and incurs service charges in the `production` environment. We recommend using the `sandbox` environment for development and testing purposes.

To set up the apointments service in `sandbox`, follow these steps:

#### 1. Initialize test data

1. Ensure your `.env` file has `ENVIRONMENT` set to `sandbox` and provide sandbox environment values for `SQUARE_ACCESS_TOKEN` and `SQUARE_LOCATION_ID`

1. Run the seeding script provided in this sample app to create some services and staff members:
  
   `npm run seed`

   This command will create the following appointment services in your account:
   * Hair Color Treatment
   * Women's Haircut
   * Men's Haircut
   * Shampoo & Blow Dry

   And the following employees:
   * John Smith
   * Amy Johnson

**Note:** You can run `npm run clear` to delete these objects from the sandbox environment when they are no longer needed.  

#### 2. Enable Appointments for your business

1. Log into your [Developer Dashboard](https://developer.squareup.com/apps) in the browser.

1. Click the *Open* button beside your sandbox test account to open the Sandbox Seller Dashboard.

1. In the new tab, click on the *Appointments* tab on the left-hand side panel.

1. Click on the **Get Started** button on the center of the page.

1. Fill in your business information as prompted.

1. On the next page, skip the link to download the Square app for now.

1. If your business has more than one location, make sure to select the location provided in the *.env* file from the dropdown on the Dashboard's *Appointments* page.

You now have appointments enabled!

#### 3. Make your staff bookable

1. Click on the [Staff](https://squareupsandbox.com/appointments/staff) tab on the left-hand side panel.

1. Click on the **Add Employee** button on the right side of the page.

1. Choose one of the two employees, `Amy Johnson` and `John Smith`, created by the seeding command, set optional configurations for the selected employee on the right-side panel, then click or tap the **Save** button on the right bottom corner of the page.

1. On the **Start Your Free Trial of Appointments for Teams** pop-up window, click or tap the **Start Trial** button. Note that use of the `sandbox` environment is always free of charge.

1. Repeat the **Add Employee** step to add the other staff.

1. Both `Amy Johnson` and `John Smith` should now be set up as service providers for the services created by the seeding command.

#### 4. Run the application

1. Run the following command in a terminal to start the server and test the application, in the environment specified in  the *.env* file.

   `npm start`

1. Type `localhost:3000` in your browser's address bar to start the application. Then select one from the list of services on the first page.

## Project organization

This application, as an Express.js project, is organized as follows:

* The *.env* file. The application provides the *.env.example* file in the project's main folder for you to copy as a template, to save it as *.env* in the same directory, and to provide your credentials in the saved *.env* file.
* The *public/* folder. Provides images, JavaScript, and CSS files used to render the pages.
* The *routes/* folder. Creates the following JavaScript files to define the routes to handle requests:
  * The *index.js* file. Redirects the index page via the `/services` route.
  * The *services.js* file. Defines a route to list appointmnet services.
  * The *staff.js* file. Defines a route to list bookable staff members for a booking.
  * The *availability.js* file. Defines routes to search availability based on selected services and staff members.
  * The *contact.js* file. Defines a route to display a customer contact information form before complete booking.
  * The *booking.js* file. Defines routes to get, create, reschedule, and cancel bookings.
* The *util/* folder. Includes the following:
  * The *square-client.js*`* file. Contains the utility code to initialize the Square SDK client.
  * The *date-helpers.js* file. Contains the utility code to create start dates and end dates for search of booking availability
* The *views/* folder. Provides the view (*.ejs*) files to render HTML displays.
