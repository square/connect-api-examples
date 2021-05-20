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

import com.squareup.catalog.demo.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility methods for prompting user for input.
 */
public class Prompts {

  /**
   * Display a message prompting the user to enter information.
   *
   * @param message the message displayed the user
   * @return the value entered by the user
   */
  public static String promptUserInput(String message) {
    System.out.print(message);
    System.out.flush();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      return br.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Display a message prompting the user to answer a yes or no question.
   *
   * @param message the message displayed the user
   * @param logger  the {@link Logger} used to log errors
   * @return true if yes, false if no
   */
  public static boolean promptUserInputYesNo(String message, Logger logger) {
    String response = promptUserInput(message).trim();
    if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
      return true;
    } else if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
      return false;
    }

    logger.error("Please enter Y or N");
    throw new IllegalArgumentException("Invalid text entered for yes/no question");
  }

  private Prompts() {
  }
}
