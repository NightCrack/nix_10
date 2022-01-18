package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.ClientDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Client entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Client entity) {
        entityManager.merge(entity);
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
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        Long count = (Long) entityManager.createQuery("select count(id) from ActionType").getSingleResult();
        if (count == 0) {
            initActionTypes();
        }
        int findRequest = (request.getPageNumber() - 1) * request.getPageSize();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> from = criteriaQuery.from(Client.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSortingCriteria())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSortingCriteria())));
        }
        List<Client> clients = entityManager.createQuery(criteriaQuery)
                .setFirstResult(findRequest)
                .setMaxResults(request.getPageSize())
                .getResultList();
        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setEntries(clients);
        return dataTableResponse;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(id) from Client");
        return (Long) query.getSingleResult();
    }

    @Override
    public Account findForeignEntityById(Long id) {
        return null;
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long clientId) {
        Map<Long, String> map = new HashMap<>();
        Set<Account> accounts = findById(clientId).getAccounts();
        for (Account account : accounts) {
            map.put(account.getId(), account.getAccountName());
        }
        return map;
    }

    @Override
    public ActionType findEntityByParameter(String parameterName, String parameterValue) {
        return null;
    }

    @Override
    public List<Client> findAllByForeignId(Long foreignId) {
        return null;
    }

    @Override
    public List<Client> findAllBySecondForeignId(Long secondForeignId) {
        return null;
    }

    private void initActionTypes() {
        for (int i = 0; i < ActionType.typeName.values().length; i++) {
            ActionType actionType = new ActionType();
            actionType.setTypeName(ActionType.typeName.values()[i]);
            if (ActionType.typeName.values()[i].getTransactionType().equals("+")) {
                actionType.setTransactionType(true);
            } else actionType.setTransactionType(false);
            actionType.setCreated(Instant.now());
            actionType.setUpdated(actionType.getCreated());
            entityManager.persist(actionType);
        }
    }
}
