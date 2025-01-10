package com.vaka.daily.service.domain;

import com.vaka.daily.domain.Schedule;
import com.vaka.daily.domain.User;
import com.vaka.daily.domain.UserType;
import com.vaka.daily.domain.dto.UserDto;
import com.vaka.daily.exception.UserNotFoundException;
import com.vaka.daily.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;
    private final UserTypeService userTypeService;
    private final ScheduleService scheduleService;

    @Autowired
    public SimpleUserService(UserRepository userRepository, UserTypeService userTypeService,
                             ScheduleService scheduleService) {
        this.userRepository = userRepository;
        this.userTypeService = userTypeService;
        this.scheduleService = scheduleService;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getByUniqueName(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public User getByTgId(Long tgId) {
        return userRepository.findByTelegramId(tgId).orElseThrow(() -> new UserNotFoundException(tgId));
    }

    @Override
    public List<User> getByUserTypeName(String userTypeName) {
        return userRepository.findByUserTypeName(userTypeName);
    }

    @Override
    public User createFromDTO(UserDto userDTO) {
        User user = convertFromDTO(userDTO);

        UserType defaultUserType = userTypeService.getDefaultUserType();
        user.setUserType(defaultUserType);

        Schedule schedule = scheduleService.createDefaultSchedule(user);
        user.addSchedule(schedule);

        return userRepository.save(user);
    }

    @Override
    public User create(User entity) {
        if (entity.getUserType() == null) {
            UserType defaultUserType = userTypeService.getDefaultUserType();
            entity.setUserType(defaultUserType);
        }

        User saved = userRepository.save(entity);

        if (entity.getSchedules().isEmpty()) {
            saved.addSchedule(scheduleService.createDefaultSchedule(entity));
        }

        return saved;
    }

    @Override
    public User updateById(Integer id, User entity) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        entity.setId(id);
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }

    private User convertFromDTO(UserDto userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setSecondName(userDTO.getSecondName());
        user.setPatronymic(userDTO.getPatronymic());

        return user;
    }
}