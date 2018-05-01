package com.devopsbuddy;

import com.devopsbuddy.backend.persistance.domain.backend.Role;
import com.devopsbuddy.backend.persistance.domain.backend.User;
import com.devopsbuddy.backend.persistance.domain.backend.UserRole;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DevopsbuddyApplication implements CommandLineRunner {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(DevopsbuddyApplication.class);

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DevopsbuddyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String username= "prodUser";
        String email= "prodUser@ggmail.com";

        User user = UserUtils.createBasicUser(username,email);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, new Role(RolesEnum.PRO)));
        LOG.debug("Creating user with username {}", user.getUsername());

        for (UserRole ur : userRoles) {
            LOG.debug("Check role details {}", ur.getUser().getUsername());
            LOG.debug("Check role details {}", ur.getRole().getName());


        }
        userService.createUser(user, PlansEnum.PRO, userRoles);
        LOG.info("User {} created", user.getUsername());
    }
}