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

import de.gematik.dsr.fd.domain.notfalldaten.Notfalldaten;
import de.gematik.dsr.fd.infrastructure.InMemoryNotfalldatenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.Objects;

@ApplicationScoped
public class NotfalldatenService {

  @Inject InMemoryNotfalldatenRepository notfalldatenRepository;

  public Notfalldaten getNotfalldatenByPatientIdentifier(final String patientIdentifier) {
    final Notfalldaten notfalldaten =
        notfalldatenRepository.findByPatientIdentifier(patientIdentifier);
    if (Objects.isNull(notfalldaten)) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    } else {
      return notfalldaten;
    }
  }
}
