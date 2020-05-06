package com.alex.instokram.dao.jpa;

import com.alex.instokram.domain.User;
import com.alex.instokram.domain.UserUploadedImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
}
