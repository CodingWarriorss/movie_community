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
    }

    changeHandler = (event) => {
        this.setState({[event.target.name] : event.target.value});
    }

    submitHandler = (event) => {
        event.preventDefault();
        console.log({
            memberId : this.state.memberId,
            memberPassword : this.state.memberPassword,
            memberName : this.state.memberName,
            memberEmail : this.state.memberEmail,
            memberAddress : this.state.memberAddress + " " +this.state.memberAddressDetail,
            memberGender : this.state.memberGender,
            memberBirth : this.state.memberBirthYear + "/" + this.state.memberBirthMonth + "/" + this.state.memberBirthDay,
            memberPhone : this.state.memberPhone1 + "-" + this.state.memberPhone2 + "-" + this.state.memberPhone3
        });
        
        axios.post(
            'localhost:8080/members/',
            {
                memberId : this.state.memberId,
                memberPassword : this.state.memberPassword,
                memberName : this.state.memberName,
                memberEmail : this.state.memberEmail,
                memberAddress : this.state.memberAddress + " " +this.state.memberAddressDetail,
                memberGender : this.state.memberGender,
                memberBirth : this.state.memberBirthYear + "/" + this.state.memberBirthMonth + "/" + this.state.memberBirthDay,
                memberPhone : this.state.memberPhone1 + "-" + this.state.memberPhone2 + "-" + this.state.memberPhone3
            }
        )
    }

    render() {
        const {memberId, memberPassword, memberName, memberEmail, 
            memberAddress, memberAddressDetail, memberGender, memberBirthYear, memberBirthMonth,
            memberBirthDay, memberPhone1, memberPhone2, memberPhone3} = this.state;
        
        return (
            <form  onSubmit = {this.submitHandler}>
                <h3>회원가입</h3>

                <div className="form-group">
                    <label>아이디</label>
                    <input 
                        type="text" className="form-control" placeholder="아이디"  name = "memberId"
                        placeholder="아이디" value = {memberId} onChange = {this.changeHandler}
                    />
                </div>

                <div className="form-group">
                    <label>비밀번호</label>
                    <input 
                        type="password" className="form-control" placeholder="비밀번호" name = "memberPassword"
                        value = {memberPassword} onChange = {this.changeHandler}
                    />
                </div>

                <div className="form-group">
                    <label>이름</label>
                    <input 
                        type="text" className="form-control" placeholder="이름"  name = "memberName"
                        value = {memberName} onChange = {this.changeHandler}
                    />
                </div>
                <div className="form-group">
                    <label>이메일</label>
                    <input
                        type="email" className="form-control" placeholder="이메일"  name = "memberEmail"
                        value = {memberEmail} onChange = {this.changeHandler}
                    />
                </div>
                <div className="form-group">
                    <div class="custom-control custom-radio custom-control-inline">
                        <input 
                            type="radio" class="custom-control-input" id="customRadio" 
                            name="memberGender" value="man" checked = {memberGender === 'man'}
                            onChange = {this.changeHandler}
                        />
                        <label class="custom-control-label" for="customRadio">남</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input 
                            type="radio" class="custom-control-input" id="customRadio2"
                            name="memberGender" value="woman" checked = {memberGender === 'woman'}
                            onChange = {this.changeHandler}
                        />
                        <label class="custom-control-label" for="customRadio2">여</label>
                    </div>
                </div>
                <div className="form-group">
                    <label>주소</label>
                    <input 
                        type="address" className="form-control" placeholder="주소" name = "memberAddress"
                        value = {memberAddress} onChange = {this.changeHandler}
                    />
                    <input
                        type="address" className="form-control" placeholder="상세 주소" name = "memberAddressDetail"
                        value = {memberAddressDetail} onChange = {this.changeHandler}
                    />
                </div>
                <div className="form-group">
                    <label>생년월일</label>
                    <div className="input-group">
                        <input 
                            type="number" className="form-control" name = "memberBirthYear"
                            value = {memberBirthYear} onChange = {this.changeHandler}
                        />
                        <span class="input-group-addon">년</span>
                        <input 
                            type="tel" className="form-control" name = "memberBirthMonth"
                            value = {memberBirthMonth} onChange = {this.changeHandler}
                        />
                        <span class="input-group-addon">월</span>
                        <input
                            type="tel" className="form-control"name = "memberBirthDay"
                            value = {memberBirthDay} onChange = {this.changeHandler}
                        />
                        <span class="input-group-addon">일</span>
                    </div>
                </div>
                <div className="form-group">
                    <label>휴대전화 번호</label>
                    <div className="input-group">
                        <input 
                            type="tel" id="ex1" className="form-control" name = "memberPhone1"
                            value = {memberPhone1} onChange = {this.changeHandler}
                        />
                        <span class="input-group-addon">-</span>
                        <input 
                            type="tel" className="form-control" name = "memberPhone2"
                            value = {memberPhone2} onChange = {this.changeHandler}
                        />
                        <span class="input-group-addon">-</span>
                        <input
                            type="tel" className="form-control" name = "memberPhone3"
                            value = {memberPhone3} onChange = {this.changeHandler}
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