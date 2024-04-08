import express, { Request, Response } from "express";
import squareClient from "../square-client";

const router = express.Router();

// Get all customers from Square
router.get("/", async (req: Request, res: Response) => {
  try {
    // Fetch customers using the imported Square client
    const {
      result: { customers },
    } = await squareClient.customersApi.listCustomers();

    // Send the customers as the response
    res.json(customers);
  } catch (error) {
    console.error("Error fetching Square Customers:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

// Get a specific customer from Square
router.get("/:customerId", async (req: Request, res: Response) => {
  try {
    // Fetch the customer using the imported Square client
    const {
      result: { customer },
    } = await squareClient.customersApi.retrieveCustomer(req.params.customerId);

    // Send the customer as the response
    res.json(customer);
  } catch (error) {
    console.error("Error fetching Square Customer:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

export default router;
