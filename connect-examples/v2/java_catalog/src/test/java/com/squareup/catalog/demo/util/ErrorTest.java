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
package com.squareup.catalog.demo.util;

import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import com.squareup.catalog.demo.Logger;
import com.squareup.square.models.Error;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ErrorTest {

  @Mock
  Logger logger;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void checkAndLogErrors_hasCodeAndCategory() {
    List<Error> errors =
        Collections.singletonList(new Error.Builder("CATEGORY", "code").detail("detail").build());

    Errors.checkAndLogErrors(errors, logger);
    verify(logger).error("[CATEGORY:code] detail");
  }

  @Test
  public void checkAndLogErrors_hasCodeOnly() {
    List<Error> errors =
        Collections.singletonList(new Error.Builder(null, "BAD REQUEST").detail("detail").build());
    Errors.checkAndLogErrors(errors, logger);
    verify(logger).error("[BAD REQUEST] detail");
  }

  @Test
  public void checkAndLogErrors_hasCategoryOnly() {
    List<Error> errors = Collections.singletonList(
        new Error.Builder("INVALID REQUEST", null).detail("detail").build());
    Errors.checkAndLogErrors(errors, logger);
    verify(logger).error("[INVALID REQUEST] detail");
  }

  @Test
  public void checkAndLogErrors_noCodeOrCategory() {
    List<Error> errors =
        Collections.singletonList(new Error.Builder(null, null).detail("detail").build());
    Errors.checkAndLogErrors(errors, logger);
    verify(logger).error("detail");
  }
}
