package com.devopsbuddy.backend.persistance.repositories;

import com.devopsbuddy.backend.persistance.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    /**
     * Return a User given a username or null if not found
     * @param username
     * @return
     */
    public User findByUsername(String username);

}
