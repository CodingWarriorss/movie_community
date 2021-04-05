import React, {Component} from 'react'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faComment, faHeart} from "@fortawesome/free-solid-svg-icons";
import {IMAGE_RESOURCE_URL} from "../constants/APIConstants";

class ProfileReviewComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {reviewData} = this.props;

        return (
            <div className="profile__photo">
                <a href="#">
                    <img
                        src={reviewData.imageList.length > 0 ? IMAGE_RESOURCE_URL + reviewData.imageList[0].fileName : 'https://user-images.githubusercontent.com/62331803/113478597-c4baa800-94c4-11eb-9359-0a300fab1044.png'}/>
                </a>
                <div className="profile__photo-overlay">
                            <span className="profile__photo-stat">
                            <FontAwesomeIcon icon={faHeart}/>
                                {reviewData.likeCount}</span>
                    <span className="profile__photo-stat">
                                <FontAwesomeIcon icon={faComment}/>
                        {reviewData.commentCount}</span>
                </div>
            </div>
        )
    }
}

export default ProfileReviewComponent