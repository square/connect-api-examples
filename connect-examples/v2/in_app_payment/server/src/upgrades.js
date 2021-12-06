/**
 * @typedef Upgrade
 * @property {string} sku
 * @property {string} name
 * @property {Object} price
 * @property {number} price.amount
 * @property {string} price.currency
 */

/**
 * @type {Upgrade[]}
 */
const upgrades = [
  {
    sku: '000001',
    name: 'One Month Upgrade',
    price: {
      amount: 500,
      currency: 'USD'
    }
  },
  {
    sku: '000002',
    name: 'Two Month Upgrade',
    price: {
      amount: 900,
      currency: 'USD'
    }
  },
  {
    sku: '000003',
    name: '6 Month Upgrade',
    price: {
      amount: 2500,
      currency: 'USD'
    }
  },
  {
    sku: '000004',
    name: '1 Year Upgrade',
    price: {
      amount: 4500,
      currency: 'USD'
    }
  }
];

module.exports = upgrades;
