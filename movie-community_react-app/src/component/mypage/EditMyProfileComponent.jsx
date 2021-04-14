import React, {Component} from 'react'
import {Modal, Button, Form} from "react-bootstrap";
import axios from "axios";
import {DEFAULT_AVATAR_URL, IMAGE_RESOURCE_URL, REST_API_SERVER_URL} from "../constants/APIConstants";
import ImageUploader from "react-images-upload";

class EditMyProfileComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            close: false,

            name: '',
            email: '',
            website: '',
            bio: '',

            picture: [],
        }

        this.loadMemberInfo = this.loadMemberInfo.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleSummit = this.handleSummit.bind(this);
        this.onDrop = this.onDrop.bind(this);
    }

    componentDidMount() {
        this.loadMemberInfo();
    }

    loadMemberInfo() {
        const requestUrl = REST_API_SERVER_URL + '/api/member';
        let config = {
            headers: {
                'Authorization': "Bearer " + localStorage.getItem('token')
            },
            params: {
                memberName: localStorage.getItem('authenticatedMember')
            }
        }
        axios.get(requestUrl, config)
            .then((response) => {
                const member = response.data.member;
                this.setState({
                    name: member.name,
                    website: member.website,
                    email: member.email,
                    bio: member.bio,
                    profileImg: member.profileImg ? IMAGE_RESOURCE_URL + member.profileImg : DEFAULT_AVATAR_URL,
                });
            }).catch((error) => {

        })
    }

    changeHandler = (e) => {
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        )
    }

    handleClose() {
        this.setState({
            close: true
        });
    }

    handleSummit() {
        const {
            name, email, website, bio, picture
        } = this.state;

        if (email
            && !/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i.test(email)) {
            alert('올바르지 않은 이메일 형식입니다.');
            return;
        }

        const url = REST_API_SERVER_URL + '/api/member';

        const formData = new FormData();
        formData.append('name', name);
        formData.append('email', email);
        formData.append('bio', bio);
        formData.append('website', website);
        if (picture.length > 0) {
            formData.append('profileImg', picture[0]);
        }

        const config = {
            headers: {
                'Authorization': "Bearer " +localStorage.getItem('token'),
                'Content-Type': "multipart/form-data"
            }
        }

        axios.put(url, formData, config)
            .then((response) => {
                const data = response.data;
                if (data.result === 'success') {
                    const member = data.member;
                    this.props.modifyMemberInfo(member);
                    alert('수정이 완료되었습니다.');
                    this.props.close();
                    // window.location.replace('/mypage');
                } else {
                    alert('수정에 실패했습니다.');
                }
            });
    }

    onDrop(pictureFile) {
        this.setState({
            picture: this.state.picture.concat(pictureFile)
        });
    }

    render() {
        const {
            isOpen, close,
        } = this.props;

        const {
            name, email, website, bio
        } = this.state;

        return (
            <Modal
                size="xs"
                show={isOpen}
                onHide={close}>
                <Modal.Header closeButton>회원 정보 수정</Modal.Header>

                <Modal.Body>
                    <ImageUploader
                        singleImage={true}
                        withIcon={false}
                        withPreview={true}
                        onChange={this.onDrop}
                        buttonText="프로필 이미지 변경"
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
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={close}>
                        취소
                    </Button>
                    <Button variant="primary" onClick={this.handleSummit}>
                        등록
                    </Button>
                </Modal.Footer>
            </Modal>
        )
    }
}

export default EditMyProfileComponent