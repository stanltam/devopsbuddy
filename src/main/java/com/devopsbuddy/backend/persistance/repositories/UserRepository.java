package com.devopsbuddy.backend.persistance.repositories;

import com.devopsbuddy.backend.persistance.domain.backend.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User,Long> {

    /**
     * Return a User given a username or null if not found
     * @param username
     * @return
     */
     User findByUsername(String username);

    /**
     * Return a User given a email or null if not found
     * @param email
     * @return
     */
     User findByEmail(String email);


    /**
     *
     * @param userId
     * @param password
     */
    @Modifying
    @Query("update User u set u.password = :password where u.id = :userId")
    void updateUserPassword(@Param("userId") long userId, @Param("password") String password);



}
