/*
Copyright 2021 Square Inc.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

const express = require("express",);
const logger = require("morgan",);
const cookieParser = require("cookie-parser",);

const routes = require("./routes/index",);
const app = express();

require("./util/square-client",);

// set the view engine to ejs
app.set("view engine", "ejs",);

app.use(logger("dev",),);

app.use(express.static(__dirname + "/public",),);

app.use(express.json(),);
app.use(express.urlencoded({
  extended: false,
},),);

app.use(cookieParser(),);
app.use("/", routes,);

// catch 404 and forward to error handler
app.use(function (req, res, next,) {
  const err = new Error("Not Found",);
  err.status = 404;
  next(err,);
},);

// error handlers
// For simplicity, we print all error information
app.use(function (err, req, res, next,) {
  res.status(err.status || 500,);
  res.render("pages/error", {
    // If it is a response error then format the JSON string, if not output the error
    error: err.errors ? JSON.stringify(err.errors, null, 4,) : err.stack,
    message: err.message,
    status: err.status,
  },);
},);


module.exports = app;
