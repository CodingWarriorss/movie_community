import React, { Component } from 'react';
import axios from "axios";

class SignupComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            memberName: '',
            availableName: false,
            checkNameStyle: {},
            checkNameText: '',

            password: '',
            password2: '',
            availablePwd: false,
            checkPwdStyle: {},
            checkPwdText: '',

            name: '',
            email: '',
            address: '',
            addressDetail: '',
            gender: 'male',
            birth1: '',
            birth2: '',
            birth3: '',
            phone1: '',
            phone2: '',
            phone3: ''
        }

        // 바인딩
        this.changeHandler = this.changeHandler.bind(this);

        this.validateName = this.validateName.bind(this); // id 중복체크

        this.checkPWD = this.checkPWD.bind(this); // 비밀번호 일치체크

        this.isJoinPossible = this.isJoinPossible.bind(this);
        this.joinClicked = this.joinClicked.bind(this); // 회원가입
    }// constructor


    changeHandler = (e) => {
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        )

        // 아이디 변경되면 중복체크 다시해야 함
        if (e.target.name === 'memberName') {
            this.setState({
                [this.state.availableName]: false,
                [this.state.checkNameStyle]: {},
                [this.state.checkNameText]: ''
            }
            )
        }
    }// changeHandler


    validateName = (e) => {    // 아이디 중복체크
        e.preventDefault();

        const that = this;

        const data = {
            memberName: this.state.memberName
        }

        fetch('http://localhost:8080/checkid', {
            method: "post",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (jsonData) {
                return JSON.stringify(jsonData);
            })
            .then(function (jsonStr) {
                if (jsonStr === '1') {
                    that.setState({
                        availableName: true,
                        checkNameStyle: { color: 'green' },
                        checkNameText: '사용 가능한 id'
                    }
                    )
                    alert('사용가능한 id입니다.');
                } else if (jsonStr === '0') {
                    that.setState({
                        availableName: false,
                        checkNameStyle: { color: 'red' },
                        checkNameText: '이미 사용중인 id'
                    }
                    )
                    alert('이미 사용중인 id입니다.');
                } else {
                    that.setState({
                        availableName: false,
                        checkNameStyle: { color: 'red' },
                        checkNameText: '서버 오류 발생'
                    }
                    )
                    alert('서버 오류!');
                }
            });
    }// validateName


    checkPWD = () => {    // 비밀번호 일치여부
        const {
            password, password2
        } = this.state;

        // 비밀번호를 입력안했거나 둘 중 하나의 값이 입력 상태가 아닐 때에
        if (password.length < 1 || password2.length < 1) {
            this.setState({
                availablePwd: false,
                checkPwdStyle: { color: 'red' },
                checkPwdText: '비말번호를 입력하세요.'
            });
        }
        // 비밀번호가 같다면
        else if (password === password2) {
            this.setState({
                availablePwd: true,
                checkPwdStyle: { color: 'green' },
                checkPwdText: '일치'
            });
        }
        // 비밀번호가 같지 않다면
        else {
            this.setState({
                availablePwd: false,
                checkPwdStyle: { color: 'red' },
                checkPwdText: '불일치'
            });
        }
    }// checkPWD


    isJoinPossible = () => {
        const {
            memberName, availableName,
            password, availablePwd,
            name, email, address, addressDetail, gender,
            birth1, birth2, birth3,
            phone1, phone2, phone3
        } = this.state;

        if (memberName.length < 1 || password.length < 1 || name.length < 1
            || email.length < 1 || address.length < 1 || addressDetail.length < 1
            || gender.length < 1 || birth1.length < 1 || birth2.length < 1 || birth3.length < 1
            || phone1.length < 1 || phone2.length < 1 || phone3.length < 1) {
            alert('기입하지 않은 항목이 있습니다.');
            return false;
        } else if (availableName === false) {
            alert('사용 불가능한 id입니다.');
            return false;
        } else if (availablePwd === false) {
            alert('비밀번호가 일치하지 않습니다.');
            return false;
        }
        return true;
    }


    joinClicked = () => {
        if (!this.isJoinPossible()) {
            return;
        }

        const {
            memberName, password,
            name, email, address, addressDetail, gender,
            birth1, birth2, birth3,
            phone1, phone2, phone3
        } = this.state;

        axios.post(
            'http://localhost:8080/join',
            {
                memberName: memberName,
                password: password,
                name: name,
                email: email,
                address: address + " " + addressDetail,
                gender: gender,
                birth: birth1 + "/" + birth2 + "/" + birth3,
                phone: phone1 + "-" + phone2 + "-" + phone3
            }
        )
            .then((response) => {
                this.props.history.push(`/login`)
            }).catch(() => {

            });
    }

    render() {
        const {
            memberName, checkNameStyle, checkNameText,
            password, password2, checkPwdStyle, checkPwdText,
            name, email, address, addressDetail, gender,
            birth1, birth2, birth3,
            phone1, phone2, phone3
        } = this.state;

        return (
            <div className="outer">
                <div className="inner">
                    <div>
                        <h3>회원가입</h3>

                        {/*아이디*/}
                        <div className="form-group">
                            <label>아이디</label>
                            <input type="text" className="form-control" placeholder="아이디" id="memberName"
                                name="memberName" value={memberName} onChange={this.changeHandler} />
                            <button onClick={this.validateName} className="btn btn-dark" id="validate-id-btn">중복확인</button>
                            <span className="custom-check" style={checkNameStyle}>{checkNameText}</span>
                        </div>


                        {/*비밀번호*/}
                        <div className="form-group">
                            <label>비밀번호</label>
                            <input type="password" className="form-control" placeholder="비밀번호"
                                name="password" value={password} onChange={this.changeHandler} onKeyUp={this.checkPWD} />
                        </div>
                        <div className="form-group">
                            <label>비밀번호 확인</label>
                            <input type="password" className="form-control" placeholder="비밀번호 확인"
                                name="password2" value={password2} onChange={this.changeHandler} onKeyUp={this.checkPWD} />
                            <span style={checkPwdStyle}>{checkPwdText}</span>
                        </div>
                        <div>
                        </div>

                        {/*기타정보*/}
                        <div className="form-group">
                            <label>이름</label>
                            <input type="text" className="form-control" placeholder="이름"
                                name="name" value={name} onChange={this.changeHandler} />
                        </div>
                        <div className="form-group">
                            <label>이메일</label>
                            <input type="email" className="form-control" placeholder="이메일"
                                name="email" value={email} onChange={this.changeHandler} />
                        </div>
                        <div className="form-group">
                            <div className="custom-control custom-radio custom-control-inline">
                                <input
                                    type="radio" className="custom-control-input" id="customRadio"
                                    name="gender" value="male" checked={gender === 'male'}
                                    onChange={this.changeHandler}
                                />
                                <label className="custom-control-label" htmlFor="customRadio">남</label>
                            </div>
                            <div className="custom-control custom-radio custom-control-inline">
                                <input
                                    type="radio" className="custom-control-input" id="customRadio2"
                                    name="gender" value="female" checked={gender === 'female'}
                                    onChange={this.changeHandler}
                                />
                                <label className="custom-control-label" htmlFor="customRadio2">여</label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label>주소</label>
                            <input type="text" className="form-control" placeholder="주소"
                                name="address" value={address} onChange={this.changeHandler} />
                            <input type="text" className="form-control" placeholder="상세주소"
                                name="addressDetail" value={addressDetail} onChange={this.changeHandler} />
                        </div>
                        <div className="form-group">
                            <label>생년월일</label>
                            <div className="input-group">
                                <input type="number" className="form-control"
                                    name="birth1" value={birth1} onChange={this.changeHandler} />
                                <span className="input-group-addon">년</span>
                                <input type="number" className="form-control"
                                    name="birth2" value={birth2} onChange={this.changeHandler} />
                                <span className="input-group-addon">월</span>
                                <input type="number" className="form-control"
                                    name="birth3" value={birth3} onChange={this.changeHandler} />
                                <span className="input-group-addon">일</span>

                            </div>
                        </div>
                        <div className="form-group">
                            <label>휴대전화</label>
                            <div className="input-group">
                                <input type="number" name="phone1" className="form-control" value={phone1}
                                    onChange={this.changeHandler} />
                                <span className="input-group-addon">-</span>
                                <input type="number" name="phone2" className="form-control" value={phone2}
                                    onChange={this.changeHandler} />
                                <span className="input-group-addon">-</span>
                                <input type="number" name="phone3" className="form-control" value={phone3}
                                    onChange={this.changeHandler} />
                            </div>
                        </div>

                        <button onClick={this.joinClicked} className="btn btn-dark btn-lg btn-block">회원가입</button>
                    </div>
                </div>
            </div>
        )

    }
}

export default SignupComponent
/*
<export default>
변수, 함수, 오브젝트, 클래스 등을 보낼 수 있는 명령어.
default는 기본이라는 의미를 담고 있으며,
default로 export한 Loginapp은 중괄호를 사용하지 않고도 import할 수 있다.
https://m.blog.naver.com/gi_balja/221227430979
*/