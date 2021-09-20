package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String label;
    private LocalDate date;
    private String school;
}
