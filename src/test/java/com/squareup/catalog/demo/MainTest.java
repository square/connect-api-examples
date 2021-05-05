/*
 * Copyright 2017 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.catalog.demo;

import com.squareup.catalog.demo.example.Example;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MainTest {

  @Mock Logger logger;
  @Mock Example exampleFoo;
  @Mock Example exampleBar;
  private Main main;

  @Before public void setup() {
    initMocks(this);
    when(exampleFoo.getName()).thenReturn("foo");
    when(exampleBar.getName()).thenReturn("bar");

    main = new Main(logger, exampleFoo, exampleBar);
  }

  @Test public void processArgs_showUsage() {
    main.processArgs(null);
    verify(logger).info(startsWith("USAGE"));
    verify(logger, never()).error(anyString());
    reset(logger);

    main.processArgs(new String[0]);
    verify(logger).info(startsWith("USAGE"));
    verify(logger, never()).error(anyString());
    reset(logger);

    main.processArgs(new String[] {"-usage"});
    verify(logger).info(startsWith("USAGE"));
    verify(logger, never()).error(anyString());
    reset(logger);

    main.processArgs(new String[] {"-USAGE"});
    verify(logger).info(startsWith("USAGE"));
    verify(logger, never()).error(anyString());
    reset(logger);
  }

  @Test public void processArgs_listExamples() {
    main.processArgs(new String[] {"-list-examples"});
    verify(logger).info(startsWith("Examples"));
    verify(logger, never()).error(anyString());
  }

  @Test public void processArgs_executeExample() throws ApiException {
    main.processArgs(new String[] {"foo", "-token", "abcdef"});
    verify(exampleBar, never()).execute(any(CatalogApi.class), any(LocationsApi.class));
    verify(exampleFoo).execute(any(CatalogApi.class), any(LocationsApi.class));
  }

  @Test public void processArgs_executeExampleWithBaseUrl() throws ApiException {
    main.processArgs(new String[] {"foo", "-token", "abcdef", "-base-url", "http://squareup.com/baseurl"});
    verify(exampleFoo).execute(any(CatalogApi.class), any(LocationsApi.class));
    verify(exampleBar, never()).execute(any(CatalogApi.class), any(LocationsApi.class));
  }

  @Test public void processArgs_exampleNotFound() throws ApiException {
    try {
      main.processArgs(new String[] {"bad_name", "-token", "abcdef"});
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected.
    }
    verifyExamplesDidNotExecute();
  }

  @Test public void processArgs_exampleDanglingToken() throws ApiException {
    main.processArgs(new String[] {"bad_name", "-token"});
    verify(logger).error(anyString());
    verifyUsageLogged();
  }

  @Test public void processArgs_cleanupExample() throws ApiException {
    main.processArgs(new String[] {"foo", "-token", "abcdef", "-cleanup"});
    verify(exampleFoo).cleanup(any(CatalogApi.class), any(LocationsApi.class));
    verify(exampleFoo, never()).execute(any(CatalogApi.class), any(LocationsApi.class));
    verify(exampleBar, never()).execute(any(CatalogApi.class), any(LocationsApi.class));
  }

  @Test public void processArgs_unrecognizedArgument() throws ApiException {
    main.processArgs(new String[] {"bad_name", "-token", "abcdef", "-unrecognized"});
    verify(logger).error(startsWith("Unrecognized"));
    verifyUsageLogged();
  }

  private void verifyUsageLogged() throws ApiException {
    verify(logger).info(startsWith("USAGE"));
    verifyExamplesDidNotExecute();
  }

  private void verifyExamplesDidNotExecute() throws ApiException {
    verify(exampleFoo, never()).execute(any(CatalogApi.class), any(LocationsApi.class));
    verify(exampleBar, never()).execute(any(CatalogApi.class), any(LocationsApi.class));
  }
}
