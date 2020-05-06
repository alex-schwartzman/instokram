package com.alex.instokram.dao.jpa;

import com.alex.instokram.domain.UserUploadedImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImagesRepository extends PagingAndSortingRepository<UserUploadedImage, Long>{
    UserUploadedImage findImageByUploadedBy(String uploadedBy);
    Page<UserUploadedImage> findAll(Pageable pageable);
}
