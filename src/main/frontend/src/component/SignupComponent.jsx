import React, {Component} from 'react';

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
            gender: '',
            birth1: '',
            birth2: '',
            birth3: '',
            phone1: '',
            phone2: '',
            phone3: ''
        }

        // 바인딩
        this.isPossibleMemberName = this.isPossibleMemberName.bind(this);
    }

    isPossibleMemberName(){
        if (this.state.availableName){
            this.setState(
                {
                    [this.state.checkNameStyle] : {
                        color : 'green'
                    },
                    [this.state.checkNameText] : '사용 가능한 id입니다.'
                }
            )
        } else {
            this.setState(
                {
                    [this.state.checkNameStyle] : {
                        color : 'red'
                    },
                    [this.state.checkNameText] : '사용할 수 없는 id입니다.'
                }
            )
        }
    }

    render() {
        return (
            <div>
                <h3>회원가입</h3>

                {/*아이디*/}
                <div className="form-group">
                    <label>아이디</label>
                    <input type="text" placeholder="아이디"/>
                    <button>중복확인</button>
                    <p style={this.state.checkNameStyle}>{this.state.checkNameText}</p>
                </div>

                {/*비밀번호*/}
                <div className="form-group">
                    <label>비밀번호</label>
                    <input type="password" placeholder="비밀번호"/>
                </div>
                <div className="form-group">
                    <label>비밀번호 확인</label>
                    <input type="password" placeholder="비밀번호 확인"/>
                </div>
                <div>
                    <p style={this.state.checkPwdStyle}>{this.state.checkPwdText}</p>
                </div>
                
                {/*기타정보*/}
                <div className="form-group">
                    <label>이름</label>
                    <input type="text" placeholder="이름"/>
                </div>
                <div className="form-group">
                    <label>이메일</label>
                    <input type="email"/>
                </div>
                <div className="form-group">
                    <label>성별</label>
                    <input type="radio"/><label>남</label>
                    <input type="radio"/><label>여</label>
                </div>
                <div className="form-group">
                    <label>주소</label>
                    <input type="text"/>
                    <input type="text"/>
                </div>
                <div className="form-group">
                    <label>생년월일</label>
                    <input type="text"/>
                    <input type="text"/>
                    <input type="text"/>
                </div>
                <div className="form-group">
                    <label>휴대전화번호</label>
                    <input type="text"/>
                    <input type="text"/>
                    <input type="text"/>
                </div>

                <button>취소</button><button>회원가입</button>
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