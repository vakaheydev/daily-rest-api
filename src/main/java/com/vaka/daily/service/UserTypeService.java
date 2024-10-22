package com.vaka.daily.service;

import com.vaka.daily.abstraction.CommonService;
import com.vaka.daily.domain.UserType;

public interface UserTypeService extends CommonService<UserType> {
    UserType getByUniqueName(String name);

    UserType getDefaultUserType();
}
