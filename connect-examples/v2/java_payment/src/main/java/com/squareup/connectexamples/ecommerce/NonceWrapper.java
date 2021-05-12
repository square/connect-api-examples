package com.squareup.connectexamples.ecommerce;

/**
 * NonceForm is a model object representing the payment form submission.
 */
public class NonceWrapper {
    private String nonce;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
