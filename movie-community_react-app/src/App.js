import React, {Component} from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";
import LoginComponent from "./component/login/LoginComponent";
import LogoutComponent from "./component/login/LogoutComponent";
import SignupComponent from "./component/signup/SignupComponent";
import MenuForAuthenticated from "./component/menu/MenuForAuthenticated";
import MenuForUnauthenticated from "./component/menu/MenuForUnauthenticated";
import MainTest from "./component/MainTest";

class App extends Component {

    render() {
        // 로그인 세션
        const access = localStorage.getItem('token');
        (access === null) ? console.log('[로그인 안됨]') : console.log('[로그인 상태]' + access);

        return (
            <Router>
                <div className="App">
                    { /*네비게이션 바*/}
                    <nav className="navbar-expand-lg navbar-light fixed-top">
                        <div className="container">
                            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                                <Link className="navbar-brand" to={"/"}><h2>Movie Community</h2></Link>
                                {/*로그인 상태별 네비게이션 메뉴 분리*/}
                                {access === null ?
                                    <MenuForUnauthenticated/> : <MenuForAuthenticated/>}
                            </div>
                        </div>
                    </nav>

                            <Switch>
                                <Route path="/logout" component={LogoutComponent}/>
                                <Route path="/login" component={LoginComponent}/>
                                <Route path="/signup" component={SignupComponent}/>
                                <Route path="/" component={MainTest}/>
                                {/*    새로고침해야지 렌더링 되는 문제.. 아마 자식 부모 관계 설정때문에 그런거 같음*/}
                            </Switch>
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
