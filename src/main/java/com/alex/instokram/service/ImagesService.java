package com.alex.instokram.service;

import com.alex.instokram.dao.jpa.ImagesRepository;
import com.alex.instokram.dao.jpa.UsersRepository;
import com.alex.instokram.domain.UserUploadedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImagesService {
    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    public ImagesService() {
    }

    public UserUploadedImage uploadImage(UserUploadedImage image) {
        if (!usersRepository.existsById(image.getUser().getId())) {
            usersRepository.save(image.getUser());
        }
        return imagesRepository.save(image);
    }

    public Optional<UserUploadedImage> getImage(long id) {
        return imagesRepository.findById(id);
    }

    public void deleteImage(long id) {
        imagesRepository.deleteById(id);
    }

    public Page<UserUploadedImage> getAllImages(Integer page, Integer size) {
        return imagesRepository.findAll(PageRequest.of(page, size));
    }
}
