package com.devopsbuddy.test.integration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistance.domain.backend.Plan;
import com.devopsbuddy.backend.persistance.domain.backend.Role;
import com.devopsbuddy.backend.persistance.domain.backend.User;
import com.devopsbuddy.backend.persistance.domain.backend.UserRole;
import com.devopsbuddy.backend.persistance.repositories.PlanRepository;
import com.devopsbuddy.backend.persistance.repositories.RoleRepository;
import com.devopsbuddy.backend.persistance.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)

public class RepositoriesIntegrationTest {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }


    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.PRO);
        planRepository.save(basicPlan);
        Optional<Plan>  retrievedPlan = planRepository.findById(PlansEnum.PRO.getId());
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() throws Exception {
        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);
        Optional<Role>  retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);
    }



    @Test
    public void createNewUser() throws Exception {
        User basicUser = createUser(testName.getMethodName(),testName.getMethodName()+"@gmail.com");
        Optional<User> newlyCreatedUser = userRepository.findById(basicUser.getId());

        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.get().getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.get().getPlan());
        Assert.assertNotNull(newlyCreatedUser.get().getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.get().getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }

    }

    @Test
    public void testDeleteUser() throws Exception{
        User baseUser = createUser(testName.getMethodName(),testName.getMethodName()+"@gmail.com");
        userRepository.deleteById(baseUser.getId());

    }


    private Plan createPlan(PlansEnum plansEnum){
        return new Plan(plansEnum);
    }

    private Role createRole(RolesEnum rolesEnum){
        return new Role(rolesEnum);
    }

    private User createUser(String username, String password) {
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




}
