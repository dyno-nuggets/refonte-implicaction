package com.dynonuggets.refonteimplicaction.community.group.domain.model;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "ia_group")
public class Group {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Community name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @OneToMany(mappedBy = "group")
    private List<Post> posts;

    private Instant createdAt;

    @ManyToOne(fetch = LAZY)
    private ProfileModel profile;

    @ManyToOne(fetch = LAZY)
    private FileModel image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "groups")
    private List<ProfileModel> profiles;

    @Column(name = "valid")
    private boolean valid;
}
