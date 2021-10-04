package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "salary")
    private String salary;

    @Column(name = "keywords")
    private String keywords;

    @ManyToOne
    @JoinColumn(name = "contract_type_id", nullable = false)
    private ContractType contractType;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}
