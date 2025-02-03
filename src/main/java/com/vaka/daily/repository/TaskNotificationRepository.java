package com.vaka.daily.repository;

import com.vaka.daily.domain.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    @Query("select un from UserNotification un where un.user.id = :userId")
    Optional<UserNotification> findByUserId(@Param("userId") Integer userId);
}
