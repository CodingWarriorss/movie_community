import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import { } from 'react-bootstrap';

import Login from "./components/body/login/login.component";
import SignUp from "./components/body/login/signup.component";
import Main from './components/body/main/main';
import MovieNavi from './components/nav/nav';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link className="navbar-brand" to={""}>Movie Community</Link>
            <MovieNavi></MovieNavi>
          </div>
        </nav>
        <div className="outer">
            <Switch>
             <Route exact path='/' component={Main} />
              <div className='inner'>
              <Route path="/api/members/login" component={Login} />
              <Route path="/api/members/join" component={SignUp} />
              </div> 
            </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;