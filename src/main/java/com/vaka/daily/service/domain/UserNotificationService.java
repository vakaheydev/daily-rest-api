package com.vaka.daily.service.domain;

import com.vaka.daily.domain.UserNotification;
import com.vaka.daily.service.abstraction.CommonService;

public interface UserNotificationService extends CommonService<UserNotification> {
    UserNotification getByUserId(Integer userId);
}
