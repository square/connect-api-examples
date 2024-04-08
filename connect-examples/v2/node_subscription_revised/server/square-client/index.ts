// src/squareClient.ts

import * as Square from "square";
import dotenv from "dotenv";

dotenv.config();

const squareAccessToken = process.env.SQ_ACCESS_TOKEN as string;

const squareClient = new Square.Client({
  bearerAuthCredentials: {
    accessToken: squareAccessToken,
  },
  environment: Square.Environment.Sandbox,
});

export default squareClient;
