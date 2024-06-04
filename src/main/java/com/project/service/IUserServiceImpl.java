package com.project.service;

import com.project.configuration.JwtProvider;
import com.project.dao.IUserDao;
import com.project.entities.User;
import com.project.helper.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;




    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userDao.findByEmail(email);

        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = Optional.ofNullable(userDao.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("User not found");
        }));
        return user.get();
    }

    @Override
    public User updateUserProjectSize(User user, int projectSize) {
        user.setProjectsSize(user.getProjectsSize() + projectSize);
        return userDao.save(user);
    }
}
