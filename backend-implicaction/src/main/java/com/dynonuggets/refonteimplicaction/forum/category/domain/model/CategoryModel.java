package com.dynonuggets.refonteimplicaction.forum.category.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "title is required")
    private String title;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "description is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryModel parent;

    @OneToMany(mappedBy = "parent")
    private List<CategoryModel> children;
}
