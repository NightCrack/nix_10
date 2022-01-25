package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.DepartmentsDAO;
import ua.com.alevel.persistence.dao.EmployeeDAO;
import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.service.DepartmentService;
import ua.com.alevel.util.WebResponseUtil;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentsDAO departmentsDAO;
    private final EmployeeDAO employeeDAO;

    public DepartmentServiceImpl(DepartmentsDAO departmentsDAO, EmployeeDAO employeeDAO) {
        this.departmentsDAO = departmentsDAO;
        this.employeeDAO = employeeDAO;
    }


    @Override
    public void create(CustomResultSet<Department> department) {
        departmentsDAO.create(department);
    }

    @Override
    public void update(Department department) {
//        if (departmentsDAO.existsById(department.getId())) {
//            departmentsDAO.update(department);
//        }
    }

    @Override
    public void delete(Long id) {
//        if (departmentsDAO.existsById(id)) {
//            employeeDAO.deleteAllByForeignId(id);
//            departmentsDAO.delete(id);
//        }
    }

    @Override
    public Department findById(Long id) {
        return departmentsDAO.findById(id);
    }

    @Override
    public DataTableResponse<Department> findAll(DataTableRequest request) {
        DataTableResponse<Department> dataTableResponse = departmentsDAO.findAll(request);
        int count = departmentsDAO.count();
        WebResponseUtil.initDataTableResponse(request,dataTableResponse,count);
        return dataTableResponse;
    }
}
