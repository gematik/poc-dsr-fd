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

import de.gematik.dsr.fd.application.NotfalldatenService;
import de.gematik.dsr.fd.domain.notfalldaten.Notfalldaten;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/notfalldaten")
@Produces(MediaType.APPLICATION_JSON)
public class NotfalldatenResource {

  @Inject NotfalldatenService notfalldatenService;

  @GET
  public Response getNotfalldaten(
      @QueryParam("patientIdentifier")
          @NotBlank(message = "Required query parameter 'patientIdentifier' missing")
          final String patientIdentifier) {
    final Notfalldaten notfalldaten =
        notfalldatenService.getNotfalldatenByPatientIdentifier(patientIdentifier.trim());
    return Response.ok(notfalldaten).build();
  }
}
