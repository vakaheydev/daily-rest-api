package com.vaka.daily.domain.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaka.daily.domain.Schedule;
import com.vaka.daily.domain.Task;
import com.vaka.daily.domain.User;
import com.vaka.daily.service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDeserializer extends JsonDeserializer<Schedule> {
    UserService userService;
    ObjectMapper mapper;

    public ScheduleDeserializer(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public Schedule deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode scheduleNode = p.getCodec().readTree(p);

        Schedule schedule = new Schedule();

        schedule.setId(parseId(scheduleNode));
        schedule.setName(parseName(scheduleNode));
        schedule.setUser(parseUser(scheduleNode));

        List<Task> tasks = parseTasks(schedule, scheduleNode);
        schedule.setTasks(tasks == null ? new ArrayList<>() : tasks);

        return schedule;
    }

    private Integer parseId(JsonNode scheduleNode) {
        JsonNode idNode = scheduleNode.get("id");
        if (!idNode.isNull()) {
            return idNode.asInt();
        }

        return null;
    }


    private String parseName(JsonNode scheduleNode) {
        JsonNode nameNode = scheduleNode.get("name");
        if (!nameNode.isNull()) {
            return nameNode.asText();
        }

        return null;
    }

    private User parseUser(JsonNode scheduleNode) {
        JsonNode scheduleUser = scheduleNode.get("user");
        if (!scheduleUser.isNull()) {
            int userId = scheduleUser.findValue("id").asInt();
            return userService.getById(userId);
        }

        return null;
    }

    private List<Task> parseTasks(Schedule schedule, JsonNode scheduleNode) {
        JsonNode tasksNode = scheduleNode.get("tasks");

        if (tasksNode.isNull()) {
            return null;
        }

        List<Task> tasks = new ArrayList<>();
        tasksNode.forEach(node -> {
            Integer id = (node.findValue("id") == null ? null : node.findValue("id").asInt());
            String name = node.findValue("name").asText();
            String description = node.findValue("description").asText();
            JsonNode deadlineNode = node.findValue("deadline");
            LocalDateTime deadline;
            if (deadlineNode.isArray()) {
                int year = deadlineNode.get(0) == null ? 1970 : deadlineNode.get(0).asInt();
                int month = deadlineNode.get(1) == null ? 1 : deadlineNode.get(1).asInt();
                int day = deadlineNode.get(2) == null ? 1 : deadlineNode.get(2).asInt();
                int hour = deadlineNode.get(3) == null ? 0 : deadlineNode.get(3).asInt();
                int minute = deadlineNode.get(4) == null ? 0 : deadlineNode.get(4).asInt();
                int second = deadlineNode.get(5) == null ? 0 : deadlineNode.get(5).asInt();
                int nanoSecond = deadlineNode.get(6) == null ? 0 : deadlineNode.get(6).asInt();
                deadline = LocalDateTime.of(year, month, day, hour, minute, second, nanoSecond);
            } else {
                deadline = LocalDateTime.parse(deadlineNode.asText());
            }

            boolean status = node.findValue("status").asBoolean();

            Task task = Task.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .deadline(deadline)
                    .status(status)
                    .schedule(schedule)
                    .build();

            tasks.add(task);
        });

        schedule.setTasks(tasks);

        return tasks;
    }
}
