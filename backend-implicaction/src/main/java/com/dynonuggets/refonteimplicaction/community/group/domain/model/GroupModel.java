package com.dynonuggets.refonteimplicaction.community.group.domain.model;

import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ia_group")
public class GroupModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Community name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = LAZY)
    private ProfileModel creator;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "groups")
    private List<ProfileModel> profiles = new ArrayList<>();

    @Column(name = "enabled")
    private boolean enabled;
}
