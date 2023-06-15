package com.twb.bookapp.aop.logging;

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
        String className = context.getTarget().getClass().getName();
        String methodName = context.getMethod().getName();
        Object[] parameters = context.getParameters();
        String parameterString = Arrays.toString(parameters)
                .replace("[", "").replace("]", "");
        Logger logger = Logger.getLogger(className.split("\\$")[0]);

        logger.info(() -> String.format("Entering %s(%s)", methodName, parameterString));
        Object result = context.proceed();
        logger.info(() -> String.format("Exiting %s(%s) - %s", methodName, parameterString, result));
        return result;
    }
}
