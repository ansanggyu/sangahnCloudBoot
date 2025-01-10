package org.sangahn.sangahncloudboot.file.domain;

import jakarta.persistence.*;
import org.sangahn.sangahncloudboot.common.BasicEntity;
import org.sangahn.sangahncloudboot.folder.domain.FolderEntity;
import org.sangahn.sangahncloudboot.user.domain.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
public class FileEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private FolderEntity folderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_owner_id", nullable = false)
    private UserEntity fileOwnerId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false, columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean fileIsDeleted = Boolean.FALSE;

    @Column(name = "file_deleted_at")
    private LocalDateTime fileDeletedAt;
}
