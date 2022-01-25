package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.DepartmentsDAO;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.type.DepartmentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

@Service
public class DepartmentsDAOImpl extends BaseDaoImpl implements DepartmentsDAO {

    private final static String CREATE_DEPARTMENT_QUERY = "insert into departments values(default, ?, ?, ?, ?)";
    private final static String UPDATE_DEPARTMENT_QUERY = "update departments set name = ?, department_type = ?, updated = ? where id = ";
    private final static String DELETE_DEPARTMENT_QUERY = "delete from departments where id = ";
    private final static String DEPARTMENT_EXIST_BY_ID_QUERY = "select count(*) from departments where id = ";
    private final static String FIND_DEPARTMENT_BY_ID_QUERY = "select * from departments where id = ";
    private final JpaConfig jpaConfig;
    public DepartmentsDAOImpl(JpaConfig jpaConfig) {
        super(jpaConfig);
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(CustomResultSet<Department> entity) {
//        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_DEPARTMENT_QUERY)) {
//            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
//            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
//            preparedStatement.setString(3, entity.getDepartmentType().name());
//            preparedStatement.setString(4, entity.getName());
//            preparedStatement.executeUpdate();
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
    }

    @Override
    public void update(Department entity) {
//        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_DEPARTMENT_QUERY + entity.getId())) {
//            preparedStatement.setString(1, entity.getName());
//            preparedStatement.setString(2, entity.getDepartmentType().name());
//            preparedStatement.setTimestamp(3, new Timestamp(entity.getUpdated().getTime()));
//            preparedStatement.executeUpdate();
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
    }

    @Override
    public void delete(Long id) {
//        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_DEPARTMENT_QUERY + id)) {
//            preparedStatement.executeUpdate();
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
    }

    @Override
    public boolean existsById(Long id) {
//        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(DEPARTMENT_EXIST_BY_ID_QUERY + id)) {
//            if (resultSet.next()) {
//                return resultSet.getInt("count(*)") != 0;
//            }
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return false;
    }

    @Override
    public Department findById(Long id) {
//        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_DEPARTMENT_BY_ID_QUERY + id)) {
//            if (resultSet.next()) {
//                return convertResultSetToDepartments(resultSet);
//            }
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return null;
    }

    @Override
    public DataTableResponse<Department> findAll(DataTableRequest request) {
        List<Department> departments = new ArrayList<>();
        Map<Object, List<Integer>> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String query = "select d.id, d.created, d.updated, d.visible, department_type, name, count(d.id) as employees " +
                "from departments as d inner join employees as e on d.id = e.department_id " +
                "group by d.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                CustomResultSet<Department> customResultSet = convertResultSetToDepartment(resultSet);
                departments.add(customResultSet.getEntity());
                otherParamMap.put(customResultSet.getEntity().getId(), (List<Integer>) customResultSet.getParams());
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Department> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(departments);
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public int count() {
        return count("departments");
    }

    private CustomResultSet<Department> convertResultSetToDepartment(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String typeName = resultSet.getString("department_type");
        Instant created = resultSet.getTimestamp("created").toInstant();
        Instant updated = resultSet.getTimestamp("updated").toInstant();
        boolean visible = resultSet.getBoolean("visible");
        DepartmentType departmentType = DepartmentType.valueOf(typeName);
        int employees = resultSet.getInt("employees");
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setDepartmentType(departmentType);
        department.setCreated(created);
        department.setUpdated(updated);
        department.setVisible(visible);
        return new CustomResultSet<>(department, Collections.singletonList(employees));

    }
}
