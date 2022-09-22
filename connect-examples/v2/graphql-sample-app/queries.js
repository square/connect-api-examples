const { gql } = require("graphql-request");

const CATALOG_QUERY = gql`
  query CATALOG_QUERY(
    $after: Cursor
    $merchantId: ID!
  ) {
    catalog (
      merchants: { equalToAnyOf: [$merchantId] }
    ) {
      all(after: $after) {
        pageInfo {
          hasNextPage
          endCursor
          startCursor
        }
        nodes {
          __typename
          id
          version
          isDeleted
          updatedAt
          absentAt {
            id
          }
          presentAt {
            id
          }
          presentAtAll
          ... on CatalogCategory {
            name
          }
          ... on CatalogItem {
            abbreviation
            availableElectronically
            availableForPickup
            availableOnline
            category {
              id
            }
            description
            image {
              id
              url
            }
            options {
              id
              name
            }
            labelColor
            modifierListInfos {
              modifierList {
                id
              }
              enabled
              maxSelectedModifiers
              minSelectedModifiers
              modifierOverrides {
                modifier {
                  id
                }
                onByDefault
              }
            }
            name
            productType
            skipModifierScreen
            taxes {
              id
              name
            }
            variations {
              id
              name
            }
          }
          ... on CatalogItemVariation {
            name
            sku
          }
          ... on CatalogItemOptionValue {
            name
            option {
              description
              name
              showColors
            }
          }
          ... on CatalogProductSet {
            allProducts
            name
            productsAll {
              id
            }
            productsAny {
              id
            }
            quantityExact
            quantityMax
            quantityMin
          }
        }
      }
    }
  }
`;

const CUSTOMERS_QUERY = gql`
  query CUSTOMERS_SAMPLE_APP_QUERY(
    $after: Cursor
    $merchantId: ID!
  ) {
    customers(
      after: $after
      filter: {
        merchantId: {
          equalToAnyOf: [ $merchantId ]
        }
      }
    ) {
      pageInfo {
        hasNextPage
        endCursor
        startCursor
      }
      nodes {
        id
        address {
          addressLine1
          addressLine2
          administrativeDistrictLevel1
          postalCode
          locality
          country
        }
        companyName
        createdAt
        givenName
        familyName
        emailAddress
        phoneNumber
        referenceId
        note
      }
    }
  }
`;

const MERCHANTS_QUERY = gql`
  query MERCHANTS_QUERY_SAMPLE_APP(
    $merchantId: ID!
  ) {
    merchants(
      filter: {
        merchants: [ $merchantId ]
      }
    ) {
      nodes {
        id
        country
        currency
        language
        locations {
          nodes {
            id
            businessEmail
            businessName
            createdAt
            description
            name
          }
        }
        mainLocation {
          id
        }
        status
      }
    }
  }
`;

const ORDERS_QUERY = gql`
  query ORDERS_QUERY_SAMPLE_APP(
    $merchantId: ID!
    $locationId: ID!
    $after: Cursor
  ) {
    orders(
      after: $after
      filter: {
        merchants: { equalToAnyOf: [$merchantId] }
        locations: { equalToAnyOf: [$locationId] }
      }
    ) {
      pageInfo {
        hasNextPage
        endCursor
        startCursor
      }
      nodes {
        id
        lineItems {
          uid
          name
          note
          basePrice {
            amount
            currency
          }
          quantity
          quantityUnit {
            precision
          }
          totalMoney {
            amount
            currency
          }
          totalTax {
            amount
            currency
          }
        }
        customer {
          id
        }
        location {
          id
        }
        taxes {
          uid
          appliedMoney {
            amount
            currency
          }
          percentage
        }
      }
    }
  }
`;

/**
 * Define the sample queries that can be executed
 */
const QUERIES = {
  catalog: CATALOG_QUERY,
  customers: CUSTOMERS_QUERY,
  merchants: MERCHANTS_QUERY,
  orders: ORDERS_QUERY
};

module.exports = QUERIES;
