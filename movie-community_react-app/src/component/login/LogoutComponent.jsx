import React, {Component} from 'react'
import AuthenticationService from "./AuthenticationService.js";

class LogoutComponent extends Component {

    render() {
        return <button className="btn" style={{
            color : 'white',
            height : 45,
            backgroundColor : '#009688',
            borderRadius : 45,
            fontWeight : 'bold'
        }}
                       onClick={AuthenticationService.logout}>๋ก๊ทธ์์</button>
    }
}

export default LogoutComponent