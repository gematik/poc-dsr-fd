/*
 * Copyright 2023 gematik GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.dsr.fd.web.v1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@QuarkusTest
@TestHTTPEndpoint(NotfalldatenResource.class)
class NotfalldatenResourceTest {

  @ParameterizedTest
  @ValueSource(strings = {"X123456", "X234567", "X345678"})
  @DisplayName("GET notfalldaten - happy path")
  void test_GET_notfalldaten_happyPath(final String patientIdentifier) {
    given()
        .when()
        .queryParam("patientIdentifier", patientIdentifier)
        .get()
        .then()
        .log()
        .ifValidationFails()
        .statusCode(200)
        .body("patientIdentifier", is(patientIdentifier));
  }

  @Test
  @DisplayName("GET notfalldaten - 404 Not Found")
  void test_GET_notfalldaten_not_found() {
    given()
        .when()
        .queryParam("patientIdentifier", "unknown")
        .get()
        .then()
        .log()
        .ifValidationFails()
        .statusCode(404);
  }

  @Test
  @DisplayName("GET notfalldaten - missing query param patientIdentifier")
  void test_GET_notfalldaten_missing_patientIdentifier() {
    given()
        .when()
        .get()
        .then()
        .log()
        .ifValidationFails()
        .statusCode(400)
        .body("errorCode", is("CONSTRAINT_VIOLATION"))
        .body("description", notNullValue())
        .body("traceId", notNullValue());
  }
}
