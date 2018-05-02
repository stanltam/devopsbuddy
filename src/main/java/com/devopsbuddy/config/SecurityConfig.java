package com.devopsbuddy.config;

import com.devopsbuddy.backend.persistance.repositories.UserRepository;
import com.devopsbuddy.backend.service.PasswordEncoderService;
import com.devopsbuddy.backend.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private Environment env;

    /**The Encryption Salt*/
    private static final String SALT = "fdalkjalk;3jlwf00sfaof";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
    }

    /** Public URLs. */
    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/CSS/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**/*",
            "/console/**",
            "/forgotmypassword"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains("dev")) {
            System.out.println("Stanley: Matched with Dev environment");
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/payload")
                .failureUrl("/login?error").permitAll()
                .and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        /*
        This session is for inmemory auth
         */
//        auth
//                .inMemoryAuthentication().passwordEncoder(new PasswordEncoderService())
//                .withUser("user").password("password")
//                .roles("USER")
//                .and()
//                .withUser("stanley").password("stanley")
//                .roles("MANAGER");



         /*
        This session is for  persistance-based security(UserDetailService) like storing in DB
         */
           auth
                   .userDetailsService(userSecurityService).passwordEncoder(bCryptPasswordEncoder());
    }
}

