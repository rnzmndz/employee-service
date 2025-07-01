package com.renzomendoza.employee_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee_profile")
@Entity
public class EmployeeProfile {

    @Id
    // Set new ID using the service
    private UUID id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String jobTitle;
    private String imageUrl;

    private LocalDate hiredDate;
    private LocalDate birthDate;

    @Embedded
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    @Embedded
    private EmergencyContact emergencyContact;
}
