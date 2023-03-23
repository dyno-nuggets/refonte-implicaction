package com.dynonuggets.refonteimplicaction.model;

import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "job_application")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private JobPosting job;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Enumerated(EnumType.STRING)
    private ApplyStatusEnum status;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(columnDefinition = "boolean default false")
    private boolean archive;
}
