package com.dynonuggets.refonteimplicaction.community.training.domain.model;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
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
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ProfileModel profile;
    private String label;
    private LocalDate date;
    private String school;
}
