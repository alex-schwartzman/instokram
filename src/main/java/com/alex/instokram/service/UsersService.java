package com.alex.instokram.service;

import com.alex.instokram.dao.jpa.ImagesRepository;
import com.alex.instokram.dao.jpa.UsersRepository;
import com.alex.instokram.domain.User;
import com.alex.instokram.domain.UserUploadedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
