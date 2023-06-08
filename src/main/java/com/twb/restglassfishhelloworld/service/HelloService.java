package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.aop.logging.Logged;
import com.twb.restglassfishhelloworld.dto.HelloDTO;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface HelloService {

    HelloDTO getHello();
}
