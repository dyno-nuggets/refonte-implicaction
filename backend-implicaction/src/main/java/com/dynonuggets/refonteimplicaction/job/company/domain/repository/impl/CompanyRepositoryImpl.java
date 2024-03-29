package com.dynonuggets.refonteimplicaction.job.company.domain.repository.impl;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import com.dynonuggets.refonteimplicaction.job.company.domain.repository.CompanyRepositoryCustom;
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
    public Page<CompanyModel> findAllWithCriteria(final Pageable pageable, String keyword) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<CompanyModel> query = criteriaBuilder.createQuery(CompanyModel.class);
        final Root<CompanyModel> queryRoot = query.from(CompanyModel.class);
        final List<Predicate> predicates = new ArrayList<>();

        // gestion du tri des résultats
        if (!pageable.getSort().isEmpty()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), queryRoot, criteriaBuilder));
        }

        // recherche par mot clé dans le nom et description
        if (StringUtils.isNotEmpty(keyword)) {
            keyword = "%" + keyword + "%";
            final Predicate namePredicate = criteriaBuilder.like(queryRoot.get("name"), keyword);
            final Predicate descriptionPredicate = criteriaBuilder.like(queryRoot.get("description"), keyword);
            predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
        }


        // combinaison des différents prédicats
        final Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        query.where(finalPredicate);

        final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(CompanyModel.class)));
        final Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        final int firstResult = pageable.getPageNumber() * pageable.getPageSize();

        // lancement de la requête et mise en place de la pagination
        final List<CompanyModel> results = entityManager.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(results, pageable, () -> totalCount);
    }
}
