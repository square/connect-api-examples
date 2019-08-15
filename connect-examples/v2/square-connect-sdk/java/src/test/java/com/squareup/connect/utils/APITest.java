package com.squareup.connect.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.BeforeClass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APITest {

    protected static Map<String, Account> accounts;

    @BeforeClass
    public static void loadAccounts() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(Paths.get("./travis-ci/accounts.json"));
        accounts = mapper.readValue(jsonData, new TypeReference<Map<String, Account>>() { });
    }
}
