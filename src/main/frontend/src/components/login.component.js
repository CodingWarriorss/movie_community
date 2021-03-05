  
import React, { Component } from "react";

export default class Login extends Component {
    render() {
        return (
            <form>
                <h3>로그인</h3>
                <div className="form-group">
                    <label>아이디</label>
                    <input type="email" className="form-control" placeholder="아이디" />
                </div>

                <div className="form-group">
                    <label>비밀번호</label>
                    <input type="password" className="form-control" placeholder="비밀번호" />
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