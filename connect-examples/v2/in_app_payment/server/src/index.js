const express = require('express');
const bodyParser = require('body-parser');

const logger = require('./logger');
const upgrades = require('./upgrades');
const { createUpgradeLink, checkStatus } = require('./square-api');

const app = express();

/**
 * Parse request body as json
 */
app.use(bodyParser.json());

/**
 * Logging for every request
 */
app.use(logger);

/**
 * Welcome endpoint
 */
app.get('/', (req, res) => {
  res.send('Welcome to the Square In-App purchase demo');
});

/**
 * List upgrades endpoint
 */
app.get('/get-upgrades', (req, res) => {
  res.json({
    upgrades,
  });
});

/**
 * Create Upgrade Link endpoint
 */
app.post('/generate-upgrade-link', async (req, res) => {
  /**
   * Look up the sku in the upgrade set
   */
  const upgrade = upgrades.find((u) => u.sku === req.body.sku);
  if (!upgrade) {
    /**
     * If is is not found, then 404.
     */
    return res.status(404).send('Sku Not Found');
  }

  try {
    /**
     * Create the upgrade link and return it.
     */
    const {url, id} = await createUpgradeLink(upgrade);
    res.json({
      url,
      id,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({
      error: error,
    });
  }
});

/**
 * Check the status of an upgrade link
 */
app.get('/check-status/:id', async (req, res) => {
  try {
    /**
     * Get the status from the order API
     */
    const status = await checkStatus(req.params.id);

    if (!status) {
      /**
       * If the upgrade link was not found in the database.
       */
      return res.status(404).send('Upgrade Link Not Found');
    }

    res.json({
      status,
    });
   } catch (error) {
    console.error(error);
    res.status(500).json({
      error: error.message,
    });
  }
});


app.listen(process.env.PORT, () => {
  console.log(`App is listening to port ${process.env.PORT}`);
});
