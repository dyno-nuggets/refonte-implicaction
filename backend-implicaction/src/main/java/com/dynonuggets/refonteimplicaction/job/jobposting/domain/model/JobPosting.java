package com.dynonuggets.refonteimplicaction.job.jobposting.domain.model;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.Company;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "job_posting")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "salary")
    private String salary;

    @Column(name = "keywords")
    private String keywords;

    @Enumerated(EnumType.STRING)
    private ContractTypeEnum contractType;

    @Enumerated(EnumType.STRING)
    private BusinessSectorEnum businessSector;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @Column(name = "valid")
    private boolean valid;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private UserModel poster;

}