package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.model.ContractTypeEnum;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostingRepositoryCustom {

    /**
     * @param pageable     l'objet de pagination
     * @param search       la chaîne de caratères à rechercher dans les champs title, description, keywords
     * @param contractType le type de contrat à rechercher (CDD, CDI)
     * @param valid        la validation de l'offre par l'administrateur
     * @return la liste de résultats paginée des JobPostings correspondant aux critères
     */
    Page<JobPosting> findAllWithCriteria(final Pageable pageable, final String search, final ContractTypeEnum contractType, BusinessSectorEnum businessSectorEnum, final Boolean archive, Boolean valid);

}
