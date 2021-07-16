# Useful Links

* [Node.js SDK Page](https://developer.squareup.com/docs/sdks/nodejs)
* [Bookings API Overview](https://developer.squareup.com/docs/bookings-api/what-it-is)
* [Bookings API Reference](https://developer.squareup.com/reference/square/bookings-api)

# Bookings API Sample App

* [Overview](#overview)
* [Setup](#setup)
* [Project organization](#project-organization)
* [Application flow](#application-flow)

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

1. Click on the *Staff* tab on the left-hand side panel.

1. Click on the **Add Employee** button on the right side of the page.

1. Choose one of the two employees, `Amy Johnson` and `John Smith`, created by the seeding command, set optional configurations for the selected employee on the right-side panel, then click or tap the **Save** button on the right bottom corner of the page.

1. On the **Start Your Free Trial of Appointments for Teams** pop-up window, click or tap the **Start Trial** button. Note that use of the `sandbox` environment is always free of charge.

1. Repeat the **Add Employee** step to add the other staff.

1. Both `Amy Johnson` and `John Smith` should now be set up as service providers for the services created by the seeding command.

### Run the application

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
  * The *square-client.js* file. Contains the utility code to initialize the Square SDK client.
  * The *date-helpers.js* file. Contains the utility code to create start dates and end dates for search of booking availability
* The *views/* folder. Provides the view (*.ejs*) files to render HTML displays.

## Application flow

The application flow is explained with the assumption that you are familiar with [Express](https://expressjs.com/) (the web framework for Node.js).

### Select services page

<img src="./bin/images/select-services-screenshot.png" width="700">

The application's landing page is the Select Services page, where you can select one of the business services to book. The left-hand pane provides information about your business and contact information.

When you open the application in the browser at <http://localhost:3000>, you are redirected to the /services route. The handler for this route retrieves all [**APPOINTMENTS_SERVICE**](https://developer.squareup.com/reference/square/enums/CatalogItemProductType#value-APPOINTMENTS_SERVICE) type services for the business location using the [Search Catalog Items endpoint](https://developer.squareup.com/reference/square/catalog/search-catalog-items) in the Catalog API.

See code in services.js:

```javascript
router.get("/", async (req, res, next) => {
  const cancel = req.query.cancel;
  try {
    let { result: { items } } = await catalogApi.searchCatalogItems({
      enabledLocationIds: [ locationId ],
      productTypes: [ "APPOINTMENTS_SERVICE" ]
    });

    if (!items) {
      items = [];
    }

    res.render("pages/select-service", { cancel, items });
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

### Select staff page

<img src="./bin/images/select-staff-screenshot.png" width="700">

After selecting service on the previous page, the application redirects you to the *Select Staff* page (i.e. _/staff/:serviceId?version_), where you can select one or any active team member. When choosing the *Any team member* option, the handler retrieves all bookable active team members that can perform the service by cross-referencing multiple APIs. This is necessary due to the fact team members are managed by both the Team API and the Bookings API. The handler first needs to find all active team members that are capable of providing the chosen service using the Team API. Then, it needs to verify that those team members are bookable, using the Bookings API. The major endpoints used in the handler are:

* [List Team Member Booking Profiles](https://developer.squareup.com/reference/square/bookings/list-team-member-booking-profiles)
* [Search Team Members](https://developer.squareup.com/reference/square/team/search-team-members)

For more information, see code in staff.js:

```javascript
router.get("/:serviceId", async (req, res, next) => {
  const serviceId = req.params.serviceId;
  const serviceVersion = req.query.version;
  try {
    // Send request to get the service associated with the given item variation ID, and related objects.
    const retrieveServicePromise = catalogApi.retrieveCatalogObject(serviceId, true);

    // Send request to list staff booking profiles for the current location.
    const listBookingProfilesPromise = bookingsApi.listTeamMemberBookingProfiles(true, undefined, undefined, locationId);

    // Send request to list all active team members for this merchant at this location.
    const listActiveTeamMembersPromise = teamApi.searchTeamMembers({
      query: {
        filter: {
          locationIds: [ locationId ],
          status: "ACTIVE"
        }
      }
    });

    // Wait until all API calls have completed.
    const [ { result: services }, { result: { teamMemberBookingProfiles } }, { result: { teamMembers } } ] =
      await Promise.all([ retrieveServicePromise, listBookingProfilesPromise, listActiveTeamMembersPromise ]);

    // We want to filter teamMemberBookingProfiles by checking that the teamMemberId associated with the profile is in our serviceTeamMembers.
    // We also want to verify that each team member is ACTIVE.
    const serviceVariation = services.object;
    const serviceItem = services.relatedObjects.filter(relatedObject => relatedObject.type === "ITEM")[0];

    const serviceTeamMembers = serviceVariation.itemVariationData.teamMemberIds || [];
    const activeTeamMembers = teamMembers.map(teamMember => teamMember.id);

    const bookableStaff = teamMemberBookingProfiles
      .filter(profile => serviceTeamMembers.includes(profile.teamMemberId) && activeTeamMembers.includes(profile.teamMemberId));

    res.render("pages/select-staff", { bookableStaff, serviceItem, serviceVariation, serviceVersion });
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

### Select availability page

<img src="./bin/images/select-availability-screenshot.png" width="700">

Next you need to select a start time for your booking. In the *Select Availability* page we allow booking an appointment 4 hours to 30 days in advance. The handler for this route (_/availability/:staffId/:serviceId?version_) uses the [Search Availability endpoint](https://developer.squareup.com/reference/square/bookings/search-availability) in the Bookings API to retrieve the available times for a specific service and team member(s).

For more information, see code in availability.js:

```javascript
router.get("/:staffId/:serviceId", async (req, res, next) => {
  const serviceId = req.params.serviceId;
  const serviceVersion = req.query.version;
  const staffId = req.params.staffId;
  const startAt = dateHelpers.getStartAtDate();
  const searchRequest = {
    query: {
      filter: {
        locationId,
        segmentFilters: [
          {
            serviceVariationId: serviceId,
          },
        ],
        startAtRange: {
          endAt: dateHelpers.getEndAtDate(startAt).toISOString(),
          startAt: startAt.toISOString(),
        },
      }
    }
  };
  try {
    // get service item - needed to display service details in left pane
    const retrieveServicePromise = catalogApi.retrieveCatalogObject(serviceId, true);
    let availabilities;
    // additional data to send to template
    let additionalInfo;
    // search availability for the specific staff member if staff id is passed as a param
    if (staffId === ANY_STAFF_PARAMS) {
      const [ services, teamMembers ] = await searchActiveTeamMembers(serviceId);
      searchRequest.query.filter.segmentFilters[0].teamMemberIdFilter = {
        any: teamMembers,
      };
      // get availability
      const { result } = await bookingsApi.searchAvailability(searchRequest);
      availabilities = result.availabilities;
      additionalInfo = {
        serviceItem: services.relatedObjects.filter(relatedObject => relatedObject.type === "ITEM")[0],
        serviceVariation: services.object
      };
    } else {
      searchRequest.query.filter.segmentFilters[0].teamMemberIdFilter = {
        any: [
          staffId
        ],
      };
      // get availability
      const availabilityPromise = bookingsApi.searchAvailability(searchRequest);
      // get team member booking profile - needed to display team member details in left pane
      const bookingProfilePromise = bookingsApi.retrieveTeamMemberBookingProfile(staffId);
      const [ { result }, { result: services }, { result: { teamMemberBookingProfile } } ] = await Promise.all([ availabilityPromise, retrieveServicePromise, bookingProfilePromise ]);
      availabilities = result.availabilities;
      additionalInfo = {
        bookingProfile: teamMemberBookingProfile,
        serviceItem: services.relatedObjects.filter(relatedObject => relatedObject.type === "ITEM")[0],
        serviceVariation: services.object
      };
    }
    // send the serviceId & serviceVersion since it's needed to book an appointment in the next step
    res.render("pages/availability", { availabilities, serviceId, serviceVersion, ...additionalInfo });
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

### Contact details page

<img src="./bin/images/contact-details-screenshot.png" width="700">

The next page is the *Contact Details* page - in order to create a booking we need to create a [Customer](https://developer.squareup.com/reference/square/objects/Customer) using [Create Customer endpoint](https://developer.squareup.com/reference/square/customers/create-customer) in the Customers API. The handler for this route (_/booking/create_) creates the customer or retrieves the existing customer for the given customer details and then calls [Create Booking endpoint](https://developer.squareup.com/reference/square/bookings-api/create-booking) in the Bookings API to create the booking.

For more information, see code in booking.js:

```javascript
router.post("/create", async (req, res, next) => {
  const serviceId = req.query.serviceId;
  const serviceVariationVersion = req.query.version;
  const staffId = req.query.staffId;
  const startAt = req.query.startAt;

  const customerNote = req.body.customerNote;
  const emailAddress = req.body.emailAddress;
  const familyName = req.body.familyName;
  const givenName = req.body.givenName;

  try {
    // Retrieve catalog object by the variation ID
    const { result: { object: catalogItemVariation } } = await catalogApi.retrieveCatalogObject(serviceId);
    const durationMinutes = convertMsToMins(catalogItemVariation.itemVariationData.serviceDuration);

    // Create booking
    const { result: { booking } } = await bookingsApi.createBooking({
      booking: {
        appointmentSegments: [
          {
            durationMinutes,
            serviceVariationId: serviceId,
            serviceVariationVersion,
            teamMemberId: staffId,
          }
        ],
        customerId: await getCustomerID(givenName, familyName, emailAddress),
        customerNote,
        locationId,
        startAt,
      },
      idempotencyKey: uuidv4(),
    });

    res.redirect("/booking/" + booking.id);
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

### Confirmation page

<img src="./bin/images/confirmation-page-screenshot.png" width="700">

If the booking was created successfully, you will be redirected to the *Confirmation* page. This page contains information about your scheduled appointment, such as the service name and description, the staff member, location information, date and time, etc. You will also see two different buttons to either *reschedule* or *cancel* your appointment. The handler for this route (_/booking/:bookingId_) aggregates the data to be rendered on this page by using several APIs mentioned in previous steps, but most importantly, the [Retrieve Booking endpoint](https://developer.squareup.com/reference/square/bookings-api/retrieve-booking) in the Bookings API, which returns information about a specific booking, based on the booking ID.

For more information, see code in booking.js:

```javascript
router.get("/:bookingId", async (req, res, next) => {
  const bookingId = req.params.bookingId;
  try {
    // Retrieve the booking provided by the bookingId.
    const { result: { booking } } = await bookingsApi.retrieveBooking(bookingId);

    const serviceVariationId = booking.appointmentSegments[0].serviceVariationId;
    const teamMemberId = booking.appointmentSegments[0].teamMemberId;

    // Make API call to get service variation details
    const retrieveServiceVariationPromise = catalogApi.retrieveCatalogObject(serviceVariationId, true);

    // Make API call to get team member details
    const retrieveTeamMemberPromise = bookingsApi.retrieveTeamMemberBookingProfile(teamMemberId);

    // Wait until all API calls have completed
    const [ { result: service }, { result: { teamMemberBookingProfile } } ] =
      await Promise.all([ retrieveServiceVariationPromise, retrieveTeamMemberPromise ]);

    const serviceVariation = service.object;
    const serviceItem = service.relatedObjects.filter(relatedObject => relatedObject.type === "ITEM")[0];

    res.render("pages/confirmation", { booking, serviceItem, serviceVariation, teamMemberBookingProfile });
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

### Reschedule booking page

<img src="./bin/images/modify-booking-screenshot.png" width="700">

You can reschedule bookings by clicking on the *Reschedule booking* button on the confirmation page. Once a new time slot is selected the controller for the route (POST _/booking/:bookingId/reschedule_) uses the [Update Booking endpoint](https://developer.squareup.com/reference/square/bookings-api/update-booking) in the Bookings API to modify the existing booking.

For more information, see code in booking.js:

```javascript
router.get("/:bookingId/reschedule", async (req, res, next) => {
  const bookingId = req.params.bookingId;
  try {
    // Retrieve the booking provided by the bookingId.
    const { result: { booking } } = await bookingsApi.retrieveBooking(bookingId);
    const { serviceVariationId, teamMemberId, serviceVariationVersion } = booking.appointmentSegments[0];
    const startAt = dateHelpers.getStartAtDate();
    const searchRequest = {
      query: {
        filter: {
          locationId,
          segmentFilters: [
            {
              serviceVariationId,
              teamMemberIdFilter: {
                any: [ teamMemberId ],
              }
            },
          ],
          startAtRange: {
            endAt: dateHelpers.getEndAtDate(startAt).toISOString(),
            startAt: startAt.toISOString(),
          },
        }
      }
    };
    // get availability
    const { result: { availabilities } } = await bookingsApi.searchAvailability(searchRequest);
    res.render("pages/reschedule", { availabilities, bookingId, serviceId: serviceVariationId, serviceVersion: serviceVariationVersion });
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

and

```javascript
/**
 * POST /booking/:bookingId/reschedule
 *
 * Update an existing booking, you may update the starting date
 */
router.post("/:bookingId/reschedule", async (req, res, next) => {
  const bookingId = req.params.bookingId;
  const startAt = req.query.startAt;

  try {
    const { result: { booking } } = await bookingsApi.retrieveBooking(bookingId);
    const updateBooking = {
      startAt,
      version: booking.version,
    };

    const { result: { booking: newBooking } } = await bookingsApi.updateBooking(bookingId, { booking: updateBooking });

    res.redirect("/booking/" + newBooking.id);
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```

### Cancel booking action

<img src="./bin/images/cancel-booking-screenshot.png" width="700">

You can cancel an appointment by clicking on the **Cancel booking** button in the confirmation page. The handler for this route (POST _/booking/:bookingId/delete_) uses the [Cancel Booking endpoint](https://developer.squareup.com/reference/square/bookings-api/cancel-booking) in the Bookings API to cancel your appointment. The handler then redirects to the initial landing page (_/services_), with an appropriate toast. This can be seen in the screenshot above.

For more information, see code in booking.js:

```javascript
/**
 * POST /booking/:bookingId/delete
 *
 * delete a booking by booking ID
 */
router.post("/:bookingId/delete", async (req, res, next) => {
  const bookingId = req.params.bookingId;

  try {
    const { result: { booking } } = await bookingsApi.retrieveBooking(bookingId);
    await bookingsApi.cancelBooking(bookingId, { bookingVersion: booking.version });

    res.redirect("/services?cancel=success");
  } catch (error) {
    console.error(error);
    next(error);
  }
});
```
