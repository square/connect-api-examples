/*
  Demonstrates creating, updating, and deleting an item with the Square Connect API.
  Replace the value of the `_accessToken` variable below before running this sample.

  This sample requires the Unirest Java library. Download instructions are here:
  http://unirest.io/java.html

  This sample requires Java SE 6 or later.
 */

package com.squareup.connectexamples;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;


public class ItemManager {

  // Replace this value with your application's personal access token,
  // available from your application dashboard (https://connect.squareup.com/apps)
  private final String _accessToken = "REPLACE_ME";

  // The ID of the location you want to create an item for.
  // See PaymentsReporter.java for an example of getting a business's location IDs.
  private final String _locationId = "REPLACE_ME";

  // The base URL for every Connect API request
  private final String _connectHost = "https://connect.squareup.com";

  // Constructor that sets headers common to every Connect API request
  public ItemManager() {
    Unirest.setDefaultHeader("Authorization", "Bearer " + _accessToken);
    Unirest.setDefaultHeader("Content-Type", "application/json");
    Unirest.setDefaultHeader("Accept", "application/json");
  }

  // Creates a "Milkshake" item.
  public JSONObject createItem() throws Exception {

    // This code block demonstrates building a JSON request body with JSONObject and JSONArray
    // objects. You can instead provide request bodies as simple JSON strings if you prefer.
    //
    // Indentation is included to reflect the structure of the corresponding JSON.

    JSONObject requestBody = new JSONObject();                       // {
      requestBody.put("name", "Milkshake");                          //   "name": "Milkshake",
      JSONArray variations = new JSONArray();                        //
      requestBody.put("variations", variations);                     //   "variations": [
        JSONObject variationSmall = new JSONObject();                //
        variations.put(variationSmall);                              //     {
          variationSmall.put("name", "Small");                       //       "name": "Small",
          variationSmall.put("pricing_type", "FIXED_PRICING");       //       "pricing_type": "FIXED_PRICING",
          JSONObject variationSmallPrice = new JSONObject();         //
          variationSmall.put("price_money", variationSmallPrice);    //       "price_money": {
            variationSmallPrice.put("currency_code", "USD");         //         "currency_code": "USD",
            variationSmallPrice.put("amount", 400);                  //         "amount": 400
                                                                     //       }
                                                                     //     }
                                                                     //   ]
                                                                     // }

    JsonNode bodyNode = new JsonNode(requestBody.toString());
    HttpResponse<JsonNode> response = null;
    try {
      response = Unirest.post(_connectHost + "/v1/" + _locationId + "/items").body(bodyNode).asJson();
    } catch (UnirestException e) {
      return null;
    }

    if (response != null && response.getStatus() != 200) {
      throw new Exception("Error encountered while creating item: " + response.getBody());
    } else if (response != null && response.getStatus() == 200) {
      System.out.println("Successfully created item:");
      System.out.println(response.getBody().getObject().toString(2));
      return response.getBody().getObject();
    } else {
      return null;
    }
  }

  // Updates the Milkshake item to rename it to "Malted Milkshake"
  public JSONObject updateItem(String itemId) throws Exception{

    // This method uses a simple hardcoded string request body (as opposed to createItem above)
    String requestBody = "{\"name\": \"Malted Milkshake\"}";

    HttpResponse<JsonNode> response = null;
    try {
      response = Unirest.put(_connectHost + "/v1/" + _locationId + "/items/" + itemId).body(requestBody).asJson();
    } catch (UnirestException e) {
      return null;
    }

    if (response != null && response.getStatus() != 200) {
      throw new Exception("Error encountered while updating item: " + response.getBody());
    } else if (response != null && response.getStatus() == 200) {
      System.out.println("Successfully updated item:");
      System.out.println(response.getBody().getObject().toString(2));
      return response.getBody().getObject();
    } else {
      return null;
    }
  }

  // Deletes the Malted Milkshake item.
  public JSONObject deleteItem(String itemId) throws Exception {

    HttpResponse<JsonNode> response = null;
    try {
      response = Unirest.delete(_connectHost + "/v1/" + _locationId + "/items/" + itemId).asJson();
    } catch (UnirestException e) {
      return null;
    }

    if (response != null && response.getStatus() != 200) {
      throw new Exception("Error encountered while deleting item: " + response.getBody());
    } else if (response != null && response.getStatus() == 200) {
      System.out.println("Successfully deleted item");
      return response.getBody().getObject();
    } else {
      System.out.println("Item deletion failed");
      return null;
    }
  }

  // Call the methods defined above
  public static void main(String[] args) {
    ItemManager manager = new ItemManager();

    try {
      JSONObject item = manager.createItem();
      // Update and delete the item only if it was successfully created
      if (item != null) {
        manager.updateItem(item.get("id").toString());
        manager.deleteItem(item.get("id").toString());
      } else {
        System.out.println("Aborting");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
