package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.HelloDTO;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jvnet.hk2.annotations.Service;

@Service
@Named("aws")
public class AwsHelloService implements HelloService {
    @Inject
    @ConfigProperty(name = "aws", defaultValue = "(AWS property not present)")
    private String aws;

    @Logged
    @Override
    public HelloDTO getHello() {
        return new HelloDTO("Hello from " + aws);
    }
}
