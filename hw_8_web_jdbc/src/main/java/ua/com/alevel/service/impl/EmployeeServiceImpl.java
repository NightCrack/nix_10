package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Department;
import ua.com.alevel.entity.Employee;
import ua.com.alevel.repository.DepartmentRepository;
import ua.com.alevel.repository.EmployeeRepository;
import ua.com.alevel.service.EmployeeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void create(Employee entity) {
        employeeRepository.save(entity);
    }

    @Override
    public void update(Employee entity) {
        if (employeeRepository.existsById(entity.getId()))
            employeeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        }

    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findAllByDepartment(Long departmentId) {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            return employeeRepository.findAllByDepartment(optionalDepartment.get());
        }
        return Collections.emptyList();
    }
}
