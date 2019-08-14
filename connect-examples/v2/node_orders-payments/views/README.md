# Views
 
This directory contains the templates written in [PugJs](https://pugjs.org/api/getting-started.html). PugJs templates are rendered by [ExpressJs](https://expressjs.com/en/guide/using-template-engines.html)'s `render` function into HTML that is subsequently sent to the client's browser. Each `.pug` file is an individual view that the user can navigate through the app. When working with PugJs one needs to keep in mind that any javascript outside of a script block in the `.pug` file is run on the server side **before** it reaches to the client. To write client side javascript, write javascript within a script block or in a separate file and load it using script tags.
 
## Includes
 
The `/includes` subdirectory contains pug files that can be `included` into other `.pug` files. Including `.pug` files into one another can be thought of as akin to component composition found in other front end frameworks. This allows us to write more modular templates that can be reused in different views.