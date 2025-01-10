package com.vaka.daily.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "User_Notification")
@Data
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_notification_id")
    private Integer id;

    @Column(name = "last_notified")
    private LocalDateTime lastNotifiedAt;

    @OneToOne
    @JoinColumn(name = "id_user", unique = true)
    @JsonIgnore
    private User user;
}
