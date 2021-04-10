package com.codeworrisors.Movie_Community_Web.config;

import com.codeworrisors.Movie_Community_Web.security.RestAuthenticationEntryPoint;
import com.codeworrisors.Movie_Community_Web.security.TokenAuthenticationFilter;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetailsService;

import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import com.codeworrisors.Movie_Community_Web.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.codeworrisors.Movie_Community_Web.security.oauth2.MovieCommunityOAuth2UserService;
import com.codeworrisors.Movie_Community_Web.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.codeworrisors.Movie_Community_Web.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberRepository memberRepository;
    private final CorsFilter corsFilter;

    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private MovieCommunityOAuth2UserService movieCommunityOAuth2UserService;
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(principalDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 쿠키와 세션 사용 안함
//                .and()
//                .addFilter(corsFilter) // 리소스공유 설정 필터 주입
//                .formLogin().disable() // 폼 로그인 방식 사용 안함
//                .httpBasic().disable() // http basic 방식 사용 안함
//
//                .authorizeRequests()// Bearer 방식 사용 : `Authorization : jwt(ID,PWD 암호화값)`
//                .antMatchers("/api/members/**")
//                .access("hasRole('ROLE_USER')")
//                .anyRequest()
//                .permitAll()
//                .and()
//                .oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorize")
//                .and()
//                .redirectionEndpoint()
//                .baseUri("/oauth2/callback/*")
//                .and()
//                .userInfoEndpoint()
//                .userService(movieCommunityOAuth2UserService)
//                .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler);

        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/auth/**", "/oauth2/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(movieCommunityOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
