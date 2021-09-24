package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_seeker")
public class JobSeeker {
    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<WorkExperience> experiences;

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<Training> trainings;
}
