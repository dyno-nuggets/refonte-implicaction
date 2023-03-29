package com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository.impl;

import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPosting;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository.JobPostingRepositoryCustom;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum;
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
public class JobPostingRepositoryImpl implements JobPostingRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public Page<JobPosting> findAllWithCriteria(final Pageable pageable, String search, final ContractTypeEnum contractType, final BusinessSectorEnum businessSector, final Boolean archive, final Boolean valid) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<JobPosting> query = criteriaBuilder.createQuery(JobPosting.class);
        final Root<JobPosting> queryRoot = query.from(JobPosting.class);
        final List<Predicate> predicates = new ArrayList<>();

        // gestion du tri des résultats
        if (!pageable.getSort().isEmpty()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), queryRoot, criteriaBuilder));
        }

        // recherche par mot clé dans le titre, description et keywords
        if (StringUtils.isNotEmpty(search)) {
            search = "%" + search + "%";
            final Predicate titlePredicate = criteriaBuilder.like(queryRoot.get("title"), search);
            final Predicate descriptionPredicate = criteriaBuilder.like(queryRoot.get("description"), search);
            final Predicate keywordsPredicate = criteriaBuilder.like(queryRoot.get("keywords"), search);
            predicates.add(criteriaBuilder.or(titlePredicate, descriptionPredicate, keywordsPredicate));
        }

        // recherche par type de contrat
        if (contractType != null) {
            predicates.add(criteriaBuilder.equal(queryRoot.get("contractType"), contractType));
        }

        // recherche par secteur d'activité
        if (businessSector != null) {
            predicates.add(criteriaBuilder.equal(queryRoot.get("businessSector"), businessSector));
        }

        if (archive != null) {
            predicates.add(criteriaBuilder.equal(queryRoot.get("archive"), archive));
        }

        if (valid != null) {
            predicates.add(criteriaBuilder.equal(queryRoot.get("valid"), valid));
        }

        // combinaison des différents prédicats
        final Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        query.where(finalPredicate);

        final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(JobPosting.class)));
        final Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        final int firstResult = pageable.getPageNumber() * pageable.getPageSize();

        // lancement de la requête et mise en place de la pagination
        final List<JobPosting> results = entityManager.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(results, pageable, () -> totalCount);
    }
}
