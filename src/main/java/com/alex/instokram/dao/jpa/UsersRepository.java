package com.alex.instokram.dao.jpa;

import com.alex.instokram.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
}
