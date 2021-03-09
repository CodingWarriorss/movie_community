import axios from "axios";
import React, { Component } from "react";

export default class SignUp extends Component {

    constructor(props) {
        super(props);

        this.state = {
            memberId : '',
            memberPassword : '',
            memberName : '',
            memberEmail : '',
            memberAddress : '',
            memberAddressDetail: '',
            memberGender : '',
            memberBirthYear: '',
            memberBirthMonth: '',
            memberBirthDay: '',
            memberPhone1 : '',
            memberPhone2 : '',
            memberPhone3 : ''
         }

         // 바인딩
         this.checkID = this.checkID.bind(this)
         this.checkPWD = this.checkPWD.bind(this)
    }

    // 모든 input 태그의 state 변화감지
    changeHandler = (e) => {
        this.setState({[e.target.name] : e.target.value});
    }

    // 아이디 중복체크
    checkID(e) {
        e.preventDefault(); // 

        const data = {
            id: this.state.memberId
        }

        fetch('http://localhost:8080/api/members/checkid', {
            method: "post",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data), //json 형식으로 변환
        })
            .then(res => res.json())
            .then(function (json) {
                if (JSON.stringify(json) === '1') {
                    alert('사용 가능한 id입니다.');
                } else {
                    alert('이미 사용중인 id입니다.');
                }
            });
    }

    // 비밀번호 일치여부 체크
    checkPWD = (e) => {
        const {memberPassword, memberPassword2} = this.state;

        // 비밀번호 입력 안했거나 둘 중 하나의 값이 입력 상태가 아닐 때
        if (memberPassword.length < 1 || memberPassword2.length < 1) {
            this.setState({
                checkPassword: '비밀번호를 입력하세요.',
            });
        }
        // 비밀번호 같다면 일치
        else if (memberPassword === memberPassword2) {
            this.setState({
                checkPassword: '비밀번호가 일치합니다.',
            });
        } else {
            this.setState({
                checkPassword: '비밀번호가 일치하지 않습니다.',
            });
        }
    }


    submitHandler = (e) => {
        e.preventDefault();

        const {
            memberId, memberPassword, memberName, memberEmail, memberAddress
            , memberGender, memberBirthYear, memberBirthMonth, memberBirthDay,
            memberPhone1, memberPhone2, memberPhone3
        } = this.state;

        // 채우지 않은 항목이 있는 경우
        if (memberId.length < 1 || memberPassword.length < 1 || memberName.length < 1
            || memberEmail.length < 1 || memberAddress.length < 1 || memberGender.length < 1
            || memberBirthYear.length < 1 || memberBirthMonth.length < 1 || memberBirthDay.length < 1
            || memberPhone1.length < 1 || memberPhone2.length < 1 || memberPhone3.length < 1) {
            alert('기입하지 않은 항목이 있습니다.');
        }
        // 모든 항목이 채워져 있는 경우
        else {
            console.log({
                memberId: this.state.memberId,
                memberPassword: this.state.memberPassword,
                memberName: this.state.memberName,
                memberEmail: this.state.memberEmail,
                memberAddress: this.state.memberAddress + " " + this.state.memberAddressDetail,
                memberGender: this.state.memberGender,
                memberBirth: this.state.memberBirthYear + "/" + this.state.memberBirthMonth + "/" + this.state.memberBirthDay,
                memberPhone: this.state.memberPhone1 + "-" + this.state.memberPhone2 + "-" + this.state.memberPhone3
            });

            axios.post(
                'http://localhost:8080/api/members/join',
                {
                    memberId: this.state.memberId,
                    memberPassword: this.state.memberPassword,
                    memberName: this.state.memberName,
                    memberEmail: this.state.memberEmail,
                    memberAddress: this.state.memberAddress + " " + this.state.memberAddressDetail,
                    memberGender: this.state.memberGender,
                    memberBirth: this.state.memberBirthYear + "/" + this.state.memberBirthMonth + "/" + this.state.memberBirthDay,
                    memberPhone: this.state.memberPhone1 + "-" + this.state.memberPhone2 + "-" + this.state.memberPhone3
                }
            )
            alert("회원 가입이 완료돠었습니다.");
            window.history.back();
        }
    }

    render() {
        const {
            memberId, memberPassword, memberPassword2, checkPassword,
            memberName, memberEmail,
            memberAddress, memberAddressDetail, memberGender, memberBirthYear, memberBirthMonth,
            memberBirthDay, memberPhone1, memberPhone2, memberPhone3
        } = this.state;

        return (
            <form onSubmit={this.submitHandler}>
                <h3>회원가입</h3>

                <div className="form-group">
                    <label>아이디</label>
                    <input
                        type="text" className="form-control" placeholder="아이디" name="memberId"
                        placeholder="아이디" value={memberId} onChange={this.changeHandler}
                    />
                    <button className="btn btn-dark btn-lg btn-block" onClick={this.checkID}>중복확인</button>
                </div>

                <div className="form-group">
                    <label>비밀번호</label>
                    <input
                        type="password" className="form-control" placeholder="비밀번호" name="memberPassword"
                        value={memberPassword} onChange={this.changeHandler} onKeyUp={this.checkPWD}
                    />
                </div>
                <div className="form-group">
                    <label>비밀번호 확인</label>
                    <input
                        type="password" className="form-control" placeholder="비밀번호 확인" name="memberPassword2"
                        value={memberPassword2} onChange={this.changeHandler} onKeyUp={this.checkPWD}
                    />
                </div>

                <div className="form-group">
                    <div style={{color: 'red'}}>{checkPassword}</div>
                </div>

                <div className="form-group">
                    <label>이름</label>
                    <input
                        type="text" className="form-control" placeholder="이름" name="memberName"
                        value={memberName} onChange={this.changeHandler}
                    />
                </div>
                <div className="form-group">
                    <label>이메일</label>
                    <input
                        type="email" className="form-control" placeholder="이메일" name="memberEmail"
                        value={memberEmail} onChange={this.changeHandler}
                    />
                </div>
                <div className="form-group">
                    <div class="custom-control custom-radio custom-control-inline">
                        <input
                            type="radio" class="custom-control-input" id="customRadio"
                            name="memberGender" value="man" checked={memberGender === 'man'}
                            onChange={this.changeHandler}
                        />
                        <label class="custom-control-label" for="customRadio">남</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input
                            type="radio" class="custom-control-input" id="customRadio2"
                            name="memberGender" value="woman" checked={memberGender === 'woman'}
                            onChange={this.changeHandler}
                        />
                        <label class="custom-control-label" for="customRadio2">여</label>
                    </div>
                </div>
                <div className="form-group">
                    <label>주소</label>
                    <input
                        type="address" className="form-control" placeholder="주소" name="memberAddress"
                        value={memberAddress} onChange={this.changeHandler}
                    />
                    <input
                        type="address" className="form-control" placeholder="상세 주소" name="memberAddressDetail"
                        value={memberAddressDetail} onChange={this.changeHandler}
                    />
                </div>
                <div className="form-group">
                    <label>생년월일</label>
                    <div className="input-group">
                        <input
                            type="number" className="form-control" name="memberBirthYear"
                            value={memberBirthYear} onChange={this.changeHandler}
                        />
                        <span class="input-group-addon">년</span>
                        <input
                            type="tel" className="form-control" name="memberBirthMonth"
                            value={memberBirthMonth} onChange={this.changeHandler}
                        />
                        <span class="input-group-addon">월</span>
                        <input
                            type="tel" className="form-control" name="memberBirthDay"
                            value={memberBirthDay} onChange={this.changeHandler}
                        />
                        <span class="input-group-addon">일</span>
                    </div>
                </div>
                <div className="form-group">
                    <label>휴대전화 번호</label>
                    <div className="input-group">
                        <input
                            type="tel" id="ex1" className="form-control" name="memberPhone1"
                            value={memberPhone1} onChange={this.changeHandler}
                        />
                        <span class="input-group-addon">-</span>
                        <input
                            type="tel" className="form-control" name="memberPhone2"
                            value={memberPhone2} onChange={this.changeHandler}
                        />
                        <span class="input-group-addon">-</span>
                        <input
                            type="tel" className="form-control" name="memberPhone3"
                            value={memberPhone3} onChange={this.changeHandler}
                        />
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