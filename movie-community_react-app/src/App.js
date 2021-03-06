import React, {Component,} from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";
import LoginComponent from "./component/login/LoginComponent";
import LogoutComponent from "./component/login/LogoutComponent";
import SignupComponent from "./component/signup/SignupComponent";
import MenuForAuthenticated from "./component/menu/MenuForAuthenticated";
import MenuForUnauthenticated from "./component/menu/MenuForUnauthenticated";
import Main from "./component/Main";
import MyProfileComponent from "./component/mypage/MyProfileComponent";
import OthersProfileComponent from "./component/mypage/OthersProfileComponent";

import OAuth2RedirectHandler from "./component/oauth2/OAuth2RedirectHandler"

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            movieTitle: ""
        }

        this.refreshReview = React.createRef();

        this.setMovieTitle = this.setMovieTitle.bind(this);
    }

    setMovieTitle = (movieTitle) => {
        this.setState({
            movieTitle: movieTitle
        });
    }

    render() {
        // 로그인 세션
        const access = localStorage.getItem('token');
        (access === null) ? console.log('[로그인 안됨]') : console.log('[로그인 상태]' + access);

        return (
            <Router>

                <div className="App">
                    { /*네비게이션 바*/}
                    
                    <nav className="navbar-expand-lg navbar-light fixed-top"
                         style={{height: "70px"}}>
                        <div className="container fixed-top">
                            <div className="collapse navbar-collapse" style={{marginTop: "10px"}}
                                 id="navbarTogglerDemo02">
                                <Link className="navbar-brand" to={"/review"}><h2 className="title-fix">Movie Community</h2></Link>

                                <Switch>
                                    <Route path="/review" render={ () => <MenuForAuthenticated getMovieTitle={this.setMovieTitle}/>}></Route>
                                    <Route path="/mypage" render={ () => <MenuForAuthenticated getMovieTitle={this.setMovieTitle}/>}></Route>
                                    <Route path="/otherspage" render={ () => <MenuForAuthenticated getMovieTitle={this.setMovieTitle}/>}></Route>
                                    <Route exact path="/*" component={MenuForUnauthenticated}></Route>
                                </Switch>
                            </div>
                        </div>
                    </nav>

                    <Switch>
                        <Route path="/otherspage" component={OthersProfileComponent}/>
                        <Route path="/mypage" component={MyProfileComponent}/>
                        <Route path="/logout" component={LogoutComponent}/>
                        <Route path="/login" component={LoginComponent}/>
                        <Route path="/signup" component={SignupComponent}/>
                        <Route path="/oauth2/redirect" component={OAuth2RedirectHandler} ></Route>
                        <Route path="/review" render={() => <Main movieTitle={this.state.movieTitle}/>}/>
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
