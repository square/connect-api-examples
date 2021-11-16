# README

## Square Online Store Snippets Example

### Setup 

The sample app currently runs on: 

  Ruby Version: 2.5.8 
  
  Rails Version: 6.1.3.1 

1. To run this app locally using Docker Compose, you must have first have [Docker Compose installed](https://docs.docker.com/compose/install/)
2. Once installed you can run `docker-compose build` to build the application web container
3. Then run `docker-compose run web rails webpacker:install` to build the application's webpack manifest
4. Update the .env file at the root with following values: (WARNING: never upload .env with your credentials/secrets)
```
SQUARE_APPLICATION_ID=your-app-id
SQUARE_APPLICATION_SECRET=your-app-secret
```
5. Then run `docker-compose up` to start the rails web server 

The application will then run and be accessible at http://localhost:3000

## Feedback
Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!