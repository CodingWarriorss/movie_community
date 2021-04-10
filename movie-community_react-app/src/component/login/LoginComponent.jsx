import React, {Component} from 'react'
import AuthenticationService from "./AuthenticationService.js";

import {GOOGLE_AUTH_URL ,GITHUB_AUTH_URL } from "component/constants/SocialLogin";

import googleLogo from 'img/google-logo.png';
import githubLogo from 'img/github-logo.png';
/*
https://ko.reactjs.org/docs/react-component.html
<마운트>
컴포넌트의 인스턴스가 생성되어 DOM 상에 삽입될 때에 순서대로 호출된다.
constructor()
static getDerivedStateFromProps()
render()
componentDidMount()

<업데이트>
props 또는 state가 변경되면 갱신이 발생합니다. 아래 메서드들은 컴포넌트가 다시 렌더링될 때 순서대로 호출된다.
static getDerivedStateFromProps()
shouldComponentUpdate()
render()
getSnapshotBeforeUpdate()
componentDidUpdate()
*/

// [로그인 컴포넌트]
class LoginComponent extends Component {

    constructor(props) {// props(컴포넌트) : React 엘리먼트
        super(props); // 부모생성자(React.Component)를 호출. 선언전까지 생성자내에서 this 키워드 사용 불가

        // 기본 변수 세팅
        this.state = {
            memberName: localStorage.getItem("authenticatedMember") || '',
            password: '',
            token: localStorage.getItem("token") || '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }

        // 콜백 함수 바인딩
        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);
    }

    // 1. input value 변경시 실행되는 콜백함수
    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        )
    }

    // 2. 로그인 함수
    loginClicked() {
        AuthenticationService
            .executeJwtAuthenticationService(this.state.memberName, this.state.password)
            .then((response) => {
                // 토큰저장
                const res = AuthenticationService.registerSuccessfulLoginForJwt(this.state.memberName, response);
                if (res.startsWith('success')) { // 로그인 성공
                    AuthenticationService.setMemberInfo();
                } else if (res.startsWith('failure')) { // 로그인 실패 (아이디 오류, 비밀번호 오류)
                    const msg = res.split('/')[1] === 'memberName' ?
                        '존재하지 않는 아이디입니다.' : '비밀번호가 틀렸습니다.';
                    alert(msg);
                    return;
                }
            }).catch(() => {
            this.setState({showSuccessMessage: false});
            this.setState({hasLoginFailed: true});
        });
    }

    /*
    https://medium.com/vingle-tech-blog/react-%EB%A0%8C%EB%8D%94%EB%A7%81-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-f255d6569849
    <렌더링>
    1. Props가 변경되었을 때
    2. State가 변경되었을 때
    3. forceUpdate()를 실행하였을 때
    4. 부모 컴포넌트가 렌더링 되었을 때
    */
    render() {
        return (
            <div className="outer">
                <div className="inner">
                    <div>
                        <h3>로그인</h3>

                        <div className="form-group">
                            <label>아이디</label>
                            <input type="text" className="form-control" placeholder="아이디"
                                   name="memberName" value={this.state.memberName}
                                   onChange={this.handleChange}/>
                        </div>

                        <div className="form-group">
                            <label>비밀번호</label>
                            <input type="password" className="form-control" placeholder="비밀번호"
                                   name="password" value={this.state.password}
                                   onChange={this.handleChange}/>
                        </div>

                        <div className="form-group">
                            <div className="custom-control custom-checkbox">
                                <input type="checkbox" className="custom-control-input" id="customCheck1"/>
                                <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                            </div>
                        </div>

                        <button className="btn btn-dark btn-lg btn-block" onClick={this.loginClicked}>로그인</button>
                        {/*<p className="forgot-password text-right">Forgot*/}
                        {/*    <a href="#"> password?</a>*/}
                        {/*</p>*/}
                    </div>

                    <div className="social-login">
                <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
                    <img src={googleLogo} alt="Google" /> Log in with Google</a>
                <a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
                    <img src={githubLogo} alt="Github" /> Log in with Github</a>
            </div>

                </div>
            </div>
        )
    }
}

export default LoginComponent
/*
<export default>
변수, 함수, 오브젝트, 클래스 등을 보낼 수 있는 명령어.
default는 기본이라는 의미를 담고 있으며,
default로 export한 Loginapp은 중괄호를 사용하지 않고도 import할 수 있다.
https://m.blog.naver.com/gi_balja/221227430979
*/