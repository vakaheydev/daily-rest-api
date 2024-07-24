package com.vaka.daily.service;

import com.vaka.daily.abstraction.CommonService;
import com.vaka.daily.domain.UserType;

import java.util.List;

public interface UserTypeService extends CommonService<UserType> {
    UserType getByUniqueName(String name);
    UserType getDefaultUserType();
}
