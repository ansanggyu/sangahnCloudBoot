package org.sangahn.sangahncloudboot.folder.domain;

import jakarta.persistence.*;
import org.sangahn.sangahncloudboot.common.BasicEntity;
import org.sangahn.sangahncloudboot.user.domain.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder")
public class FolderEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id", nullable = false)
    private Long folderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_owner_id", nullable = false)
    private UserEntity folderOwnerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id")
    private FolderEntity parentFolderId;

    @OneToMany(mappedBy = "parentFolderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolderEntity> subFolders = new ArrayList<>();

    @Column(name = "folder_name", nullable = false)
    private String folderName;

    @Column(name = "folder_is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean folderIsDeleted = Boolean.FALSE;

    @Column(name = "folder_deleted_at")
    private LocalDateTime folderDeletedAt;
}
