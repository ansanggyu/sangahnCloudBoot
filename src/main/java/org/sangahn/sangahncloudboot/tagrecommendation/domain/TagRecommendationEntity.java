package org.sangahn.sangahncloudboot.tagrecommendation.domain;

import jakarta.persistence.*;
import org.sangahn.sangahncloudboot.file.domain.FileEntity;
import org.sangahn.sangahncloudboot.user.domain.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tagrecommendation")
public class TagRecommendationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_recommendation_id", nullable = false)
    private Long tagRecommendationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity fileId;

    @Column(name = "recommended_tag", nullable = false)
    private String recommendedTag;

    @Column(name = "confidence", nullable = false)
    private Double confidence;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
