# Script

Use the script in this repository to help populate your sandbox catalog with sample items.

## Running the script
For more information on how to run the script, visit our [Quick Start Guide](https://developer.squareup.com/docs/orders-api/quick-start/step-3). The script can be found at `seed-catalog.js`.

To run the script, first go to the root directory of your app, then run:
```
npm run seed
```

Go to `localhost:3000` and refresh your app. It should now be populated with test catalog items:

## Clearing Your Sandbox Catalog
If you need to clear your sandbox catalog of items, run:
```
npm run clear
```

This will clear your sandbox of any catalog items. Warning, this is irreversable and permanent!

## sample-seed-data.json

The sample data for the sandbox items are stored in here. Each JSON item is compromised of several parts:

#### Title
Each item has its own item title, which is how the script indexes the image and the data.
```
  "#Bacon Cheeseburger": {
```

#### Image
There is also an image part of the JSON item. This item has a url, image id, and caption. 
```
    "image": {
      "url": "./bin/script/img/bacon-burger@2x.png",
      "id": "#Bacon Cheeseburger",
      "caption": "A picture of a bacon cheeseburger."
    }
```

#### Data
The last part of the item is the data portion. This data includes pricing, variations, description, and more. We need this information in order to create a catalog item. Please see [documentation for CatalogObject](https://developer.squareup.com/reference/square/objects/CatalogObject) for more information.
```
"data": {
    "type": "ITEM",
    "id": "#Bacon Cheeseburger",
    "present_at_all_locations": true,
    "item_data": {
    "name": "Bacon Cheeseburger",
    "description": "One mouth-watering, flame-grilled, quarter-pound angus beef burger with your choice of cheese on a sesame seed bun. Comes with a side of fries.",
    "variations": [
        {
        "type": "ITEM_VARIATION",
        "id": "#Regular Bacon Cheeseburger",
        "is_deleted": false,
        "present_at_all_locations": true,
        "item_variation_data": {
            "item_id": "#Bacon Cheeseburger",
            "name": "Regular",
            "pricing_type": "FIXED_PRICING",
            "price_money": {
            "amount": 995,
            "currency": "USD"
            }
        }
        }
    ]
}
```

## img

Images are stored in the `./img/` folder in the script folder. You may edit and add your own images if you wish.
