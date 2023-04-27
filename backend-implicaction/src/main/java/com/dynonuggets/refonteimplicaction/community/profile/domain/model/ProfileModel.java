package com.dynonuggets.refonteimplicaction.community.profile.domain.model;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.community.training.domain.model.TrainingModel;
import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperienceModel;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.forum.response.domain.model.ResponseModel;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProfileModel {
    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Long id;
    @MapsId
    @OneToOne(cascade = MERGE)
    @JoinColumn(name = "user_id")
    private UserModel user;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    private String imageUrl;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(fetch = LAZY, mappedBy = "profile")
    private List<WorkExperienceModel> experiences;
    @OneToMany(fetch = LAZY, mappedBy = "profile")
    private List<TrainingModel> trainings;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<GroupModel> groups;

    @OneToMany(mappedBy = "author")
    private List<TopicModel> topics;

    @OneToMany(mappedBy = "author")
    private List<ResponseModel> responses;
}
