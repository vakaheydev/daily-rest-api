package com.vaka.daily.service.scheduler.format;

import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.TaskType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class FormatTaskService {
    public String formatTaskForNotification(Task task) {
        String formattedTask = formatTask(task);
        String msg;

        if (task.getTaskType().getName().equals("repetitive")) {
            if (task.getDeadline().isBefore(LocalDateTime.now())) {
                msg = String.format("Вы забыли выполнить регулярное задание\n\n%s", formattedTask);
            } else {
                Period between = Period.between(LocalDateTime.now().toLocalDate(), task.getDeadline().toLocalDate());
                int days = between.getDays();

                if (days == 1) {
                    msg = String.format("Не забудьте!\n\nСкоро нужно будет: %s", formattedTask);
                } else {
                    return null;
                }
            }
        } else {
            msg = String.format("Напоминание\n\nУ Вас есть нерешённая задача: %s", formattedTask);
        }

        return msg;
    }

    private String formatTask(Task task) {
        StringBuilder msg = new StringBuilder();
        msg.append(String.format("\nЗадание '%s' | %s | до %s | Тип: %s",
                task.getName(),
                task.getDescription(),
                formatDeadLine(task.getDeadline()),
                formatTaskType(task.getTaskType())));
        msg.append("\n---\n");
        return msg.toString();
    }

    private String formatTaskType(TaskType taskType) {
        return switch (taskType.getName()) {
            case "singular" -> "Одиночное";
            case "repetitive" -> "Повторяемое";
            case "regular" -> "Регулярное";
            default -> "Неизвестный тип";
        };
    }

    private String formatDeadLine(LocalDateTime deadLine) {
        return deadLine.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.forLanguageTag("ru")));
    }
}

