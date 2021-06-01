const {
  giftCardsApi
} = require("../util/square-client");

async function checkAuth(req, res, next) {
  if (!req.session.loggedIn) {
    res.redirect("/login");
  } else {
    next();
  }
}

async function checkCardOwner(req, res, next) {
  if (req.params.gan) {
    // If the gift card being accessed does not belong to the customer
    // logged in, redirect to the login page
    const gan = req.params.gan;
    const { result: { giftCard } } = await giftCardsApi.retrieveGiftCardFromGAN({ gan });
    if (!giftCard.customerIds.includes(req.session.customerId)) {
      res.redirect("/login");
    } else {
      res.locals.giftCard = giftCard;
      next();
    }
  }
}

module.exports = {
  checkAuth,
  checkCardOwner
};