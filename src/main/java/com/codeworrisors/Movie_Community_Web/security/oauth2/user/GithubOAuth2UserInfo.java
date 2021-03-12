package com.codeworrisors.Movie_Community_Web.security.oauth2.user;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo{

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getId() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("avatar_url");
    }

    

}