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
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@QuarkusTest
@TestHTTPEndpoint(ERezeptResource.class)
class ERezeptResourceTest {

  @Test
  @DisplayName("GET all eRezept V1")
  void test_GET_all() {
    given().when().get().then().log().ifValidationFails().statusCode(200).body("size()", is(2));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {"6c371ed4-f83a-47b4-8249-446852a5b382", "17e5970d-6e64-465f-be0c-ed0f5d7624a0"})
  @DisplayName("GET eRezept V1 by id - happy path")
  void test_GET_eRezept_happyPath(final String id) {
    given()
        .when()
        .pathParam("id", id)
        .get("{id}")
        .then()
        .log()
        .ifValidationFails()
        .statusCode(200)
        .body("id", is(id));
  }

  @ParameterizedTest
  @ValueSource(strings = {"a61eb300-df3a-4c87-9de5-efdadc1b0259", "unknown"})
  @DisplayName("GET eRezept V1 by id- 404 Not Found")
  void test_GET_eRezept_not_found(final String id) {
    given().when().pathParam("id", id).get("{id}").then().log().ifValidationFails().statusCode(404);
  }
}
