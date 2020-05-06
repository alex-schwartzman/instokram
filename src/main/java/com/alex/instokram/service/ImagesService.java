package com.alex.instokram.service;

import com.alex.instokram.dao.jpa.ImagesRepository;
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

    public ImagesService() {
    }

    public UserUploadedImage uploadImage(UserUploadedImage image) {
        return imagesRepository.save(image);
    }

    public Optional<UserUploadedImage> getImage(long id) {
        return imagesRepository.findById(id);
    }

    public void updateImage(UserUploadedImage image) {
        imagesRepository.save(image);
    }

    public void deleteImage(long id) {
        imagesRepository.deleteById(id);
    }

    public Page<UserUploadedImage> getAllImages(Integer page, Integer size) {
        return imagesRepository.findAll(PageRequest.of(page, size));
    }
}
