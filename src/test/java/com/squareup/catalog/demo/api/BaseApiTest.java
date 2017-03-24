package com.squareup.catalog.demo.api;

import com.squareup.catalog.demo.Logger;
import com.squareup.resources.Error;
import com.squareup.resources.ErrorResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.squareup.catalog.demo.util.GsonProvider.gson;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BaseApiTest {

  @Mock Logger logger;
  private BaseApi baseApi;

  @Before public void setup() {
    initMocks(this);
    baseApi = new BaseApi("baseUrl", "accessToken", logger);
  }

  @Test public void logHttpError_logsServerErrors() throws IOException {
    ErrorResponse errorResponse = new ErrorResponse.Builder() //
        .errors(asList( //
            new Error.Builder() //
                .code(Error.Code.INVALID_TIME) //
                .detail("Error detail 1") //
                .build(), //
            new Error.Builder() //
                .code(Error.Code.UNEXPECTED_VALUE) //
                .detail("Error detail 2") //
                .build())) //
        .build();
    String errorResponseJson = gson().toJson(errorResponse);

    HttpURLConnection connection = mock(HttpURLConnection.class);
    when(connection.getResponseCode()).thenReturn(404);
    when(connection.getResponseMessage()).thenReturn("Not Found");
    when(connection.getErrorStream()).thenReturn( //
        new ByteArrayInputStream(errorResponseJson.getBytes(UTF_8)));

    baseApi.logHttpError(connection);
    verify(logger, never()).info(anyString());
    verify(logger, times(2)).error(anyString());
  }

  @Test public void logHttpError_noErrorStream() throws IOException {
    HttpURLConnection connection = mock(HttpURLConnection.class);
    when(connection.getResponseCode()).thenReturn(404);
    when(connection.getResponseMessage()).thenReturn("Not Found");
    when(connection.getErrorStream()).thenReturn(null);

    baseApi.logHttpError(connection);
    verify(logger, never()).info(anyString());
    verify(logger).error("404 (Not Found)");
  }

  @Test public void logHttpError_nonJsonResponse() throws IOException {
    HttpURLConnection connection = mock(HttpURLConnection.class);
    when(connection.getResponseCode()).thenReturn(401);
    when(connection.getResponseMessage()).thenReturn("Unauthorized");
    when(connection.getErrorStream()).thenReturn( //
        new ByteArrayInputStream("who are you?".getBytes(UTF_8)));

    baseApi.logHttpError(connection);
    verify(logger, never()).info(anyString());
    verify(logger).error("[401 Unauthorized] who are you?");
  }
}
