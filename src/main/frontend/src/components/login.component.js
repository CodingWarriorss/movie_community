  
import axios from "axios";
import React, { Component } from "react";

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            memberId : '',
            memberPassword : ''
        }
    }

    changeHandler = (event) => {
        this.setState({ [event.target.name] : event.target.value});
    }

    submitHandler = (event) => {
        event.preventDefault();
        console.log(this.state);

        axios.post(
            'http://localhost:8080/api/members/login',
            this.state
        ).then(response => {
            if (response.data === 1) {
                alert("로그인 성공");
                
            } else if (response.data === 0) {
                alert("비밀 번호를 잘 못 입력하셨습니다.");
            } else {
                alert("존재하지 않는 사용자 입니다.");
            }
        })
    }

    render() {
        const {memberId, memberPassword} = this.state;
        return (
            <form onSubmit = {this.submitHandler}>
                <h3>로그인</h3>
                <div className="form-group">
                    <label>아이디</label>
                    <input
                        type="text" className="form-control" placeholder="아이디" name = "memberId"
                        value = {memberId} onChange = { this.changeHandler }
                    />
                </div>

                <div className="form-group">
                    <label>비밀번호</label>
                    <input 
                        type="password" className="form-control" placeholder="비밀번호" name = "memberPassword"
                        value = {memberPassword} onChange = { this.changeHandler }
                    />
                </div>

                <div className="form-group">
                    <div className="custom-control custom-checkbox">
                        <input type="checkbox" className="custom-control-input" id="customCheck1" />
                        <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                    </div>
                </div>

                <button type="submit" className="btn btn-dark btn-lg btn-block">Sign in</button>
                <p className="forgot-password text-right">
                    Forgot <a href="#">password?</a>
                </p>
            </form>
        );
    }
}