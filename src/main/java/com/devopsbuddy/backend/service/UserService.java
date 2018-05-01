package com.devopsbuddy.backend.service;

import com.devopsbuddy.backend.persistance.domain.backend.Plan;
import com.devopsbuddy.backend.persistance.domain.backend.User;
import com.devopsbuddy.backend.persistance.domain.backend.UserRole;
import com.devopsbuddy.backend.persistance.repositories.PlanRepository;
import com.devopsbuddy.backend.persistance.repositories.RoleRepository;
import com.devopsbuddy.backend.persistance.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly=true)
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User createUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {

        String encryptPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);
        Plan plan = new Plan(plansEnum);
        // It makes sure the plans exist in the database
        if (!planRepository.existsById(plansEnum.getId())) {
            plan = planRepository.save(plan);
        }

        user.setPlan(plan);

        for (UserRole ur : userRoles) {
            roleRepository.save(ur.getRole());
        }

        user.getUserRoles().addAll(userRoles);

        user = userRepository.save(user);

        return user;

    }


}
