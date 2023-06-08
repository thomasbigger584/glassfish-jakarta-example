package com.twb.restglassfishhelloworld.aop.logging;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.text.MessageFormat;
import java.util.logging.Logger;

@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor {
    private static final Logger logger = Logger.getLogger(LoggedInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        System.out.println("LoggedInterceptor.logMethodInvocation");
        logger.info(MessageFormat.format("Entering method: {0}", context.getMethod().getName()));

        Object result = context.proceed();

        logger.info(MessageFormat.format("Exiting method: {0}", context.getMethod().getName()));
        return result;
    }
}
