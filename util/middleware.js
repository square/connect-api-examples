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
    const gan = req.params.gan;
    const { result: { giftCard } } = await giftCardsApi.retrieveGiftCardFromGAN({ gan });
    if (!giftCard.customerIds.includes(req.session.customerId)) {
      res.redirect("/login");
    } else {
      // pass the giftCard object to the next middleware
      res.locals.giftCard = giftCard;
      next();
    }
  }
}

module.exports = {
  checkLoginStatus,
  checkCardOwner
};