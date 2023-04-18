package com.dynonuggets.refonteimplicaction.job.company.domain.repository;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {
    /**
     * @param pageable l'objet de pagination
     * @param keyword  la chaîne de caractère à rechercher dans les champs keyword, description
     * @return la liste de résultats paginée des Company correspondant aux critères
     */
    Page<CompanyModel> findAllWithCriteria(final Pageable pageable, final String keyword);

}
