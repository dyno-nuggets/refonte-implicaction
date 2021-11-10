package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {
    /**
     * @param pageable l'objet de pagination
     * @param name     la chaîne de caractère à rechercher dans les champs name, description
     * @return la liste de résultats paginée des Company correspondant aux critères
     */
    Page<Company> findAllWithCriteria(final Pageable pageable, final String name);

}