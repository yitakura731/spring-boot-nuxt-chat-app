package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.data.BaseUser;

@Component
public class InitialDataSetup {

    private final UserRepository userRepository;

    @Autowired
    InitialDataSetup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void setupInitialData(ContextRefreshedEvent event) throws Exception {
        this.setupDate();
    }

    public void setupDate() throws Exception {
        BaseUser user11 = userRepository.findByUserId("user11");
        if (user11 == null) {
            user11 = userRepository.save(BaseUser.builder().
                    userId("user11").
                    passwd("user11").
                    name("鈴木 一郎").
                    email("user11@dummy.com").
                    auth("ROLE_USER").
                    build());
        }
        BaseUser user12 = userRepository.findByUserId("user12");
        if (user12 == null) {
            user12 = userRepository.save(BaseUser.builder().
                    userId("user12").
                    passwd("user12").
                    name("山田 一郎").
                    email("user12@dummy.com").
                    auth("ROLE_USER").
                    build());
        }
        BaseUser user13 = userRepository.findByUserId("user13");
        if (user13 == null) {
            user13 = userRepository.save(BaseUser.builder().
                    userId("user13").
                    passwd("user13").
                    name("佐藤 一郎").
                    email("user13@dummy.com").
                    auth("ROLE_USER").
                    build());
        }
        BaseUser user14 = userRepository.findByUserId("user14");
        if (user14 == null) {
            user14 = userRepository.save(BaseUser.builder().
                    userId("user14").
                    passwd("user14").
                    name("高橋 一郎").
                    email("user14@dummy.com").
                    auth("ROLE_USER").
                    build());
        }
        BaseUser user15 = userRepository.findByUserId("user15");
        if (user15 == null) {
            user15 = userRepository.save(BaseUser.builder().
                    userId("user15").
                    passwd("user15").
                    name("野村 一郎").
                    email("user15@dummy.com").
                    auth("ROLE_USER").
                    build());
        }
    }
}
