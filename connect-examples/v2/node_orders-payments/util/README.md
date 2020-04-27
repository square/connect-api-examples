# Util


Here we store files that are useful to define, either because they are widely used throughout the project or have a very verbose definition. In the case of `square-connect-client.js`, it is widely used throughout the routes an it is simpler to define a square connect client once and require it into the necessary places in the app.

square-connect-client.js contains the instances of [SquareConnect APIs](https://developer.squareup.com/docs/) that are used throughout the project and also provide utilities to fetch a collection of related square data.