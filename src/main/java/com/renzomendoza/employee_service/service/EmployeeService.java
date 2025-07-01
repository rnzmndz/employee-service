package com.renzomendoza.employee_service.service;

import com.renzomendoza.employee_service.dto.AddressDto;
import com.renzomendoza.employee_service.dto.ContactInformationDto;
import com.renzomendoza.employee_service.dto.EmergencyContactDto;
import com.renzomendoza.employee_service.dto.employee.EmployeeList;
import com.renzomendoza.employee_service.dto.employee.EmployeeRequest;
import com.renzomendoza.employee_service.exception.EmployeeNotFoundException;
import com.renzomendoza.employee_service.mapper.EmployeeMapper;
import com.renzomendoza.employee_service.model.EmployeeProfile;
import com.renzomendoza.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeProfile createEmployee(EmployeeRequest employeeRequest) {
        EmployeeProfile employee = employeeMapper.employeeRequestToEmployee(employeeRequest);
        employee.setId(UUID.randomUUID());
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeProfile getEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @Transactional(readOnly = true)
    public Page<EmployeeList> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::employeeToEmployeeList);
    }

    public EmployeeProfile updateEmployee(UUID employeeId, EmployeeRequest employeeRequest) {
        EmployeeProfile existingEmployee = getEmployeeById(employeeId);

        // Update basic employee info
        existingEmployee.setFirstName(employeeRequest.getFirstName());
        existingEmployee.setMiddleName(employeeRequest.getMiddleName());
        existingEmployee.setLastName(employeeRequest.getLastName());
        existingEmployee.setJobTitle(employeeRequest.getJobTitle());
        existingEmployee.setImageUrl(employeeRequest.getImageUrl());
        existingEmployee.setHiredDate(employeeRequest.getHiredDate());
        existingEmployee.setBirthDate(employeeRequest.getBirthDate());

        // Update nested objects
        updateEmployeeAddress(employeeId, employeeRequest.getAddressDto());
        updateEmployeeContactInformation(employeeId, employeeRequest.getContactInformationDto());
        updateEmployeeEmergencyContact(employeeId, employeeRequest.getEmergencyContactDto());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(UUID employeeId) {
        EmployeeProfile employee = getEmployeeById(employeeId);
        employeeRepository.delete(employee);
    }

    public AddressDto updateEmployeeAddress(UUID employeeId, AddressDto addressDto) {
        EmployeeProfile employee = getEmployeeById(employeeId);
        employee.setAddress(employeeMapper.addressDtoToAddress(addressDto));
        EmployeeProfile updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.addressToAddressDto(updatedEmployee.getAddress());
    }

    public ContactInformationDto updateEmployeeContactInformation(UUID employeeId, ContactInformationDto contactInformationDto) {
        EmployeeProfile employee = getEmployeeById(employeeId);
        employee.setContactInformation(employeeMapper.contactInformationDtoToContactInformation(contactInformationDto));
        EmployeeProfile updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.contactInformationToContactInformationDto(updatedEmployee.getContactInformation());
    }

    public EmergencyContactDto updateEmployeeEmergencyContact(UUID employeeId, EmergencyContactDto emergencyContactDto) {
        EmployeeProfile employee = getEmployeeById(employeeId);
        employee.setEmergencyContact(employeeMapper.emergencyContactDtoToEmergencyContact(emergencyContactDto));
        EmployeeProfile updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.emergencyContactToEmergencyContactDto(updatedEmployee.getEmergencyContact());
    }

    @Transactional(readOnly = true)
    public Page<EmployeeList> getAllEmployeesSorted(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::employeeToEmployeeList);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeList> getEmployeesByJobTitle(String jobTitle, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByJobTitle(jobTitle, pageable)
                .map(employeeMapper::employeeToEmployeeList);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeList> searchEmployeesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByFirstNameContainingOrLastNameContaining(name, name, pageable)
                .map(employeeMapper::employeeToEmployeeList);
    }
}