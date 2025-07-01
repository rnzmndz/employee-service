package com.renzomendoza.employee_service.mapper;

import com.renzomendoza.employee_service.dto.AddressDto;
import com.renzomendoza.employee_service.dto.ContactInformationDto;
import com.renzomendoza.employee_service.dto.EmergencyContactDto;
import com.renzomendoza.employee_service.dto.employee.EmployeeList;
import com.renzomendoza.employee_service.dto.employee.EmployeeRequest;
import com.renzomendoza.employee_service.model.Address;
import com.renzomendoza.employee_service.model.ContactInformation;
import com.renzomendoza.employee_service.model.EmergencyContact;
import com.renzomendoza.employee_service.model.EmployeeProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    }

    @Test
    void testEmployeeRequestToEmployeeProfile() {
        // Given
        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("John")
                .middleName("Doe")
                .lastName("Smith")
                .jobTitle("Developer")
                .imageUrl("http://example.com/image.jpg")
                .hiredDate(LocalDate.of(2020, 1, 1))
                .birthDate(LocalDate.of(1990, 5, 15))
                .addressDto(AddressDto.builder()
                        .street("123 Main St")
                        .city("New York")
                        .state("NY")
                        .zipCode("10001")
                        .build())
                .contactInformationDto(ContactInformationDto.builder()
                        .phoneNumber("123-456-7890")
                        .email("john.smith@example.com")
                        .build())
                .emergencyContactDto(EmergencyContactDto.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .phoneNumber("987-654-3210")
                        .build())
                .build();

        // When
        EmployeeProfile employee = employeeMapper.employeeRequestToEmployee(request);

        // Then
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getMiddleName());
        assertEquals("Smith", employee.getLastName());
        assertEquals("Developer", employee.getJobTitle());
        assertEquals("http://example.com/image.jpg", employee.getImageUrl());
        assertEquals(LocalDate.of(2020, 1, 1), employee.getHiredDate());
        assertEquals(LocalDate.of(1990, 5, 15), employee.getBirthDate());

        // Verify address mapping
        assertNotNull(employee.getAddress());
        assertEquals("123 Main St", employee.getAddress().getStreet());
        assertEquals("New York", employee.getAddress().getCity());
        assertEquals("NY", employee.getAddress().getState());
        assertEquals("10001", employee.getAddress().getZipCode());

        // Verify contact information mapping
        assertNotNull(employee.getContactInformation());
        assertEquals("123-456-7890", employee.getContactInformation().getPhoneNumber());
        assertEquals("john.smith@example.com", employee.getContactInformation().getEmail());

        // Verify emergency contact mapping
        assertNotNull(employee.getEmergencyContact());
        assertEquals("Jane", employee.getEmergencyContact().getFirstName());
        assertEquals("Doe", employee.getEmergencyContact().getLastName());
        assertEquals("987-654-3210", employee.getEmergencyContact().getPhoneNumber());
    }

    @Test
    void testEmployeeToEmployeeRequest() {
        // Given
        EmployeeProfile employee = EmployeeProfile.builder()
                .firstName("Alice")
                .middleName("Marie")
                .lastName("Johnson")
                .jobTitle("Manager")
                .imageUrl("http://example.com/alice.jpg")
                .hiredDate(LocalDate.of(2019, 6, 10))
                .birthDate(LocalDate.of(1985, 8, 20))
                .address(Address.builder()
                        .street("456 Oak Ave")
                        .city("Los Angeles")
                        .state("CA")
                        .zipCode("90001")
                        .build())
                .contactInformation(ContactInformation.builder()
                        .phoneNumber("555-123-4567")
                        .email("alice.johnson@example.com")
                        .build())
                .emergencyContact(EmergencyContact.builder()
                        .firstName("Bob")
                        .lastName("Lee")
                        .phoneNumber("555-987-6543")
                        .build())
                .build();

        // When
        EmployeeRequest request = employeeMapper.employeeToEmployeeRequest(employee);

        // Then
        assertNotNull(request);
        assertEquals("Alice", request.getFirstName());
        assertEquals("Marie", request.getMiddleName());
        assertEquals("Johnson", request.getLastName());
        assertEquals("Manager", request.getJobTitle());
        assertEquals("http://example.com/alice.jpg", request.getImageUrl());
        assertEquals(LocalDate.of(2019, 6, 10), request.getHiredDate());
        assertEquals(LocalDate.of(1985, 8, 20), request.getBirthDate());

        // Verify address mapping
        assertNotNull(request.getAddressDto());
        assertEquals("456 Oak Ave", request.getAddressDto().getStreet());
        assertEquals("Los Angeles", request.getAddressDto().getCity());
        assertEquals("CA", request.getAddressDto().getState());
        assertEquals("90001", request.getAddressDto().getZipCode());

        // Verify contact information mapping
        assertNotNull(request.getContactInformationDto());
        assertEquals("555-123-4567", request.getContactInformationDto().getPhoneNumber());
        assertEquals("alice.johnson@example.com", request.getContactInformationDto().getEmail());

        // Verify emergency contact mapping
        assertNotNull(request.getEmergencyContactDto());
        assertEquals("Bob", request.getEmergencyContactDto().getFirstName());
        assertEquals("Lee", request.getEmergencyContactDto().getLastName());
        assertEquals("555-987-6543", request.getEmergencyContactDto().getPhoneNumber());
    }

    @Test
    void testEmployeeToEmployeeList() {
        // Given
        EmployeeProfile employee = EmployeeProfile.builder()
                .firstName("Michael")
                .middleName("James")
                .lastName("Brown")
                .jobTitle("Designer")
                .imageUrl("http://example.com/michael.jpg")
                .build();

        // When
        EmployeeList employeeList = employeeMapper.employeeToEmployeeList(employee);

        // Then
        assertNotNull(employeeList);
        assertEquals("Michael", employeeList.getFirstName());
        assertEquals("James", employeeList.getMiddleName());
        assertEquals("Brown", employeeList.getLastName());
        assertEquals("Designer", employeeList.getJobTitle());
        assertEquals("http://example.com/michael.jpg", employeeList.getImageUrl());
    }

    @Test
    void testAddressDtoToAddress() {
        // Given
        AddressDto addressDto = AddressDto.builder()
                .street("789 Pine Rd")
                .city("Chicago")
                .state("IL")
                .zipCode("60601")
                .build();

        // When
        Address address = employeeMapper.addressDtoToAddress(addressDto);

        // Then
        assertNotNull(address);
        assertEquals("789 Pine Rd", address.getStreet());
        assertEquals("Chicago", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("60601", address.getZipCode());
    }

    @Test
    void testContactInformationDtoToContactInformation() {
        // Given
        ContactInformationDto contactDto = ContactInformationDto.builder()
                .phoneNumber("111-222-3333")
                .email("test@example.com")
                .build();

        // When
        ContactInformation contactInfo = employeeMapper.contactInformationDtoToContactInformation(contactDto);

        // Then
        assertNotNull(contactInfo);
        assertEquals("111-222-3333", contactInfo.getPhoneNumber());
        assertEquals("test@example.com", contactInfo.getEmail());
    }

    @Test
    void testEmergencyContactDtoToEmergencyContact() {
        // Given
        EmergencyContactDto emergencyContactDto = EmergencyContactDto.builder()
                .firstName("Sarah")
                .lastName("Ann")
                .phoneNumber("444-555-6666")
                .build();

        // When
        EmergencyContact emergencyContact = employeeMapper.emergencyContactDtoToEmergencyContact(emergencyContactDto);

        // Then
        assertNotNull(emergencyContact);
        assertEquals("Sarah", emergencyContact.getFirstName());
        assertEquals("Ann", emergencyContact.getLastName());
        assertEquals("444-555-6666", emergencyContact.getPhoneNumber());
    }
}