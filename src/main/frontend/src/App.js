import React, { Component } from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import LoginComponent from "./component/jwtlogin/LoginComponent";
import SignupComponent from "./component/SignupComponent";
import ReviewWriteBox from './component/review/ReviewWriteBox';

/*
Link 컴포넌트 : 화면전환 컴포넌트. SPA에서 다른 라우트로 이동할 때, <a/>대신 사용한다.
*/

class App extends Component {

    render() {
        var access = localStorage.getItem("token");
        return (
            <Router>
                <div className="App">
                    { /*네비게이션 바*/}
                    <nav className="navbar-expand-lg navbar-light fixed-top">
                        <div className="container">
                            <Link className="navbar-brand">Movie Community</Link>
                            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                                <ul className="navbar-nav ml-auto">
                                    <li className="nav-item">
                                        <Link className="nav-link" to={"/login"}>로그인</Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link" to={"/signup"}>회원가입</Link>
                                    </li>
                                    <li>
                                        <ReviewWriteBox></ReviewWriteBox>
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </nav>

                    <div className="outer">
                        <div className="inner">
                            <Switch>
                                <Route path="/login" component={LoginComponent} />
                                <Route path="/signup" component={SignupComponent} />
                            </Switch>
                        </div>
                    </div>

                    {/**/}
                    {/*<LoginApp/>*/}
                </div>
            </Router>
        );
    }
}

export default App;
/*
<export default>
변수, 함수, 오브젝트, 클래스 등을 보낼 수 있는 명령어.
default는 기본이라는 의미를 담고 있으며,
default로 export한 Loginapp은 중괄호를 사용하지 않고도 import할 수 있다.
https://m.blog.naver.com/gi_balja/221227430979
*/