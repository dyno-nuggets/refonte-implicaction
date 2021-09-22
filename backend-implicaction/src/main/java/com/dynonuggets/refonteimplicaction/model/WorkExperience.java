package com.dynonuggets.refonteimplicaction.model;

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
@Table(name = "work_experience")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "started_at")
    private LocalDate startedAt;
    @Column(name = "finished_at")
    private LocalDate finishedAt;
    private String label;
    private String description;
    private String companyName;
}
