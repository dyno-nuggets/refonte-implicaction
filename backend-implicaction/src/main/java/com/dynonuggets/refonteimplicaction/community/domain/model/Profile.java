package com.dynonuggets.refonteimplicaction.community.domain.model;

import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile {
    @Id
    @Column(name = "user_id")
    private Long id;
    @MapsId
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    private UserModel user;
    private String hobbies;
    private String purpose;
    private String presentation;
    private String expectation;
    private String contribution;
    @ManyToOne
    private FileModel avatar;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(fetch = LAZY, mappedBy = "profile")
    private List<WorkExperience> experiences;
    @OneToMany(fetch = LAZY, mappedBy = "profile")
    private List<Training> trainings;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<Group> groups;
}
