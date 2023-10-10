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

package de.gematik.dsr.fd.application;

import de.gematik.dsr.fd.domain.erezept.v1.ERezept;
import de.gematik.dsr.fd.infrastructure.InMemoryERezeptRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ERezeptService {

  @Inject InMemoryERezeptRepository eRezeptRepository;

  public Collection<ERezept> getAllV1() {
    return eRezeptRepository.findAllV1();
  }

  public ERezept getERezeptV1ById(final UUID id) {
    final ERezept eRezept = eRezeptRepository.findV1ById(id);
    if (Objects.isNull(eRezept)) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    } else {
      return eRezept;
    }
  }

  public de.gematik.dsr.fd.domain.erezept.v2.ERezept getERezeptV2ById(final UUID id) {
    final de.gematik.dsr.fd.domain.erezept.v2.ERezept eRezept = eRezeptRepository.findV2ById(id);
    if (Objects.isNull(eRezept)) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    } else {
      return eRezept;
    }
  }

  public Collection<de.gematik.dsr.fd.domain.erezept.v2.ERezept> getAllV2() {
    return eRezeptRepository.findAllV2();
  }
}
