import express, { Request, Response } from "express";
import squareClient from "../square-client";
import crypto from "crypto";

const router = express.Router();

// Create a card on file for a customer
// For simplicity, we're hardcoding the billing address, and using the postal code from the request body
// which is collected from the web payment form
router.post("/", async (req: Request, res: Response) => {
  try {
    const {
      result: { card },
    } = await squareClient.cardsApi.createCard({
      idempotencyKey: crypto.randomUUID(),
      sourceId: req.body.token,
      card: {
        customerId: req.body.customerId,
        billingAddress: {
          addressLine1: "500 Electric Ave",
          locality: "New York",
          administrativeDistrictLevel1: "NY",
          postalCode: req.body.postalCode,
          country: "US",
        },
      },
    });
    res.json(card);
  } catch (error) {
    console.error("Error creating card:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

export default router;
