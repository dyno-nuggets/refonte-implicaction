package com.dynonuggets.refonteimplicaction.job.jobapplication.domain.model;

import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.ApplyStatusEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
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
public class JobApplicationModel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private JobPostingModel job;

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
