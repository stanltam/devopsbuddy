package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistance.domain.backend.User;

public class UserUtils {

    /**
     * Non instantiable.
     */
    private UserUtils() {
        throw new AssertionError("Non instantiable");
    }

    /**
     *reates a user with basic attributes set.
     * @param username
     * @param email
     * @return
     */
    public static User createBasicUser(String username, String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword("stanley");
        user.setEmail(email);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456789123");
        user.setCountry("GB");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/basicuser");

        return user;
    }

}
