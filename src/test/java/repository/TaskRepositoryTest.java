package repository;

import com.vaka.daily.Application;
import com.vaka.daily.domain.Task;
import com.vaka.daily.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DialectOverride;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = {Application.class})
@Slf4j
@Transactional
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @DisplayName("Get tasks for notification (not completed and deadline > now)")
    @Test
    public void shouldGetNotCompletedTasks() {
        List<Task> tasksForNotification = taskRepository.findTasksForNotification(LocalDateTime.now());
        log.info(tasksForNotification.toString());

        Assertions.assertEquals(3, tasksForNotification.size());
    }
}
