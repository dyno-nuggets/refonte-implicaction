package com.dynonuggets.refonteimplicaction.repository.impl;

import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.repository.CompanyRepositoryCustom;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public Page<Company> findAllWithCriteria(Pageable pageable, String keyword) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = criteriaBuilder.createQuery(Company.class);
        Root<Company> queryRoot = query.from(Company.class);
        List<Predicate> predicates = new ArrayList<>();

        // gestion du tri des résultats
        if (!pageable.getSort().isEmpty()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), queryRoot, criteriaBuilder));
        }

        // recherche par mot clé dans le nom et description
        if (StringUtils.isNotEmpty(keyword)) {
            keyword = "%" + keyword + "%";
            Predicate namePredicate = criteriaBuilder.like(queryRoot.get("name"), keyword);
            Predicate descriptionPredicate = criteriaBuilder.like(queryRoot.get("description"), keyword);
            predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
        }


        // combinaison des différents prédicats
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        query.where(finalPredicate);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Company.class)));
        final Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        final int firstResult = pageable.getPageNumber() * pageable.getPageSize();

        // lancement de la requête et mise en place de la pagination
        final List<Company> results = entityManager.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(results, pageable, () -> totalCount);
    }
}
