package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.HelloDTO;
import jakarta.inject.Named;
import org.jvnet.hk2.annotations.Service;

@Service
@Named("aws")
public class AwsHelloService implements HelloService {
    @Logged
    @Override
    public HelloDTO getHello() {
        return new HelloDTO("Hello from AWS");
    }
}
