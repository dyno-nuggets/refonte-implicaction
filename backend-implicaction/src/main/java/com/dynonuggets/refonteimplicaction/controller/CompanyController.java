package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.COMPANIES_BASE_URI;

@RestController
@AllArgsConstructor
@RequestMapping(COMPANIES_BASE_URI)
public class CompanyController {


    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<Page<CompanyDto>> getAllByCriteria(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        Pageable pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        Page<CompanyDto> companyDtos = companyService.getAllWithCriteria(pageable, keyword);
        return ResponseEntity.ok(companyDtos);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<CompanyDto> createOrUpdate(@RequestBody CompanyDto companyDto) throws ImplicactionException {
        CompanyDto companyCreated = companyService.saveOrUpdateCompany(companyDto);
        return ResponseEntity.ok(companyCreated);
    }
}

