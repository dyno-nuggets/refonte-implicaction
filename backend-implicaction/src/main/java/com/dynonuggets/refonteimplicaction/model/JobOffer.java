package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "job_offer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobOffer {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    private String title;
    private String description;
    @Column(name = "added_at")
    private String addedAt;
    private String address;
    private String locality;
    private String postcode;
    private String country;
    private Company society;
}
