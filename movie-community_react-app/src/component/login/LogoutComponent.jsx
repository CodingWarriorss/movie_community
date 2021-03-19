import React, {Component} from 'react'
import AuthenticationService from "./AuthenticationService.js";

class LogoutComponent extends Component{

    render() {
        return <button onClick={AuthenticationService.logout}>로그아웃</button>
    }
}

export default LogoutComponent