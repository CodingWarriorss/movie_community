import React, { Component } from "react";

export default class SignUp extends Component {
    render() {
        return (
            <form>
                <h3>회원가입</h3>

                <div className="form-group">
                    <label>아이디</label>
                    <input type="text" className="form-control" placeholder="아이디" />
                </div>

                <div className="form-group">
                    <label>비밀번호</label>
                    <input type="password" className="form-control" placeholder="비밀번호" />
                </div>

                <div className="form-group">
                    <label>이름</label>
                    <input type="text" className="form-control" placeholder="이름" />
                </div>
                <div className="form-group">
                    <label>이메일</label>
                    <input type="email" className="form-control" placeholder="이메일" />
                </div>
                <div className="form-group">
                    <div class="custom-control custom-radio custom-control-inline">
                        <input type="radio" class="custom-control-input" id="customRadio" name="man" value="man"></input>
                        <label class="custom-control-label" for="customRadio">남</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input type="radio" class="custom-control-input" id="customRadio2" name="woman" value="woman"></input>
                        <label class="custom-control-label" for="customRadio2">여</label>
                    </div>
                </div>
                <div className="form-group">
                    <label>주소</label>
                    <input type="address" className="form-control" placeholder="주소" />
                    <input type="address" className="form-control" placeholder="상세 주소" />
                </div>
                <div className="form-group">
                    <label>생년월일</label>
                    <div className="input-group">
                        <input type="number" className="form-control"/>
                        <span class="input-group-addon">년</span>
                        <input type="tel" className="form-control"/>
                        <span class="input-group-addon">월</span>
                        <input type="tel" className="form-control"/>
                        <span class="input-group-addon">일</span>
                    </div>
                </div>
                <div className="form-group">
                    <label>휴대전화 번호</label>
                    <div className="input-group">
                        <input type="tel" id="ex1" className="form-control" />
                        <span class="input-group-addon">-</span>
                        <input type="tel" className="form-control" />
                        <span class="input-group-addon">-</span>
                        <input type="tel" className="form-control" />
                    </div>
                </div>
                <button type="submit" className="btn btn-dark btn-lg btn-block">회원 가입</button>
                <p className="forgot-password text-right">
                    Already registered <a href="/">log in?</a>
                </p>
            </form>
        );
    }
}