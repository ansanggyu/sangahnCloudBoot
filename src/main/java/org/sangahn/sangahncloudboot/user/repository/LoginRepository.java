package org.sangahn.sangahncloudboot.user.repository;

import org.sangahn.sangahncloudboot.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<UserEntity, String> {
}
