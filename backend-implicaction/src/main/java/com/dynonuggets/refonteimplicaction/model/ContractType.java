package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "contract_type")
public class ContractType {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "label", nullable = false)
    private String label;
}
