import React, {Component} from 'react'
import '../css/Profile.css';
import EditProfileComponent from "./EditProfileComponent";
import {DEFAULT_AVATAR_URL, IMAGE_RESOURCE_URL, REST_API_SERVER_URL} from "../constants/APIConstants";
import axios from "axios";
import ProfileReviewComponent from "./ProfileReviewComponent";
import DeleteMemberComponent from "./DeleteMemberComponent";

class ProfileComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isModalEditOpen: false,
            isModalWithdrawOpen: false,

            memberName: '',
            name: '',
            website: '',
            bio: '',
            profileImg: '',

            reviewList: [],
        }

        this.loadMemberInfo = this.loadMemberInfo.bind(this);
        this.loadReview = this.loadReview.bind(this);
        this.openEditModal = this.openEditModal.bind(this);
        this.closeEditModal = this.closeEditModal.bind(this);
        this.openWithdrawModal = this.openWithdrawModal.bind(this);
        this.closeWithdrawModal = this.closeWithdrawModal.bind(this);
    }

    componentDidMount() {
        this.loadMemberInfo(this.props.location.memberName);
        this.loadReview(this.props.location.memberName);
    }

    openEditModal() {
        this.setState({isModalEditOpen: true});
    }

    closeEditModal() {
        this.setState({isModalEditOpen: false});
    }

    openWithdrawModal() {
        this.setState({isModalWithdrawOpen: true});
    }

    closeWithdrawModal() {
        this.setState({isModalWithdrawOpen: false});
    }

    loadMemberInfo(memberName) {
        const requestUrl = REST_API_SERVER_URL + '/api/member';
        let config = {
            headers: {
                'Authorization': localStorage.getItem('token')
            },
            params: {
                memberName: memberName
            }
        }
        axios.get(requestUrl, config)
            .then((response) => {
                console.log(response.data);
                const member = response.data.member;
                this.setState({
                    memberName: memberName,
                    name: member.name,
                    website: member.website,
                    bio: member.bio,
                    profileImg: member.profileImg ? IMAGE_RESOURCE_URL + member.profileImg : DEFAULT_AVATAR_URL,
                });
            }).catch((error) => {

        })

    }

    loadReview(memberName) {
        const requestUrl = REST_API_SERVER_URL + '/api/review';
        let config = {
            headers: {
                'Authorization': localStorage.getItem('token')
            },
            params: {
                memberName: memberName,
                pageIndex: 0
            }
        }
        axios.get(requestUrl, config)
            .then((response) => {
                console.log(response.data);
                this.setState({
                    reviewList: response.data
                });
            }).catch((error) => {

        })
    }

    render() {
        const {
            isModalEditOpen, isModalWithdrawOpen,
            memberName, name, website, bio, profileImg,
            reviewList
        } = this.state;

        return (
            <main id="profile" style={{backgroundColor: "white", padding: "100px 100px"}}>
                <header className="profile__header">
                    <div className="avatar__container">
                        <form action="" id="frm_profile_img">
                            <input type="file" name="profileImage" style={{display: "none"}}/>
                        </form>
                        <img
                            src={profileImg}
                            id="profile_image"
                            style={{cursor: "pointer"}}/>
                    </div>

                    <div className="profile__info">
                        <div className="profile__title">
                            <h1>{memberName}</h1>
                            <a href="#">
                                <button className="profile_edit_btn" onClick={this.openEditModal}>
                                    회원수정
                                </button>
                                <EditProfileComponent isOpen={isModalEditOpen} close={this.closeEditModal}/>
                            </a>
                            <a href="#">
                                <button className="profile_follow_btn" onClick={this.openWithdrawModal}>
                                    회원탈퇴
                                </button>
                                <DeleteMemberComponent isOpen={isModalWithdrawOpen}
                                                       close={this.closeWithdrawModal}/>
                            </a>
                        </div>

                        <div className="profile__bio" style={{paddingLeft: "3px"}}>
                            <p className="profile__fullname">{name}</p>
                            <p>{bio}</p>
                            <p><a href={website}>{website}</a></p>
                        </div>
                    </div>
                </header>
                <div className="profile__photo-grid">
                    {reviewList.map(
                        reviewData => {
                            return <ProfileReviewComponent reviewData={reviewData} key={reviewData.reviewId}/>
                        }
                    )}
                </div>
            </main>
        )
    }
}

export default ProfileComponent