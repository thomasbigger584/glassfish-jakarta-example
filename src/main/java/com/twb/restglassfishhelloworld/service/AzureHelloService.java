package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.HelloDTO;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jvnet.hk2.annotations.Service;

@Service
@Named("azure")
public class AzureHelloService implements HelloService {

    @Inject
    @ConfigProperty(name="azure", defaultValue = "(Azure property not present)")
    private String azure;

    @Logged
    @Override
    public HelloDTO getHello() {
        return new HelloDTO("Hello from " + azure);
    }
}
