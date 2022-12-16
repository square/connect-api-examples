package com.squareup.connectexamples.ecommerce;

/**
 * TokenWrapper is a model object representing the token received from the front end.
 */
public class TokenWrapper {

    private String token;
    private String idempotencyKey;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdempotencyKey() {
      return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
      this.idempotencyKey = idempotencyKey;
    }
}
