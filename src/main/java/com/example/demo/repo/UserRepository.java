package com.example.demo.repo;

import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    
    List<User> findByUsernameLike(String username);
    List<User> findByUsernameContains(String username);
    User findByUsername(String username);

    Iterable<User> findAllByUsernameNot(String username);
}