# In App Payment Demo (Server Side)

This is a mock api server that would be used by mobile apps to implemnt Square in app payments. A real server would
 use a database and authentication, but for simplicity, the demo is unauthenticated and uses an in memory store. The
 mock server is written in Javascript and runs on NodeJs.

This demo usages:
* Express - for request handling
* Square API - for Square API calls

## Getting Up and Running

1. To start this server you will need NodeJS installed. A good way to install it is with Node Version Manager, which
 is a tool to manage multiple node versions on one machine. This will allow you to install the specific version this
  project needs. To get started, install [NVM](https://github.com/nvm-sh/nvm).
2. Next move into this folder and run `nvm use`.
3. Then install the node dependecies for the project with `npm install`.
4. Copy the example env file and add your Square developer personal access token and location token. `cp .env.example
 .env`.
5. Run the server with `npm start`.
