// src/squareClient.ts

import * as Square from 'square';
import dotenv from 'dotenv';

dotenv.config();

const squareAccessToken = process.env.SQ_ACCESS_TOKEN as string;

const squareClient = new Square.Client({
  environment: Square.Environment.Sandbox, // or Square.Environment.Sandbox for testing
  accessToken: squareAccessToken,
});

export default squareClient;
