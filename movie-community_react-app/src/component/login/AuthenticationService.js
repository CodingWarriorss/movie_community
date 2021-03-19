import axios from 'axios'

class AuthenticationService {

    // 1. 로그인 요청 : memberName, password를 서버에 전송
    executeJwtAuthenticationService(memberName, password) {
        return axios.post('http://localhost:8080/login', {
            memberName,
            password
        });
    }

    // 2. 로그인 후처리
    registerSuccessfulLoginForJwt(memberName, token) {
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
        this.setupAxiosInterceptors();
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
        console.log('[로그아웃 완료]');
        window.location.replace('/');
    }
}

export default new AuthenticationService();