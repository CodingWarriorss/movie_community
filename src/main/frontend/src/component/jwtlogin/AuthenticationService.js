import axios from 'axios'

class AuthenticationService{

    // 1. 로그인 요청 : memberName, password를 서버에 전송
    executeJwtAuthenticationService(memberName, password){
        return axios.post('http://localhost:8080/login',{
            memberName,
            password
        });
    }

    // 2. 테스트
    executeHomeService(){
        console.log("========executeHomeService===========");
        return axios.get('http://localhost:8080/home');
    }

    // 3. 로그인 성공 후처리
    registerSuccessfulLoginForJwt(memberName, token){
        console.log("====registerSuccessfulLoginJwt====");
        // 스토리지에 로그인된 유저의 id(memberName)과 token 저장
        localStorage.setItem('token', token);
        localStorage.setItem('authenticatedMember', memberName);
        // sessionStorage.setItem('authenticatedUser', username)
        //this.setupAxiosInterceptors(this.createJWTToken(token))
        this.setupAxiosInterceptors();
    }

    // 토큰 생성 테스트
    createJWTToken(token){
        return 'Bearer ' + token;
    }

    // 5. 요청이나 응답전 axios
    setupAxiosInterceptors(){
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

    // 6. 로그아웃 : 스토리지에서 현재 유저 정보를 삭제
    logout(){
        localStorage.removeItem("authenticatedMember");
        localStorage.removeItem("token");
    }

    // 7. 현재 사용자가 로그인되어 있는지 확인
    isMemberLoggedIn(){
        const token = localStorage.getItem('token');
        console.log("====MemberLoggedInCheck====");
        console.log("token:" + token);

        if (token){
            return true;
        }
        return false;
    }

    // 8. 로그인된 사용자의 id(memberName) 반환
    getLoggedInMemberName(){
        let member = localStorage.getItem("authenticatedMember");
        if (member === null) return '';
        return member;
    }
}

export default new AuthenticationService();