package com.twb.bookapp.aop.logging;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Logged {
}