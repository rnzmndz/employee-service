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
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Employee mappings
    EmployeeProfile employeeRequestToEmployee(EmployeeRequest employeeRequest);
    EmployeeRequest employeeToEmployeeRequest(EmployeeProfile employee);
    EmployeeList employeeToEmployeeList(EmployeeProfile employee);

    // Address mappings
    Address addressDtoToAddress(AddressDto addressDto);
    AddressDto addressToAddressDto(Address address);

    // ContactInformation mappings
    ContactInformation contactInformationDtoToContactInformation(ContactInformationDto contactInformationDto);
    ContactInformationDto contactInformationToContactInformationDto(ContactInformation contactInformation);

    // EmergencyContact mappings
    EmergencyContact emergencyContactDtoToEmergencyContact(EmergencyContactDto emergencyContactDto);
    EmergencyContactDto emergencyContactToEmergencyContactDto(EmergencyContact emergencyContact);
}
