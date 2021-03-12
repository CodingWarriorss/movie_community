package com.codeworrisors.Movie_Community_Web.security.oauth2.user;

import java.util.Map;

import com.codeworrisors.Movie_Community_Web.model.AuthProvider;

public class OAuth2UserInfoFactory {
    
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes){

        if( registrationId.equalsIgnoreCase( AuthProvider.github.toString() )){
            return new GithubOAuth2UserInfo(attributes);
        }else if( registrationId.equalsIgnoreCase( AuthProvider.google.toString() )){
            return new GoogleOAuth2UserInfo(attributes);
        }else{
            //바꿔야한다
            throw new RuntimeException();
        }
    }
}
