# Overview


This sample web application integrates the Square [Bookings API](https://developer.squareup.com/reference/square/bookings-api) and showcases some of its functionality, including:
* Create booking
* Update booking
* Cancel booking
* Search availabilities
* List team member booking profiles
* Retrieve team member booking profile

In addition to the Bookings API, the application uses the following Square APIs for an integrated experience:

* The [Catalog API](https://developer.squareup.com/reference/square/catalog-api) to create and retrieve appointment service type catalog objects
* The [Customers API](https://developer.squareup.com/reference/square/customers-api) to create and retrieve customer profiles in the seller's Customer Directory
* The [Locations API](https://developer.squareup.com/reference/square/locations-api) to get information about the seller's business location to be used throughout the application
* The [Teams API](https://developer.squareup.com/reference/square/team-api) to retrieve team member details

# Setup

## Set up the application
1. Ensure that you have npm installed with Node.js version v10 or later (run `npm -v` in your terminal). If not, follow the instructions for your OS: [https://www.npmjs.com/get-npm](https://www.npmjs.com/get-npm) 

2. Set your credentials: 
    1. You need a `.env` file at the top directory to provide credentials. You can copy the content in the `.env.example` file provided in the project and use it as a template
    2. In the file:
        1. Set `ENVIRONMENT` to `sandbox` (for testing) or `production`
        2. Provide values for `SQUARE_APPLICATION_ID`, `SQUARE_ACCESS_TOKEN`, and `SQUARE_LOCATION_ID` for the corresponding environment
 
    For more information, see [Getting Started](https://developer.squareup.com/docs/get-started#step-2-create-an-application). The **Create an application** section explains where you can find your credentials information. 

    **WARNING:** Remember to use your own credentials only for testing the sample application. If you plan to make a version of this sample application available for your own purposes, use the Square [OAuth API](https://developer.squareup.com/docs/oauth-api/overview) to safely manage access to Square accounts. 

3. Open your terminal and install the sample application's dependencies with the following command: 
 
   `npm install`

## Set up your seller account

In order to run this app, you need to first ensure your seller account has appointments enabled. The appointments service is **free** in the `sandbox` environment and is a paid service in the `production` environment. We recommend using the `sandbox` environment for development and testing purposes.

To set up the apointments service in `sandbox`, follow these steps:

### 1. Initialize test data

1. Ensure your `.env` file has `ENVIRONMENT` set to `sandbox` and provide sandbox environment values for `SQUARE_APPLICATION_ID`, `SQUARE_ACCESS_TOKEN`, and `SQUARE_LOCATION_ID`

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

**Note:** You may delete these objects from the sandbox environment when you wish by running `npm run clear` 

### 2. Enable Appointments for your business

1. Log into [Developer Dashboard](https://developer.squareup.com/apps) in the browser

1. Click the *Open* button beside your sandbox test account to open the sandbox seller dashboard. Follow the instructions in this and the next section within the sandbox seller dashboard

1. If your business has more than 1 location, make sure you select the location provided in the `.env` file from the dropdown on the Dashboard's *Home page*

1. Click on the *Appointments* tab on the left-hand side panel

1. Click on the *Get Started* button on the center of the page

1. Fill in your business information as prompted

1. On the next page, skip the link to download the Square app for now

1. You now have appointments enabled

### 3. Make your staff bookable

1. Click on the *Staff* tab on the left-hand side panel

1. Click on the *Add Employee* button on the right side of the page

1. You should see 2 available employees if you successfully ran the seeding command: *Amy Johnson* and *John Smith*. Choose either one of the them and set optional configurations for the employee on the right-side panel, then click the *Save* button on the right bottom corner of the page

1. You will get a pop up to *Start Your Free Trial of Appointments for Teams*. Click on the *Start Trial* button. Note that if you are in the `sandbox` environment you will not be charged

1. Repeat the *Add Employee* step to add the other staff

1. Both Amy Johnson and John Smith should now be set up as service providers for the services that were created by the seeding command

## 4. Run the application

1. Test the application and run the server. Depending on the content of the `.env` file, the application runs in the Square sandbox or production environment.

   `npm start` 

1. Enter **localhost:3000** in your browser. You should see a list of services to select on the first page.

# Project organization

This Express.js project is organized as follows:

*   **.env** Square provides a `.env.example` file. You should make a copy of this file and save it as `.env`. You should provide your credentials in the `.env` file
*   **public/** Provides images, JavaScript, and CSS files used to render the pages
*   **routes/** The following JavaScript files define the routes to handle requests:
    *   **index.js** Redirect the index page to `/services`
    *   **services.js** Provides a route to list appointmnet services
    *   **staff.js** Provides a route to list bookable staff members for a service
    *   **availability.js** Provides routes to search availability based on service and staff member selected
    *   **contact.js** Provides a route to display a customer contact information form prior to complete booking
    *   **booking.js** Provides routes to get, create, reschedule, and cancel bookings 
*   **util/** Includes the following:
    *   **square-client.js** The utility code initializes the Square SDK client
    *   **date-helpers.js** The utility code to create start & end dates for search booking availability
*   **views/** Provides the view (.ejs) files

