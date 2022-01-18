package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.AccountDao;
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
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Account account) {
        entityManager.persist(account);
    }

    @Override
    public void update(Account account) {
        entityManager.merge(account);
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
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        int findRequest = (request.getPageNumber() - 1) * request.getPageSize();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSortingCriteria())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSortingCriteria())));
        }
        List<Account> accounts = entityManager.createQuery(criteriaQuery)
                .setFirstResult(findRequest)
                .setMaxResults(request.getPageSize())
                .getResultList();
        DataTableResponse<Account> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setEntries(accounts);
        return dataTableResponse;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(id) from Account");
        return (Long) query.getSingleResult();
    }

    @Override
    public Client findForeignEntityById(Long accountId) {
        return findById(accountId).getClient();
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long id) {
        return null;
    }

    @Override
    public ActionType findEntityByParameter(String parameterName, String parameterValue) {
        return null;
    }

    @Override
    public List<Account> findAllByForeignId(Long foreignId) {
        return null;
    }

    @Override
    public List<Account> findAllBySecondForeignId(Long secondForeignId) {
        return null;
    }
}
