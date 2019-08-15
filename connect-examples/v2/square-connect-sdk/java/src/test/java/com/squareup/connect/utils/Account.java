package com.squareup.connect.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

// Represent the account info
public class Account {
    @JsonProperty("email")
    public String email;
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("location_id")
    public String locationId;
    @JsonProperty("application_id")
    public String applicationId;
}
