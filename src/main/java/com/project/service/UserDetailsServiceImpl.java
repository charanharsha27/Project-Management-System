package com.project.service;

import com.project.dao.IUserDao;
import com.project.entities.User;
import com.project.helper.ResourceNotFoundException;
import com.project.security.userDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User u = userDao.findByEmail(username);

        return new userDetails(u);
    }

}

