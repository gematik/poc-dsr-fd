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
import de.gematik.dsr.fd.domain.erezept.v1.ERezept;
import io.smallrye.jwt.util.ResourceUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class InMemoryERezeptRepository {

  private final ObjectMapper objectMapper;
  private final Map<UUID, ERezept> eRezeptMapV1;
  private final Map<UUID, de.gematik.dsr.fd.domain.erezept.v2.ERezept> eRezeptMapV2;

  @Inject
  public InMemoryERezeptRepository(
      final ObjectMapper objectMapper,
      @ConfigProperty(name = "erezept-v1-files") final String[] eRezeptV1JsonFiles,
      @ConfigProperty(name = "erezept-v2-files") final String[] eRezeptV2JsonFiles) {
    this.objectMapper = objectMapper;
    this.eRezeptMapV1 = convertV1(eRezeptV1JsonFiles);
    this.eRezeptMapV2 = convertV2(eRezeptV2JsonFiles);
  }

  private Map<UUID, ERezept> convertV1(final String[] eRezeptV1JsonFiles) {
    final Map<UUID, ERezept> dataMap = new HashMap<>();
    for (final String jsonFilePath : eRezeptV1JsonFiles) {
      try (final InputStream inputStream = ResourceUtils.getResourceStream(jsonFilePath)) {
        final ERezept eRezept = objectMapper.readValue(inputStream, ERezept.class);
        dataMap.put(eRezept.id(), eRezept);
      } catch (final Exception e) {
        throw new IllegalArgumentException(
            "Failed to convert eRezept v1 Json file into ERezept", e);
      }
    }
    return Collections.unmodifiableMap(dataMap);
  }

  private Map<UUID, de.gematik.dsr.fd.domain.erezept.v2.ERezept> convertV2(
      final String[] eRezeptV2JsonFiles) {
    final Map<UUID, de.gematik.dsr.fd.domain.erezept.v2.ERezept> dataMap = new HashMap<>();
    for (final String jsonFilePath : eRezeptV2JsonFiles) {
      try (final InputStream inputStream = ResourceUtils.getResourceStream(jsonFilePath)) {
        final de.gematik.dsr.fd.domain.erezept.v2.ERezept eRezept =
            objectMapper.readValue(inputStream, de.gematik.dsr.fd.domain.erezept.v2.ERezept.class);
        dataMap.put(eRezept.id(), eRezept);
      } catch (final Exception e) {
        throw new IllegalArgumentException(
            "Failed to convert eRezept v2 Json file into ERezept", e);
      }
    }
    return Collections.unmodifiableMap(dataMap);
  }

  public ERezept findV1ById(final UUID id) {
    return eRezeptMapV1.get(id);
  }

  public de.gematik.dsr.fd.domain.erezept.v2.ERezept findV2ById(final UUID id) {
    return eRezeptMapV2.get(id);
  }

  public Collection<ERezept> findAllV1() {
    return eRezeptMapV1.values();
  }

  public Collection<de.gematik.dsr.fd.domain.erezept.v2.ERezept> findAllV2() {
    return eRezeptMapV2.values();
  }
}
