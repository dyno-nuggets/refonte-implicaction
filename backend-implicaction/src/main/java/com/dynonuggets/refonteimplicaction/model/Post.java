package com.dynonuggets.refonteimplicaction.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Post Name cannot be empty or Null")
    private String name;

    @Nullable
    private String url;

    @Nullable
    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer voteCount = 0;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;
}
