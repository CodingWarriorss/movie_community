package com.codeworrisors.Movie_Community_Web.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * 커스텀? 어노테이션을 만들어서 사용하는거라고 합니다.....;;;
 * 또 찾아봐야해.... 잘 모르겠음...
 */
@Target({ElementType.PARAMETER , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentMember {
    
}
