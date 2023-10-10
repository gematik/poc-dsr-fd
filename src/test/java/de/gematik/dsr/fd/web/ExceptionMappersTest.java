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

package de.gematik.dsr.fd.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import org.junit.jupiter.api.Test;

class ExceptionMappersTest {

  @Test
  void test_handling_unhandled_exception() throws Exception {

    // Given
    final var uriInfoMock = mock(UriInfo.class);
    when(uriInfoMock.getAbsolutePath()).thenReturn(new URI("http://test"));

    // target under test
    final ExceptionMappers exceptionMappers = new ExceptionMappers(uriInfoMock);

    // When
    final Response response =
        exceptionMappers.mapException(new IllegalArgumentException("just a TEST"));

    // Then
    assertNotNull(response);
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
  }
}
