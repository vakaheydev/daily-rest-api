package com.vaka.daily.service;

import com.vaka.daily.abstraction.CommonService;
import com.vaka.daily.domain.Schedule;
import com.vaka.daily.domain.User;

import java.util.List;

public interface ScheduleService extends CommonService<Schedule> {
    List<Schedule> getByUserId(Integer id);

    List<Schedule> getByName(String name);

    Schedule createDefaultSchedule(User user);
}
