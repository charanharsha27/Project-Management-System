package com.project.service;

import com.project.entities.User;

public interface IUserService {

    User findUserByJwt(String jwt);

    User findUserByEmail(String email);

    User findUserById(Long id);

    User updateUserProjectSize(User user,int projectSize);
}
