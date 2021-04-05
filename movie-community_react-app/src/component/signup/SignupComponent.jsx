import React, {Component} from 'react';
import axios from "axios";
import {REST_API_SERVER_URL} from '../constants/APIConstants';
import ImageUploader from "react-images-upload";

class SignupComponent extends Component {

    constructor(props) {
        super(props);

        console.log(REST_API_SERVER_URL)
        this.state = {
            memberName: '',
            availableName: false,
            checkNameStyle: {display: "block", float: "right", color: "red"},
            checkNameText: '아이디를 중복확인 해주세요',

            password: '',
            password2: '',
            availablePwd: false,
            checkPwdStyle: {display: "block", float: "right", color: "red"},
            checkPwdText: '',

            name: '',
            email: '',
            bio: '',
            website: '',

            picture: []
        }

        // 바인딩
        this.changeHandler = this.changeHandler.bind(this);
        this.validateName = this.validateName.bind(this);
        this.checkPWD = this.checkPWD.bind(this);
        this.isJoinPossible = this.isJoinPossible.bind(this);
        this.joinClicked = this.joinClicked.bind(this);
        this.onDrop = this.onDrop.bind(this);
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
                    availableName: false,
                    checkNameStyle: {display: "block", float: "right", color: "red"},
                    checkNameText: '아이디를 중복확인 해주세요'
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

        if (!/(?=.*\d{1,50})(?=.*[a-zA-Z]{1,50}).{6,50}$/.test(this.state.memberName)) {
            this.setState({
                    availableName: false,
                    checkNameStyle: {display: "block", float: "right", color: 'red'},
                    checkNameText: '올바르지 않은 아이디 형식입니다'
                }
            )
            return;
        }

        fetch(REST_API_SERVER_URL + '/checkid', {
            method: "post",
            headers: {"Content-Type": "application/json"},
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
                            checkNameStyle: {display: "block", float: "right", color: 'green'},
                            checkNameText: '사용 가능한 아이디입니다'
                        }
                    )
                } else if (jsonStr === '0') {
                    that.setState({
                            availableName: false,
                            checkNameStyle: {display: "block", float: "right", color: "red"},
                            checkNameText: '이미 사용중인 아이디입니다'
                        }
                    )
                } else {
                    that.setState({
                            availableName: false,
                            checkNameStyle: {display: "block", float: "right", color: 'red'},
                            checkNameText: '서버 오류가 발생했습니다'
                        }
                    )
                }
            });
    }// validateName


    checkPWD = () => {    // 비밀번호 일치여부
        const {
            password, password2
        } = this.state;

        if (!/(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/.test(password)) {
            this.setState({
                availablePwd: false,
                checkPwdStyle: {display: "block", float: "right", color: 'red'},
                checkPwdText: '올바르지 않은 비밀번호 형식입니다'
            });
        }
        else if (password === password2) {
            this.setState({
                availablePwd: true,
                checkPwdStyle: {display: "block", float: "right", color: 'green'},
                checkPwdText: '비밀번호가 일치합니다'
            });
        }
        else {
            this.setState({
                availablePwd: false,
                checkPwdStyle: {display: "block", float: "right", color: 'red'},
                checkPwdText: '비밀번호가 일치하지 않습니다'
            });
        }
    }// checkPWD


    isJoinPossible = () => {
        const {
            memberName, availableName,
            password, availablePwd, email
        } = this.state;

        if (memberName.length < 1 || password.length < 1) {
            alert('아이디와 비밀번호는 필수 입력값입니다.');
            return false;
        } else if (availableName === false) {
            alert('사용 불가능한 아이디입니다.');
            return false;
        } else if (availablePwd === false) {
            alert('비밀번호가 일치하지 않습니다.');
            return false;
        } else if (email
            && !/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i.test(email)) {
            alert('올바르지 않은 이메일 형식입니다.');
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
            name, email, bio, website, picture
        } = this.state;

        const url = REST_API_SERVER_URL + '/join';

        const formData = new FormData();
        formData.append('memberName', memberName);
        formData.append('password', password);
        formData.append('name', name);
        formData.append('email', email);
        formData.append('bio', bio);
        formData.append('website', website);
        if (picture.length > 0) {
            formData.append('profileImg', picture[0]);
        }

        const config = {
            headers: {
                'Content-Type': "multipart/form-data"
            }
        }

        axios.post(url, formData, config)
            .then((response) => {
                window.location.replace('/login');
            }).catch(() => {

        });
    }

    onDrop(pictureFile) {
        this.setState({
            picture: this.state.picture.concat(pictureFile)
        });
    }

    render() {
        const {
            memberName, checkNameStyle, checkNameText,
            password, password2, checkPwdStyle, checkPwdText,
            name, email, bio, website
        } = this.state;

        return (
            <div className="outer">
                <div className="inner">
                    <div>
                        <h3>회원가입</h3><br/>
                        <div className="form-group">
                            <label>아이디<span
                                style={{paddingLeft: "2px", color: "red", fontSize: "initial"}}>*</span></label>
                            <br/>
                            <input type="text" className="form-control" placeholder="영문/숫자를 포함하여 6자리 이상" id="memberName"
                                   name="memberName" value={memberName} onChange={this.changeHandler}
                                   style={{width: "70%", float: "left", position: "static", display: "block"}}/>
                            <button onClick={this.validateName} className="btn btn-dark" id="validate-id-btn">중복확인
                            </button>
                            <span className="custom-check" style={checkNameStyle}>{checkNameText}</span>
                        </div>
                        <br/>
                        <br/>
                        <div className="form-group">
                            <label style={{float: "left", display: "inline-block"}}>비밀번호 <span
                                style={{color: "red", fontSize: "initial"}}>*</span></label>
                            <input type="password" className="form-control" placeholder="영문/숫자/특수문자를 포함하여 8자리 이상"
                                   name="password" value={password} onChange={this.changeHandler}
                                   onKeyUp={this.checkPWD}/>
                        </div>
                        <div className="form-group">
                            <label>비밀번호 확인<span style={{paddingLeft: "2px", color: "red", fontSize: "initial"}}>*</span></label>
                            <input type="password" className="form-control" placeholder="영문/숫자/특수문자를 포함하여 8자리 이상"
                                   name="password2" value={password2} onChange={this.changeHandler}
                                   onKeyUp={this.checkPWD}/>
                            <span style={checkPwdStyle}>{checkPwdText}</span>
                        </div>

                        <br/><br/>
                        <h5>추가입력사항</h5>
                        <ImageUploader
                            singleImage={true}
                            withIcon={false}
                            withPreview={true}
                            onChange={this.onDrop}
                            buttonText="프로필 이미지를 선택하세요"
                            imgExtension={[".jpg", ".gif", ".png", ".gif"]}
                            maxFileSize={5242880}
                        />
                        <div className="form-group">
                            <label>이름</label>
                            <input type="text" className="form-control" placeholder="ex. 안무비"
                                   name="name" value={name} onChange={this.changeHandler}/>
                        </div>
                        <div className="form-group">
                            <label>이메일</label>
                            <input type="email" className="form-control" placeholder="ex. movie520@gmail.com"
                                   name="email" value={email} onChange={this.changeHandler}/>
                        </div>
                        <div className="form-group">
                            <label>웹사이트</label>
                            <input type="text" className="form-control" placeholder="ex. https://www.ilovemovie.com"
                                   name="website" value={website} onChange={this.changeHandler}/>
                        </div>
                        <div className="form-group">
                            <label>자기소개</label>
                            <textarea className="form-control" placeholder="ex. 저는 만화영화를 사랑하는 안무비입니다. 무야호오오"
                                      name="bio" value={bio} onChange={this.changeHandler}/>
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