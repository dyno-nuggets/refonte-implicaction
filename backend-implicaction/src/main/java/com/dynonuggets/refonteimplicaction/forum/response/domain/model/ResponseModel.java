package com.dynonuggets.refonteimplicaction.forum.response.domain.model;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "response")
public class ResponseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    @NotBlank(message = "message is required")
    private String message;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "edited_at", nullable = false)
    @UpdateTimestamp
    private Instant editedAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private ProfileModel author;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicModel topic;
}
