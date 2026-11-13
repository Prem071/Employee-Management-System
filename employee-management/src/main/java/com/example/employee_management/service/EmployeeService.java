package com.example.employee_management.service;

import com.example.employee_management.exception.DuplicateEmailException;
import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<Employee> findAll(String department, Pageable pageable) {
        if (department != null && !department.isBlank()) {
            return repository.findByDepartmentIgnoreCase(department, pageable);
        }
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    public Employee create(Employee employee) {
        if (repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmailException(employee.getEmail());
        }
        employee.setId(null);
        return repository.save(employee);
    }

    public Employee update(Long id, Employee updated) {
        Employee existing = findById(id);

        if (repository.existsByEmailAndIdNot(updated.getEmail(), id)) {
            throw new DuplicateEmailException(updated.getEmail());
        }

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        existing.setJobTitle(updated.getJobTitle());
        existing.setSalary(updated.getSalary());
        existing.setHireDate(updated.getHireDate());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
