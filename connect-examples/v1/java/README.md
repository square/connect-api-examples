# Connect API v1 Java Examples

These examples demonstrate performing common Connect API tasks in Java.

These examples use the [Unirest HTTP library](http://unirest.io/java.html), which is
installed automatically during the **Compile with Maven** step below.

These examples require Java SE 8 or later.

## Setup

### Specify your application credentials

In order for the samples to work, you must specify values for the strings labeled `REPLACE_ME`
at the top of each sample. Comments indicate the value you should specify.


### Compile with Maven

From this directory, run `mvn install` to compile the application.


## Run the examples

To run a particular example, execute the following from this directory, providing the name 
of the example where indicated:

    java -cp target/shaded-connectexamples-1.0-SNAPSHOT.jar com.squareup.connectexamples.NAME_OF_EXAMPLE

Valid example names are:

* `PaymentsReporter`
* `ItemManager`
* `WebhookHandler`