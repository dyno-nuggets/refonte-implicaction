package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

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

    @ManyToOne(fetch = LAZY)
    private User uploader;
}
