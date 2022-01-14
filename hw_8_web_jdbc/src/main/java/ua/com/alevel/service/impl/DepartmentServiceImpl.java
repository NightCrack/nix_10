package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Department;
import ua.com.alevel.repository.DepartmentRepository;
import ua.com.alevel.repository.EmployeeRepository;
import ua.com.alevel.service.DepartmentService;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void create(Department entity) {
        departmentRepository.save(entity);
    }

    @Override
    public void update(Department entity) {
        if (departmentRepository.existsById(entity.getId())) {
            departmentRepository.save(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (departmentRepository.existsById(id)) {
            employeeRepository.deleteAllByDepartment(departmentRepository.findById(id).get());
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
