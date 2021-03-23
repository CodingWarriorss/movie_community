import React, {Component} from 'react'
import AuthenticationService from "./AuthenticationService.js";

class LogoutComponent extends Component {

    render() {
        return <button className="btn btn-primary" style={{borderColor: "black"}}
                       onClick={AuthenticationService.logout}>로그아웃</button>
    }
}

export default LogoutComponent