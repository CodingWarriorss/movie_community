package com.codeworrisors.Movie_Community_Web.security.oauth2;

import com.codeworrisors.Movie_Community_Web.exception.OAuth2AuthenticationProcessingException;
import com.codeworrisors.Movie_Community_Web.model.AuthProvider;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import com.codeworrisors.Movie_Community_Web.security.oauth2.user.OAuth2UserInfo;
import com.codeworrisors.Movie_Community_Web.security.oauth2.user.OAuth2UserInfoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;


/*
    OAuth2 공급자로부터 액세스 토큰을 얻은 후에 호출되는 서비스 클래스
    여기서는 OAuth2 제공 업체에서 사용자의 세부 정보를 가져오고 동일한 이메일을 사용한는 사용자가
    있으면 업데이트 아니면 바로 등록해버림.
 */
@Service
public class MovieCommunityOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Member> memberOptional = memberRepository.findByMemberName(oAuth2UserInfo.getEmail());
        Member member;
        if(memberOptional.isPresent()) {
            member = memberOptional.get();
            if(!member.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        member.getProvider() + " account. Please use your " + member.getProvider() +
                        " account to login.");
            }
            member = updateExistingUser(member, oAuth2UserInfo);
        } else {
            member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        logger.info( member.toString() );


        return PrincipalDetails.create(member);

    }

    private Member registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Member member = new Member();

        member.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        member.setProviderId(oAuth2UserInfo.getId());
        member.setName(oAuth2UserInfo.getName());
        member.setEmail(oAuth2UserInfo.getEmail());
        member.setMemberName(oAuth2UserInfo.getEmail());
        member.setProfileImg(oAuth2UserInfo.getImageUrl());
        return memberRepository.save(member);
    }

    private Member updateExistingUser(Member existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setProfileImg(oAuth2UserInfo.getImageUrl());
        return memberRepository.save(existingUser);
    }

}
