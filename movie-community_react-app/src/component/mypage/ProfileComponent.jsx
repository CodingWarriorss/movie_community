import React, {Component} from 'react'
import '../css/Profile.css';
import EditProfileComponent from "./EditProfileComponent";
import {REST_API_SERVER_URL} from "../constants/APIConstants";
import axios from "axios";
import AuthenticationService from "../login/AuthenticationService";
import ProfileReviewComponent from "./ProfileReviewComponent";

class ProfileComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isModalOpen: false,
            reviewList: [],
        }

        this.loadReview = this.loadReview.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.withdraw = this.withdraw.bind(this);
    }

    componentDidMount() {
        console.log('componentDidMount')
        this.loadReview();
    }

    openModal() {
        console.log('open edit modal');
        this.setState({isModalOpen: true});
    }

    closeModal() {
        console.log('close edit modal');
        this.setState({isModalOpen: false});
    }

    withdraw() {
        let config = {
            headers: {
                'Authorization': localStorage.getItem('token')
            }
        }

        axios.delete(REST_API_SERVER_URL + '/api/member', config)
            .then(function (response) {
                // handle success
                console.log(response);
                alert('회원 정보가 삭제되었습니다.');
                AuthenticationService.logout();
            })
    }

    loadReview() {
        const requestUrl = REST_API_SERVER_URL + '/api/review'
        let config = {
            headers: {
                'Authorization': localStorage.getItem('token')
            },
            params: {
                memberName: localStorage.getItem('authenticatedMember'),
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
        const memberName = localStorage.getItem('authenticatedMember');
        const name = localStorage.getItem('name');
        const website = localStorage.getItem('website');
        const bio = localStorage.getItem('bio');

        return (
            <main id="profile" style={{backgroundColor: "white", padding: "100px 100px"}}>
                <header className="profile__header">
                    <div className="avatar__container">
                        <form action="" id="frm_profile_img">
                            <input type="file" name="profileImage" style={{display: "none"}}/>
                        </form>
                        <img
                            src={localStorage.getItem('profileImg')}
                            id="profile_image"
                            style={{cursor: "pointer"}}/>
                    </div>

                    <div className="profile__info">
                        <div className="profile__title">
                            <h1>{memberName}</h1>
                            <a href="#">
                                <button className="profile_edit_btn" onClick={this.openModal}>
                                    회원수정
                                </button>
                                <EditProfileComponent isOpen={this.state.isModalOpen} close={this.closeModal}/>
                            </a>
                            <a href="#">
                                <button className="profile_follow_btn" onClick={this.withdraw}>
                                    회원탈퇴
                                </button>
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
                    {this.state.reviewList.map(
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