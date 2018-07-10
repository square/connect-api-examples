# README

The root page has the selections for the different implementations.

Ruby version: 2.5.1

Rails version: 5.2.0

To get the app running:

* Run bundler

```
bundle install
bundle exec rake db:create db:migrate # (No db in example, but keeps rails from complaining)
```

* Update the .env file at the root with following values:
(<b>WARNING</b>: never upload .env with your credentials/access_token)

```
SQUARE_APPLICATION_ID=your-app-id
SQUARE_ACCESS_TOKEN=your-access-token
SQUARE_LOCATION_ID=your-location-id
```

* Run the application: `bin/rails s`

* The application runs in `http://localhost:3000/`

* [Testing using the API sandbox](https://docs.connect.squareup.com/articles/using-sandbox)
