import React, {Component} from 'react'

class MainTest extends Component {

    render() {
        const access = localStorage.getItem('token');
        return access == null ? <h1> 로그인 안된 상태 </h1> : <h1>로그인 된 상태</h1>
    }
}

export default MainTest