import React, { Component } from 'react'
import LoginApp from './component/jwtlogin/LoginApp'
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