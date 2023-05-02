package com.dynonuggets.refonteimplicaction.community.profile.domain.repository.impl;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepositoryCustom;
import com.dynonuggets.refonteimplicaction.community.profile.dto.enums.RelationCriteriaEnum;
import com.dynonuggets.refonteimplicaction.community.relation.domain.model.RelationModel;
import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.profile.dto.enums.RelationCriteriaEnum.ALL_FRIENDS;
import static com.dynonuggets.refonteimplicaction.community.profile.dto.enums.RelationCriteriaEnum.ONLY_FRIEND_REQUESTS;

@RequiredArgsConstructor
public class ProfileRepositoryCustomImpl implements ProfileRepositoryCustom {
    private final EntityManager entityManager;

    /**
     * @param username         nom de l'utilisateur courant
     * @param relationCriteria type de relation.
     * @param pageable         options de pagination
     * @return <ul>
     * <li>{@link ANY} retourne tous profils qu'ils soient en relation ou non avec l'utilisateur courant à l'exception de l'utilisateur courant</li>
     * <li>{@link ALL_FRIENDS} retourne toutes les relations confirmées de l'utilisateur (en tant que sender ou receiver) à l'exception de l'utilisateur courant</li>
     * <li>{@link ONLY_FRIEND_REQUESTS} retourne seulement les profiles qui ont effectué une demande de relation avec l'utilisateur courant à l'exception de l'utilisateur courant</li>
     */
    @Override
    public Page<ProfileModel> findAllProfilesWithRelationTypeCriteria(final String username, final RelationCriteriaEnum relationCriteria, final Pageable pageable) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProfileModel> query = builder.createQuery(ProfileModel.class);
        final Root<ProfileModel> p = query.from(ProfileModel.class);
        final List<Predicate> queryPredicates = new ArrayList<>();

        queryPredicates.add(builder.isTrue(p.get("user").get("enabled")));
        queryPredicates.add(builder.notEqual(p.get("user").get("username"), username));

        if (List.of(ALL_FRIENDS, ONLY_FRIEND_REQUESTS).contains(relationCriteria)) {
            final Subquery<Integer> subQuery = query.subquery(Integer.class);
            final Root<RelationModel> r = subQuery.from(RelationModel.class);

            // l'utilisateur doit être à la cible d'une invitation (receiver)
            Predicate relationPredicate = builder.and(builder.equal(r.get("receiver").get("user").get("username"), username), builder.equal(r.get("sender"), p));

            // si l'on veut remonter toutes les relatons de l'utilisateur, il faut aussi récupérer les profils pour lesquels il est à l'origine d'une relation
            if (relationCriteria == ALL_FRIENDS) {
                final Predicate isSender = builder.and(builder.equal(r.get("sender").get("user").get("username"), username), builder.equal(r.get("receiver"), p));
                relationPredicate = builder.or(relationPredicate, isSender);
            }

            // si 2 utilisateurs sont amis, alors la relation a été confirmée, sinon non
            final Predicate confirmedPredicate = relationCriteria == ALL_FRIENDS
                    ? builder.isNotNull(r.get("confirmedAt"))
                    : builder.isNull(r.get("confirmedAt"));

            subQuery
                    .select(builder.literal(1))
                    .where(relationPredicate, confirmedPredicate);

            queryPredicates.add(builder.exists(subQuery));
        }

        query.where(queryPredicates.toArray(Predicate[]::new));

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class).where(queryPredicates.toArray(Predicate[]::new));
        countQuery.select(builder.count(countQuery.from(CompanyModel.class)));
        final List<ProfileModel> profileModels = entityManager.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .getResultList();

        return PageableExecutionUtils.getPage(profileModels, pageable, () -> entityManager.createQuery(countQuery).getSingleResult());
    }
}
