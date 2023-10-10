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

package de.gematik.dsr.fd.web.v2;

import de.gematik.dsr.fd.application.ERezeptService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@Path("/api/v2/erezept")
@Produces(MediaType.APPLICATION_JSON)
public class ERezeptResource {

  @Inject ERezeptService eRezeptService;

  @GET
  public Response getAll() {
    final var all = eRezeptService.getAllV2();
    return Response.ok(all).build();
  }

  @GET
  @Path("/{id}")
  public Response getERezept(@PathParam("id") final UUID id) {
    final var entity = eRezeptService.getERezeptV2ById(id);
    return Response.ok(entity).build();
  }
}
