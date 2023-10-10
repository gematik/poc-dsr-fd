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

package de.gematik.dsr.fd.domain.notfalldaten;

import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import java.util.List;

public record Patient(
    String name,
    LocalDate dateOfBirth,
    BloodGroup bloodGroup,
    List<String> allergies,
    List<Medication> medications,
    List<String> medicalDiagnoses,
    List<String> pastMedicalHistory,
    List<EmergencyContact> emergencyContacts,
    InsuranceInformation insuranceInformation,
    boolean organDonation) {

  public enum BloodGroup {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String value;

    BloodGroup(final String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }
  }
}
