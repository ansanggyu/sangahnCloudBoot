package org.sangahn.sangahncloudboot.share.domain;

import jakarta.persistence.*;
import org.sangahn.sangahncloudboot.file.domain.FileEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "share")
public class ShareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_id", nullable = false)
    private Long shareId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity fileId;

    @Column(name = "shared_with_userid", nullable = false)
    private String sharedWithUserId;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}
