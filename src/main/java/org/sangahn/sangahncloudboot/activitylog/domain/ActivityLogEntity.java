package org.sangahn.sangahncloudboot.activitylog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sangahn.sangahncloudboot.common.BasicEntity;
import org.sangahn.sangahncloudboot.file.domain.FileEntity;
import org.sangahn.sangahncloudboot.folder.domain.FolderEntity;
import org.sangahn.sangahncloudboot.user.domain.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "activitylog")
public class ActivityLogEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_action", nullable = false)
    private LogAction logAction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileEntity fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private FolderEntity folderId;

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    public enum LogAction {
        UPLOAD,
        DOWNLOAD,
        DELETE,
        SHARE
    }
}
