const {
  giftCardsApi
} = require("./square-client");

async function checkLoginStatus(req, res, next) {
  if (!req.session.loggedIn) {
    res.redirect("/login");
  } else {
    next();
  }
}

/**
 * If the gift card being accessed does not belong to the customer
 * logged in, redirect to the login page
 * */
async function checkCardOwner(req, res, next) {
  if (req.params.gan) {
    try {
      const gan = req.params.gan;
      const { result: { giftCard } } = await giftCardsApi.retrieveGiftCardFromGAN({ gan });
      if (!giftCard.customerIds.includes(req.session.customerId)) {
        res.redirect("/");
      } else {
        // pass the giftCard object to the next middleware
        res.locals.giftCard = giftCard;
        next();
      }
    } catch (error) {
      res.redirect("/");
    }
  }
}

/**
 * We don't want to allow production access to a few endpoints.
 * This helper function verifies that the environment set for the customer is sandbox.
 */
async function checkSandboxEnv(req, res, next) {
  if (process.env[`ENVIRONMENT`].toLowerCase() !== "sandbox") {
    res.redirect("/");
  } else {
    next();
  }
}

/**
 * If the customerId path parameter for the endpoint doesn't match the customerId
 * in the session, don't let the request through.
 */
 async function checkCustomerIdMatch(req, res, next) {
  if (req.session.customerId !== req.params.customerId) {
    res.redirect("/");
  } else {
    next();
  }
}

/**
 * Verify that the card provided by the gan is inactive (i.e. NOT_ACTIVE).
 */
 async function checkInactiveCard(req, res, next) {
  if (res.locals.giftCard.state !== "NOT_ACTIVE") {
    res.redirect("/");
  } else {
    next();
  }
}

module.exports = {
  checkLoginStatus,
  checkCardOwner,
  checkSandboxEnv,
  checkCustomerIdMatch,
  checkInactiveCard
};
