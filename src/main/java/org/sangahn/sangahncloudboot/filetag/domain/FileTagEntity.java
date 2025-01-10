package org.sangahn.sangahncloudboot.filetag.domain;

import jakarta.persistence.*;
import org.sangahn.sangahncloudboot.file.domain.FileEntity;

@Entity
@Table(name = "filetag")
public class FileTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filetag_id", nullable = false)
    private Long fileTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity fileId;

    @Column(name = "tag", nullable = false)
    private String tag;
}
