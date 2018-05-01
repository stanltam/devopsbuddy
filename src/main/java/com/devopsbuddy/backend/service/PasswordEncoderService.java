package com.devopsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService implements PasswordEncoder {

    /*** The application logger*/
    private static final Logger LOG = LoggerFactory.getLogger(PasswordEncoderService.class);

    @Override
    public String encode(CharSequence arg0) {
        LOG.info("PasswordEncoderService: Encode:{}",arg0.toString());
        return arg0.toString();
    }

    @Override
    public boolean matches(CharSequence arg0, String arg1) {
        LOG.info("PasswordEncoderService: matches:{}",arg0.toString());

        return arg1.equals(arg0.toString());
    }
}
