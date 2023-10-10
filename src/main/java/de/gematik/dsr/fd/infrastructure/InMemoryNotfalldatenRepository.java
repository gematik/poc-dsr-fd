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

package de.gematik.dsr.fd.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.dsr.fd.domain.notfalldaten.Notfalldaten;
import io.smallrye.jwt.util.ResourceUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class InMemoryNotfalldatenRepository {

  private final ObjectMapper objectMapper;
  private final Map<String, Notfalldaten> notfalldatenMap;

  @Inject
  public InMemoryNotfalldatenRepository(
      final ObjectMapper objectMapper,
      @ConfigProperty(name = "notfalldaten-files") final String[] notfalldatenFiles) {
    this.objectMapper = objectMapper;
    this.notfalldatenMap = convert(notfalldatenFiles);
  }

  private Map<String, Notfalldaten> convert(final String[] notfalldatenFiles) {
    final Map<String, Notfalldaten> dataMap = new HashMap<>();
    for (final String jsonFilePath : notfalldatenFiles) {
      try (final InputStream inputStream = ResourceUtils.getResourceStream(jsonFilePath)) {
        final Notfalldaten notfalldaten = objectMapper.readValue(inputStream, Notfalldaten.class);
        dataMap.put(notfalldaten.patientIdentifier(), notfalldaten);
      } catch (final Exception e) {
        throw new IllegalArgumentException(
            "Failed to convert Notfalldaten Json file into Notfalldaten", e);
      }
    }
    return Collections.unmodifiableMap(dataMap);
  }

  public Notfalldaten findByPatientIdentifier(final String patientIdentifier) {
    return notfalldatenMap.get(patientIdentifier);
  }
}
