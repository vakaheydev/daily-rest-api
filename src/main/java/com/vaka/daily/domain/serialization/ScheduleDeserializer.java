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
        if (idNode != null) {
            return idNode.asInt();
        }

        return null;
    }


    private String parseName(JsonNode scheduleNode) {
        JsonNode nameNode = scheduleNode.get("name");
        if (nameNode != null) {
            return nameNode.asText();
        }

        return null;
    }

    private User parseUser(JsonNode scheduleNode) {
        JsonNode scheduleUser = scheduleNode.get("user");
        if (scheduleUser != null) {
            int userId = scheduleUser.findValue("id").asInt();
            return userService.getById(userId);
        }

        return null;
    }

    private List<Task> parseTasks(Schedule schedule, JsonNode scheduleNode) {
        JsonNode tasksNode = scheduleNode.get("tasks");

        if (tasksNode == null) {
            return null;
        }

        List<Task> tasks = new ArrayList<>();
        tasksNode.forEach(node -> {
            String name = node.findValue("name").asText();
            String description = node.findValue("description").asText();
            LocalDateTime deadLine = LocalDateTime.parse(node.findValue("deadline").asText());
            boolean status = node.findValue("status").asBoolean();

            Task task = Task.builder()
                    .name(name)
                    .description(description)
                    .deadline(deadLine)
                    .status(status)
                    .schedule(schedule)
                    .build();

            tasks.add(task);
        });

        schedule.setTasks(tasks);

        return tasks;
    }
}
