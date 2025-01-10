package com.vaka.daily.service.domain;

import com.vaka.daily.domain.UserNotification;
import com.vaka.daily.exception.UserNotFoundException;
import com.vaka.daily.exception.UserNotificationNotFoundException;
import com.vaka.daily.repository.UserNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleUserNotificationService implements UserNotificationService {
    private final UserNotificationRepository repository;

    public SimpleUserNotificationService(UserNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserNotification> getAll() {
        return repository.findAll();
    }

    @Override
    public UserNotification getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotificationNotFoundException(id));
    }

    @Override
    public UserNotification getByUserId(Integer userId) {
        return repository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public UserNotification create(UserNotification entity) {
        return repository.save(entity);
    }

    @Override
    public UserNotification updateById(Integer id, UserNotification entity) {
        if (!repository.existsById(id)) {
            throw new UserNotificationNotFoundException(id);
        }

        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            throw new UserNotificationNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
