import axios from 'axios'
import {DEFAULT_AVATAR_URL, IMAGE_RESOURCE_URL, REST_API_SERVER_URL} from '../constants/APIConstants';

class AuthenticationService {

    // 1. 로그인 요청 : memberName, password를 서버에 전송
    executeJwtAuthenticationService(memberName, password) {
        return axios.post(REST_API_SERVER_URL + '/auth/login', {
            memberName,
            password
        });
    }

    // 2. 로그인 후처리
    // registerSuccessfulLoginForJwt(memberName, token) {
    registerSuccessfulLoginForJwt(memberName, response) {
        const token = response.headers['authorization'];

        // 스토리지에 로그인된 유저의 id(memberName)과 token 저장
        if (token.startsWith('Bearer')) {
            console.log('[로그인 성공]'); // 토큰 확인 완료
            localStorage.setItem('token', token);
            localStorage.setItem('authenticatedMember', memberName);

            return 'success';
        } else if (token.startsWith('failure')) {
            console.log('[로그인 실패]'); // 토큰 확인 완료
            return token;
        } else {
            console.log('[로그인 오류 발생]')
        }
    }

    setMemberInfo() {
        const requestURL = REST_API_SERVER_URL + '/api/member';
        const config = {
            headers: {
                'Authorization': 'Bearer ' +localStorage.getItem('token')
            }
        }

        axios.get(requestURL, config)
            .then((response) => {
                const member = response.data.member;

                console.log( JSON.stringify( member , null ,4));
                localStorage.setItem('name', member.name ? member.name : '');
                localStorage.setItem('email', member.email ? member.email : '');
                localStorage.setItem('website', member.website ? member.website : '');
                localStorage.setItem('bio', member.bio ? member.bio : '');

                let imgUrl = member.profileImg ? IMAGE_RESOURCE_URL + member.profileImg : DEFAULT_AVATAR_URL;
                if( member.provider === "local" ) imgUrl = member.profileImg;
                localStorage.setItem('profileImg', member.profileImg ? IMAGE_RESOURCE_URL + member.profileImg : DEFAULT_AVATAR_URL);
                window.location.replace('/review');
            });
    }

    // 3. 요청이나 응답전 axios
    setupAxiosInterceptors() {
        // axios.interceptors : axios에 포함된 기능으로, request/response 전에 무언가를 수행하거나 오류발생시 수행할 것을 정의한다.
        axios.interceptors.request.use(
            // 서버로 request하기 전 config 세팅
            config => {
                const token = localStorage.getItem('token');
                if (token) {
                    config.headers['Authorization'] = 'Bearer ' + token;
                }
                config.headers['Content-Type'] = 'application/json';
                return config;
            },
            // request error 발생
            error => {
                Promise.reject(error);
            });
    }

    // 4. 로그아웃 : 스토리지에서 현재 유저 정보를 삭제
    logout() {
        localStorage.removeItem("authenticatedMember");
        localStorage.removeItem("token");
        localStorage.removeItem("name");
        localStorage.removeItem("email");
        localStorage.removeItem("birth");
        localStorage.removeItem("website");
        localStorage.removeItem("bio");
        localStorage.removeItem("profileImg");
        window.location.replace('/');
    }
}

export default new AuthenticationService();