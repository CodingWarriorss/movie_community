import React, {Component} from 'react'
import AuthenticationService from "./AuthenticationService.js";
/*
https://ko.reactjs.org/docs/react-component.html
<마운트>
컴포넌트의 인스턴스가 생성되어 DOM 상에 삽입될 때에 순서대로 호출된다.
constructor()
static getDerivedStateFromProps()
render()
componentDidMount()

<업데이트>
props 또는 state가 변경되면 갱신이 발생합니다. 아래 메서드들은 컴포넌트가 다시 렌더링될 때 순서대로 호출된다.
static getDerivedStateFromProps()
shouldComponentUpdate()
render()
getSnapshotBeforeUpdate()
componentDidUpdate()
*/

// [로그인 컴포넌트]
class LoginComponent extends Component{

    constructor(props){// props(컴포넌트) : React 엘리먼트
        super(props); // 부모생성자(React.Component)를 호출. 선언전까지 생성자내에서 this 키워드 사용 불가

        // 기본 변수 세팅
        this.state = {
            memberName: localStorage.getItem("authenticatedMember") || '',
            password: '',
            token: localStorage.getItem("token") || '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }

        // 콜백 함수 바인딩
        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);
    }
    
    // 1. input value 변경시 실행되는 콜백함수
    handleChange(event) {
        this.setState(
            {
                [event.target.name] : event.target.value
            }
        )
    }
    
    // 2. 로그인 함수
    loginClicked() {
        AuthenticationService
            .executeJwtAuthenticationService(this.state.memberName, this.state.password)
            .then((response) => {
                AuthenticationService.registerSuccessfulLoginForJwt(this.state.memberName, response.data.token);
                this.props.history.push(`/welcome/${this.state.memberName}`);
            }).catch(() =>{
            this.setState({showSuccessMessage:false});
            this.setState({hasLoginFailed:true});
        });
    }
    
    /*
    https://medium.com/vingle-tech-blog/react-%EB%A0%8C%EB%8D%94%EB%A7%81-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-f255d6569849
    <렌더링>
    1. Props가 변경되었을 때
    2. State가 변경되었을 때
    3. forceUpdate()를 실행하였을 때
    4. 부모 컴포넌트가 렌더링 되었을 때
    */
    render() {
        return(
            <div>
                <h1>Login</h1>
                <div className="container">
                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                    {this.state.showSuccessMessage && <div>Login Success</div>}
                    memberName : <input type="text" name="memberName" value={this.state.memberName} onChange={this.handleChange}/>
                    password: <input type="password" name="password" value={this.state.password} onChange={this.handleChange}/>
                    <button className="btn btn-success" onClick={this.loginClicked}>로그인</button>
                </div>
            </div>
        )
    }
}

export default LoginComponent