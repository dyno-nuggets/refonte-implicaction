package com.dynonuggets.refonteimplicaction.filemanagement.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    private String url;

    @Column(name = "content_type")
    private String contentType;

    private String objectKey;

    @Builder.Default
    private Boolean publicAccess = false;
}
