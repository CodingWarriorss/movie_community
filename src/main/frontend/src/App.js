import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import Login from "./components/login.component";
import SignUp from "./components/signup.component";

function App() {
  return (<Router>
    <div className="App">
      <nav className="navbar navbar-expand-lg navbar-light fixed-top">
        <div className="container">
          <Link className="navbar-brand" to={"api/members/login"}>Movie Community</Link>
          <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link className="nav-link" to={"/api/members/login"}>로그인</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to={"/api/members/join"}>회원 가입</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div className="outer">
        <div className="inner">
          <Switch>
            <Route exact path='/' component={Login} />
            <Route path="/api/members/login" component={Login} />
            <Route path="/api/members/join" component={SignUp} />
          </Switch>
        </div>
      </div>
    </div></Router>
  );
}

export default App;