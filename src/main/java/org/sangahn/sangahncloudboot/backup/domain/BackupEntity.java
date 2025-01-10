package org.sangahn.sangahncloudboot.backup.domain;

import jakarta.persistence.*;
import org.sangahn.sangahncloudboot.file.domain.FileEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "backup")
public class BackupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity file;

    @Column(name = "backup_path", nullable = false)
    private String backupPath;

    @Column(name = "backup_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime backupDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_provider", nullable = false)
    private StorageProvider storageProvider;

    public enum StorageProvider {
        AWS_S3,
        LOCAL
    }
}
