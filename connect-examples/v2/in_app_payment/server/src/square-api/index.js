const { Client, Environment } = require('square');
const { v4: uuidv4 } = require('uuid');
const createOrderLink = require('./create-order-link');

/**
 * Create a shared client, reused on every request.
 * @type {Client}
 */
const client = new Client({
  environment: Environment.Custom,
  customUrl: "https://connect.squareupstaging.com",
  accessToken: process.env.SQUARE_ACCESS_TOKEN,
});

/**
 * Create a in-memory store of upgrade links.
 * In a real system, this would be a database.
 * @type {{[string]: {orderId: string, url: string}}}
 */
const upgradeLinks = {};

/**
 *
 * @param {Upgrade} upgrade
 * @returns {Promise<{id: string, url: string}>}
 */
async function createUpgradeLink(upgrade) {
  const id = uuidv4();
  const orderBody = {
    // create a unique key so this request can be retied if it fails.
    idempotencyKey: id,
    order: {
      locationId: process.env.LOCATION_ID,

      lineItems: [
        {
          basePriceMoney: upgrade.price,
          name: upgrade.name,
          quantity: '1'
        },
      ],
    }
  };

  /**
   * Create the order
   */
  const order = await client.ordersApi.createOrder(orderBody);

  /**
   * Create the order link
   */
  const link = await createOrderLink(client, order.result.order);

  /**
   * Store the link and order for status checking
   */
  upgradeLinks[id] = {
    orderId: order.result.order.id,
    url: link.result.url,
  };

  return {
    url: link.result.url,
    id,
  };
}

async function checkStatus(id) {
  if (!upgradeLinks[id]) {
    return null;
  }

  /**
   * If the upgrade link exists in the database.
   */
  const { orderId } = upgradeLinks[id];

  /**
   * Retrieve it from the orders API.
   */
  const orderResult = await client.ordersApi.retrieveOrder(orderId);
  const order = orderResult.result.order;

  // if order is canceled, then return that.
  if (order.state === 'CANCELED') {
    return 'CANCELED';
  }

  // Otherwise, check if its paid for yet
  if (order.tenders) {
    let remainingValue = order.totalMoney.amount;
    for (const tender of order.tenders) {
      remainingValue -= tender.amountMoney.amount;
    }

    // 0n is a BigInt Zero
    if (remainingValue === 0n) {
      return 'PAID';
    }
  }

  return 'PENDING';
}

module.exports = {
  createUpgradeLink,
  checkStatus,
};
