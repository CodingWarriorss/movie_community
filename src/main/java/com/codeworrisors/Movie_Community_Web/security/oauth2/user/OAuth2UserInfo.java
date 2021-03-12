package com.codeworrisors.Movie_Community_Web.security.oauth2.user;

import java.util.Map;

/**
 * 각 플랫폼?별 정보를 가져올떄 attribute 별 Naming이 조금씩 다르기
 * 때문에 각 이런 패턴으로 만들어서 다른 부분은 각 플랫폼별 사용자 모델을 만들어서
 * 사용하는 패턴으로 보입니다. 굿?
 */
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
