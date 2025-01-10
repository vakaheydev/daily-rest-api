package service;

import com.vaka.daily.Application;
import com.vaka.daily.domain.User;
import com.vaka.daily.domain.UserNotification;
import com.vaka.daily.repository.UserNotificationRepository;
import com.vaka.daily.service.domain.UserNotificationService;
import com.vaka.daily.service.domain.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {Application.class})
@Slf4j
@Transactional
public class UserNotificationServiceTest {
    @Autowired
    UserNotificationService userNotificationService;

    @Autowired
    UserService userService;

    @Autowired
    UserNotificationRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        entityManager.createNativeQuery("alter sequence user_notification_user_notification_id_seq restart with " + getNewId())
                .executeUpdate();
    }

    Integer getNewId() {
        return Math.toIntExact(repo.count() + 1);
    }

    @DisplayName("Should create new notification")
    @Test
    public void shouldCreate() {
        User user = userService.getById(1);
        UserNotification userNotification = new UserNotification();

        userNotification.setUser(user);
        userNotification.setLastNotifiedAt(LocalDateTime.now());

        userNotificationService.create(userNotification);

        UserNotification byUserId = userNotificationService.getByUserId(1);

        log.info(byUserId.toString());
        assertNotNull(byUserId);

        UserNotification byId = userNotificationService.getById(1);
        log.info(byId.toString());

        assertNotNull(byId);

        List<UserNotification> notifications = userNotificationService.getAll();

        assertEquals(1, notifications.size());
        assertEquals(1, notifications.get(0).getId());

        assertEquals(userNotification.getLastNotifiedAt(), userNotificationService.getById(1).getLastNotifiedAt());
    }
}
