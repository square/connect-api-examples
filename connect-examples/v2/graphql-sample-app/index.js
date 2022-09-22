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

const { program } = require("commander");
const { GraphQLClient } = require("graphql-request");
const prompts = require("prompts");
const QUERIES = require("./queries");
require("dotenv").config();

const GRAPHQL_SANDBOX_SERVER_URL = "https://connect.squareupsandbox.com/alpha/graphql";
const GRAPHQL_EXPLORER_URL = "https://developer.squareup.com/explorer/graphql";

/**
 * Initialize and return a new GraphQL client
 * @returns GraphQLClient
 */
function getGraphQlClient() {
  // Check that the required .env variable exists
  if (!process.env["SQUARE_ACCESS_TOKEN"]) {
    console.error(".env file missing required field \"SQUARE_ACCESS_TOKEN\"");
    process.exit(1);
  }
  return new GraphQLClient(GRAPHQL_SANDBOX_SERVER_URL, {
    headers: {
      "Content-Type": "application/json",
      accept: "application/json",
      authorization: `Bearer ${process.env["SQUARE_ACCESS_TOKEN"]}`
    }
  });
}

/**
 * Get all the required query variables from the query
 * Ex. if the query is
 * {
 *   query customersQuery($merchantId: String!) {
 *     customers(
 *       filter: {...}
 *     ) {
 *       nodes {...}
 *     }
 *   }
 * }
 * then the query variables returned is ["merchantId"]
 *
 * All the GraphQL queries require query variables.
 *
 * @param {gql} query
 * @return {String[]}
 */
function getQueryVariables(query) {
  const variables = query.match(/\$[a-zA-Z]+:/g);
  return variables && variables.map(variable => variable.slice(1, -1));
}

/**
 * Executes the GraphQL query provided with the query variables
 * and logs the list of objects returned
 * @param {String} queryName
 * @param {Object} queryVariables
 */
async function query(queryName, queryVariables) {
  const sampleQuery = QUERIES[queryName];
  try {
    let hasNextPage = true;
    let prevEndCursor = null;
    let pageNum = 1;
    console.log(`Running the query: ${sampleQuery}`);
    console.log(`Here are the results from the ${queryName} query:`);
    while (hasNextPage) {
      queryVariables.after = prevEndCursor ? prevEndCursor : null;
      const data = await getGraphQlClient().request(sampleQuery, queryVariables);
      const dataObj = queryName === "catalog" ? data[queryName].all : data[queryName];
      if (!dataObj) {
        console.log("No objects returned from query");
        return;
      }
      const { pageInfo, nodes } = dataObj;
      for (const obj of nodes) {
        console.log("===================================================");
        console.dir(obj, { depth: null });
      }
      // We need to fetch more data if hasNextPage is true
      if (pageInfo) {
        hasNextPage = pageInfo.hasNextPage;
        prevEndCursor = pageInfo.endCursor;
      } else {
        hasNextPage = false;
      }
      pageNum++;
    }
  } catch (err) {
    console.error(`Error when executing ${queryName} query`, err);
  }
  console.log(`Query execution is complete. You can also execute queries & view the GraphQL schema at ${GRAPHQL_EXPLORER_URL}`);
}

program
  .command("query")
  .description("Execute a GraphQL query against Square's GraphQL endpoint")
  .action(async () => {
    const response = await prompts([
      {
        choices: Object.keys(QUERIES).map(key => ({
          title: key,
          value: key
        })),
        message: "Which GraphQL query would you like to execute?",
        name: "query",
        type: "select"
      }
    ]);
    const queryVariables = {};
    const variables = getQueryVariables(QUERIES[response.query]);
    if (variables) {
      for (const variable of variables) {
        if (variable === "after") {
          continue; // skip the $after variable since we populate it from the pageInfo response
        }
        let variablesResponse;
        // all variables are required so don't allow empty strings
        while (!variablesResponse || !variablesResponse[variable] ||
          (variablesResponse[variable].length && !variablesResponse[variable][0])
        ) {
          variablesResponse = variable.endsWith("s") ?
            await prompts([
              {
                initial: "",
                message: `Enter in ${variable}. If multiple then separate them by a comma (ex. merchantId1, merchantId2) (required)`,
                name: variable,
                separator: ",",
                type: "list",
              }
            ]) : await prompts([
              {
                message: `Enter in your ${variable} (required)`,
                name: variable,
                type: "text"
              }
            ]);
          queryVariables[variable] = variablesResponse[variable];
        }
      }
    }
    await query(response.query, queryVariables);
  });

program.parse(process.argv);
