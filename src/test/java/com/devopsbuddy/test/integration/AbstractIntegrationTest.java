package com.devopsbuddy.test.integration;

import com.devopsbuddy.backend.persistance.domain.backend.*;
import com.devopsbuddy.backend.persistance.repositories.PasswordResetTokenRepository;
import com.devopsbuddy.backend.persistance.repositories.PlanRepository;
import com.devopsbuddy.backend.persistance.repositories.RoleRepository;
import com.devopsbuddy.backend.persistance.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractIntegrationTest {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;


    protected Plan createPlan(PlansEnum plansEnum){
        return new Plan(plansEnum);
    }

    protected Role createRole(RolesEnum rolesEnum){
        return new Role(rolesEnum);
    }

    protected User createUser(String username, String password) {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(username,password);
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);
        return basicUser;
    }

    protected User createUser(TestName testName) {
        return createUser(testName.getMethodName(),testName+"@gmail.com");
    }

    private PasswordResetToken createPasswordResetToken(String token, User user, LocalDateTime now) {


        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, expirationTimeInMinutes);
        passwordResetTokenRepository.save(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getId());
        return passwordResetToken;

    }



}
