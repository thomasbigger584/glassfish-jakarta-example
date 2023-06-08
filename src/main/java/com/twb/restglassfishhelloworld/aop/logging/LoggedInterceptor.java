package com.twb.restglassfishhelloworld.aop.logging;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.Arrays;
import java.util.logging.Logger;

@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor {

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        String className = context.getTarget().getClass().getSimpleName();
        String methodName = context.getMethod().getName();
        Object[] parameters = context.getParameters();

        Logger logger = Logger.getLogger(className);

        logger.info(() -> String.format("Entering %s(%s)", methodName, Arrays.toString(parameters)));
        Object result = context.proceed();
        logger.info(() -> String.format("Exiting %s(%s)", methodName, Arrays.toString(parameters)));
        return result;
    }
}
