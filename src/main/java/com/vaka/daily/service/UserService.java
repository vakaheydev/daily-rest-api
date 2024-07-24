package com.vaka.daily.service;

import com.vaka.daily.abstraction.CommonService;
import com.vaka.daily.domain.User;
import com.vaka.daily.domain.dto.UserDTO;

import java.util.List;

public interface UserService extends CommonService<User> {
    List<User> getByUserTypeName(String userTypeName);

    User getByUniqueName(String name);

    User createFromDTO(UserDTO userDTO);
}
