package com.alex.instokram.service;

import com.alex.instokram.dao.jpa.UsersRepository;
import com.alex.instokram.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersService() {
    }

    public User saveUser(User user) {
        return usersRepository.save(user);
    }
}
