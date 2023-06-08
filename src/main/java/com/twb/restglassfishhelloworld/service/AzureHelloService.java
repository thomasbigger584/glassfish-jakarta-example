package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.dto.HelloDTO;
import jakarta.inject.Named;
import org.jvnet.hk2.annotations.Service;

@Service
@Named("azure")
public class AzureHelloService implements HelloService {
    @Override
    public HelloDTO getHello() {
        return new HelloDTO("Hello from Azure");
    }
}
