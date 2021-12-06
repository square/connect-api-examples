const { Client, Order } = require('square');

const orderLinkSchema = {
  type() {},
  validateBeforeMap() {return []},
  map(value) { return value },
}


/**
 * @param {Client} client
 * @param {Order} order
 * @returns Promise<Order>
 */
function creatOrderLink(client, order) {
  /**
   * Create the payment link
   * NOTE: This is accessing interal methods on the square library
   * to allow for creating an alpha request type. After GA, these
   * should be updated.
   */
  const req = client.checkoutApi.createRequest('POST', '/v2/checkout-links/order-link')
  req.json({
    order_id: order.id,
    location_id: process.env.LOCATION_ID,
  });
  return req.callAsJson(orderLinkSchema);
}

module.exports = creatOrderLink;
