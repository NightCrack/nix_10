package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.dao.AccountDao;
import ua.com.alevel.dao.ActionDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Action;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActionDaoImpl implements ActionDao {

    private static final String OUT_ACCOUNT_ID = "select * from actions where account_id = ";
    private static final String OUT_CLIENT_ID = "select act.id,act.account_id,act.action_type_id,act.amount,act.created,act.updated from actions as act inner join accounts as acc on act.account_id = acc.id where acc.client_id = ";

    private final JpaConfig jpaConfig;
    private final AccountDao accountDao;

    @PersistenceContext
    EntityManager entityManager;

    public ActionDaoImpl(JpaConfig jpaConfig, AccountDao accountDao) {
        this.jpaConfig = jpaConfig;
        this.accountDao = accountDao;
    }

    @Override
    public void create(Action action) {
        entityManager.persist(action);
    }

    @Override
    public void update(Action action) {
        entityManager.merge(action);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.contains(findById(id));
    }

    @Override
    public Action findById(Long id) {
        return entityManager.find(Action.class, id);
    }

    @Override
    public DataTableResponse<Action> findAll(DataTableRequest request) {
        int findRequest = (request.getPageNumber() - 1) * request.getPageSize();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Action> criteriaQuery = criteriaBuilder.createQuery(Action.class);
        Root<Action> from = criteriaQuery.from(Action.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSortingCriteria())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSortingCriteria())));
        }
        List<Action> actions = entityManager.createQuery(criteriaQuery)
                .setFirstResult(findRequest)
                .setMaxResults(request.getPageSize())
                .getResultList();
        DataTableResponse<Action> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setEntries(actions);
        return dataTableResponse;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(action_id) from actions");
        return (Long) query.getSingleResult();
    }

    @Override
    public BaseEntity findForeignEntityById(Long id) {
        return null;
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long id) {
        return null;
    }

    @Override
    public ActionType findEntityByParameter(String parameterName, String parameterValue) {
        try {
            Integer.parseInt(parameterValue);
        } catch (Exception exception) {
            parameterValue = "'" + parameterValue + "'";
        }
        Query query = entityManager.createNativeQuery("select id,created,updated,deposit,type_name from action_types where " + parameterName + " = " + parameterValue, ActionType.class);
        return (ActionType) query.getSingleResult();
    }

    @Override
    public List<Action> findAllByForeignId(Long accountId) {
        List<Action> actions = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(OUT_ACCOUNT_ID + accountId)) {
            while (resultSet.next()) {
                actions.add(convertResultSetToOperation(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return actions;
    }

    @Override
    public List<Action> findAllBySecondForeignId(Long clientId) {
        List<Action> actions = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(OUT_CLIENT_ID + clientId)) {
            while (resultSet.next()) {
                actions.add(convertResultSetToOperation(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return actions;
    }

    private Action convertResultSetToOperation(ResultSet resultSet) throws SQLException {
        Long actionId = resultSet.getLong("id");
        Long accountId = resultSet.getLong("account_id");
        String actionTypeId = resultSet.getString("action_type_id");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        Action action = new Action();
        action.setId(actionId);
        action.setActionType(findEntityByParameter("id", actionTypeId));
        action.setAccount(accountDao.findById(accountId));
        action.setAmount(amount);
        action.setCreated(created.toInstant());
        action.setCreated(updated.toInstant());
        return action;
    }
}
