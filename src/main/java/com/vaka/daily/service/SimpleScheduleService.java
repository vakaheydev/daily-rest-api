package com.vaka.daily.service;

import com.vaka.daily.domain.Schedule;
import com.vaka.daily.domain.User;
import com.vaka.daily.exception.ScheduleNotFoundException;
import com.vaka.daily.exception.UserNotFoundException;
import com.vaka.daily.repository.ScheduleRepository;
import com.vaka.daily.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SimpleScheduleService implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Autowired
    public SimpleScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getById(Integer id) {
        var temp = scheduleRepository.findById(id);
        return temp.orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    @Override
    public List<Schedule> getByName(String name) {
        return scheduleRepository.findByName(name);
    }

    @Override
    public Schedule createDefaultSchedule(User user) {
        Schedule schedule = create(new Schedule("main", user));

        return schedule;
    }

    @Override
    public List<Schedule> getByUserId(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        return scheduleRepository.findByUserId(id);
    }

    @Override
    public Schedule create(Schedule entity) {
        return scheduleRepository.save(entity);
    }

    @Override
    public Schedule updateById(Integer id, Schedule entity) {
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException(id);
        }

        entity.setId(id);
        return scheduleRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException(id);
        }

        scheduleRepository.deleteById(id);
    }
}
