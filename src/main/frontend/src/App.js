import React, { Component } from 'react'
import LoginApp from './component/jwtlogin/LoginApp'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css';

class App extends Component{
  render() {
    return(
        <div className="App">
          <LoginApp/>
        </div>
    );
  }
}

export default App;