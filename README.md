# Square Connect API Examples

This repository contains code samples demonstrating the functionality of the
Square Connect API and templates to simplify use of our SDKs and APIs.


## In this repository

* `connect-examples/oauth` - samples implementing OAuth
* `connect-examples/v1` - samples demonstrating Connect v1 functionality
* `connect-examples/v2` - samples demonstrating Connect v2 functionality
* `templates/` - code blocks and function definitions to simplify common usage


## Getting help

Complete documentation for Square APIs is available at
[docs.connect.squareup.com].

If you have questions about Square Connect API features or implementation,
you can ask for help on [Stack Overflow] or in our [Slack community].


--------------------------------------------------------------------------------

## Release notes

### 2018-08-15
All V2 examples have been updated to remove vulnerable libraries and to ensure
consistency between examples.

### 2018-08-13

Updating directory structure and submitting inaugural code templates.

### 2017-05-23

On 23 May 2017, we improved our SDKs to expand their functionality and make them
easier to use. Unfortunately, some of the changes are not backward compatible.
You can read more about the changes in our [SDK blog post].


### 2016-02-16

On 16 February 2016, we changed the way merchant data is represented in the
Connect APIs. The data change also altered API behavior slightly. The samples
in this repository reflect this altered behavior. If you created your Connect
API application *before* 16 February, you can find samples that reflect your
application's behavior in the `v1` directory.


[//]: # "Link anchor definitions"
[SDK blog post]: https://medium.com/square-corner-blog/announcing-our-new-versions-of-our-client-sdks-1336d26e8099
[Stack Overflow]: https://stackoverflow.com/questions/tagged/square-connect
[Slack community]: https://squ.re/2Hks3YE
[docs.connect.squareup.com]: https://docs.connect.squareup.com
