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
            checkNameStyle: {},
            checkNameText: '',

            password: '',
            password2: '',
            availablePwd: false,
            checkPwdStyle: {},
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
                            checkNameStyle: {color: 'green'},
                            checkNameText: '사용 가능한 id'
                        }
                    )
                } else if (jsonStr === '0') {
                    that.setState({
                            availableName: false,
                            checkNameStyle: {color: 'red'},
                            checkNameText: '이미 사용중인 id'
                        }
                    )
                } else {
                    that.setState({
                            availableName: false,
                            checkNameStyle: {color: 'red'},
                            checkNameText: '서버 오류 발생'
                        }
                    )
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
                checkPwdStyle: {color: 'red'},
                checkPwdText: '비말번호를 입력하세요.'
            });
        }
        // 비밀번호가 같다면
        else if (password === password2) {
            this.setState({
                availablePwd: true,
                checkPwdStyle: {color: 'green'},
                checkPwdText: '일치'
            });
        }
        // 비밀번호가 같지 않다면
        else {
            this.setState({
                availablePwd: false,
                checkPwdStyle: {color: 'red'},
                checkPwdText: '불일치'
            });
        }
    }// checkPWD


    isJoinPossible = () => {
        const {
            memberName, availableName,
            password, availablePwd
        } = this.state;

        if (memberName.length < 1 || password.length < 1) {
            alert('아이디와 비밀번호는 필수 입력값입니다.');
            return false;
        } else if (availableName === false) {
            alert('사용 불가능한 아이디를 입력하셨습니다.');
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
                        <h3>회원가입</h3>
                        <h5
                            style={{paddingBottom: "10px", color: "red"}}>필수입력사항
                        </h5>
                        <div className="form-group">
                            <label>아이디<span
                                style={{paddingLeft: "2px", color: "red", fontSize: "initial"}}>*</span></label>
                            <input type="text" className="form-control" placeholder="아이디" id="memberName"
                                   name="memberName" value={memberName} onChange={this.changeHandler}/>
                            <button onClick={this.validateName} className="btn btn-dark" id="validate-id-btn">중복확인
                            </button>
                            <span className="custom-check" style={checkNameStyle}>{checkNameText}</span>
                        </div>
                        <div className="form-group">
                            <label>비밀번호 <span style={{color: "red", fontSize: "initial"}}>*</span></label>
                            <input type="password" className="form-control" placeholder="비밀번호"
                                   name="password" value={password} onChange={this.changeHandler}
                                   onKeyUp={this.checkPWD}/>
                        </div>
                        <div className="form-group">
                            <label>비밀번호 확인<span style={{paddingLeft: "2px", color: "red", fontSize: "initial"}}>*</span></label>
                            <input type="password" className="form-control" placeholder="비밀번호 확인"
                                   name="password2" value={password2} onChange={this.changeHandler}
                                   onKeyUp={this.checkPWD}/>
                            <span style={checkPwdStyle}>{checkPwdText}</span>
                        </div>

                        {/*기타정보*/}
                        <br/>
                        <h5>추가입력사항</h5>
                        <ImageUploader
                            singleImage={true}
                            withIcon={false}
                            withPreview={true}
                            onChange={this.onDrop}
                            buttonText="프로필 이미지"
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