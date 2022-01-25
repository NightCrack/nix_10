package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.EmployeeDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.persistence.entity.Employee;
import ua.com.alevel.type.DepartmentType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDAOImpl extends BaseDaoImpl implements EmployeeDAO {

    private final static String CREATE_EMPLOYEE_QUERY = "insert into employees values(default, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_EMPLOYEE_QUERY = "update employees set updated = ?, age = ?, first_name = ?, last_name = ?, department_id = ? where id = ";
    private final static String DELETE_EMPLOYEE_QUERY = "delete from employees where id = ";
    private final static String DELETE_EMPLOYEE_BY_DEPARTMENT_QUERY = "delete from employees where department_id = ";
    private final static String EMPLOYEE_EXIST_BY_ID_QUERY = "select count(*) from employees where id = ";
    private final static String FIND_EMPLOYEE_JOIN_DEPARTMENT_BY_ID_QUERY = "select * from employees as e join departments as d on d.id = e.department_id where e.id = ";
    private final static String FIND_ALL_EMPLOYEES_QUERY = "select * from employees as e join departments as d on e.department_id = d.id";
    private final static String FIND_ALL_EMPLOYEES_BY_DEPARTMENT_QUERY = "select * from employees as e join departments as d on e.department_id = d.id where e.department_id = ";
    private final JpaConfig jpaConfig;
    public EmployeeDAOImpl(JpaConfig jpaConfig) {
        super(jpaConfig);
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(CustomResultSet<Employee> employee) {
//        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_EMPLOYEE_QUERY)) {
//            preparedStatement.setTimestamp(1, new Timestamp(employee.getCreated().getTime()));
//            preparedStatement.setTimestamp(2, new Timestamp(employee.getUpdated().getTime()));
//            preparedStatement.setString(3, employee.getAge().toString());
//            preparedStatement.setString(4, employee.getFirstName());
//            preparedStatement.setString(5, employee.getLastName());
//            preparedStatement.setLong(6, employee.getDepartment().getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
    }

    @Override
    public void update(Employee employee) {
//        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_EMPLOYEE_QUERY + employee.getId())) {
//            preparedStatement.setTimestamp(1, new Timestamp(employee.getUpdated().getTime()));
//            preparedStatement.setString(2, employee.getAge().toString());
//            preparedStatement.setString(3, employee.getFirstName());
//            preparedStatement.setString(4, employee.getLastName());
//            preparedStatement.setLong(5, employee.getDepartment().getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
    }

    @Override
    public void delete(Long id) {
//        jpaConfig.deleteByCriteria(DELETE_EMPLOYEE_QUERY, id);
    }

    @Override
    public boolean existsById(Long id) {
//        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EMPLOYEE_EXIST_BY_ID_QUERY + id)) {
//            if (resultSet.next()) {
//                return resultSet.getInt("count(*)") != 0;
//            }
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return false;
    }

    @Override
    public Employee findById(Long id) {
//        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_EMPLOYEE_JOIN_DEPARTMENT_BY_ID_QUERY + id)) {
//            if (resultSet.next()) {
//                return convertResultSetToEmployees(resultSet);
//            }
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return null;
    }

    @Override
    public DataTableResponse<Employee> findAll(DataTableRequest request) {
        List<Employee> employees = new ArrayList<>();
        try(ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_EMPLOYEES_QUERY)) {
            while (resultSet.next()) {
                employees.add(convertResultSetToEmployees(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Employee> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(employees);
        return dataTableResponse;
    }

    @Override
    public int count() {
        return count("employees");
    }

    @Override
    public void deleteAllByForeignId(Long id) {
//        jpaConfig.deleteByCriteria(DELETE_EMPLOYEE_BY_DEPARTMENT_QUERY, id);
    }

    @Override
    public List<Employee> findAllByForeignId(Long id) {
        return null;
//        return findAllByCriteria(FIND_ALL_EMPLOYEES_BY_DEPARTMENT_QUERY, id);
    }

    private List<Employee> findAllByCriteria(String criteria, Long... id) {
//        Long criteriaId = null;
//        if (id.length != 0) {
//            criteriaId = id[0];
//        }
//        ResultSet resultSet = jpaConfig.findAllByCriteria(criteria, criteriaId);
//        List<Employee> employees = new ArrayList<>();
//        try {
//            while (resultSet.next()) {
//                employees.add(convertResultSetToEmployees(resultSet));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return employees;
        return null;
    }

    private Employee convertResultSetToEmployees(ResultSet resultSet) throws SQLException {
        Long employeeId = resultSet.getLong("e.id");
        Instant employeeCreated = resultSet.getTimestamp("e.created").toInstant();
        Instant employeeUpdated = resultSet.getTimestamp("e.updated").toInstant();
        int age = resultSet.getInt("age");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Long departmentId = resultSet.getLong("d.id");
        Instant departmentCreated = resultSet.getTimestamp("d.created").toInstant();
        Instant departmentUpdated = resultSet.getTimestamp("d.updated").toInstant();
        String type = resultSet.getString("department_type");
        String name = resultSet.getString("name");
        Department department = new Department();
        department.setId(departmentId);
        department.setCreated(departmentCreated);
        department.setUpdated(departmentUpdated);
        department.setDepartmentType(DepartmentType.valueOf(type));
        department.setName(name);
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setCreated(employeeCreated);
        employee.setUpdated(employeeUpdated);
        employee.setAge(age);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(department);
        return employee;
    }
}
