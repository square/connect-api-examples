import express, { Request, Response } from 'express';
import squareClient from '../square-client';

const router = express.Router();

// Fetches catalog items based on the category IDs provided in the request body
router.post('/', async (req: Request, res: Response) => {
    try {
      // Fetch items using the imported Square client
      const { result: {items} } = await squareClient.catalogApi.searchCatalogItems({
        categoryIds: req.body.categoryIds
      })
      // Fetch all catalog images
      const {result:{objects}} = await squareClient.catalogApi.searchCatalogObjects({
        objectTypes: [
          "IMAGE"
        ]
      })
      // Map the image to the item
      const final:any = []
      items?.forEach(item => {
        objects?.forEach(image => {
          if(image.id === item?.itemData?.imageIds?.[0]) {
           final.push({...item, image: image.imageData?.url})
          }
        })
      })
  
      // Send the new items with mapped images as the response
      res.json(final);
    } catch (error) {
      console.error('Error fetching Square Catalog Items:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  });

  export default router;