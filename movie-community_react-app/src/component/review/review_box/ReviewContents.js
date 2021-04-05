import React, { Component, useState } from "react";
import { Accordion, Button, Dropdown, Modal, Form } from "react-bootstrap";

import { Slide } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'

import './ReviewContents.css';
import axios from "axios";
import { REST_API_SERVER_URL, IMAGE_RESOURCE_URL } from "component/constants/APIConstants";
import ReactImageUploadComponent from "react-images-upload";




import editImage from "img/button/edit.png";
import closeImage from "img/button/close.png";




//Review 게시물 상단
function ReviewHeader(props) {

    const [contentModified, setContent] = useState(props.data.reviewData.content);

    const [addImageList, setImageList] = useState([]);

    const [contentModalShow, setContentShow] = useState(false);
    const [imageModalShow, setImageShow] = useState(false);

    const handleContentShow = () => {
        setContentShow(!contentModalShow);
    }

    const handleImageShow = () => {
        setImageShow(!imageModalShow);
    }

    function onDrop(pictureFiles) {
        console.log(pictureFiles)
        setImageList(addImageList.concat(pictureFiles));
    }

    const convertDateFormet = (dateStr) => {
        let date = new Date(dateStr);
        return date.toLocaleString();
    }

    const modifyReview = () => {
        let dataModified = {
            reviewId: props.data.reviewId,
            content: contentModified,
        }
        props.modifyReview(dataModified);
        handleContentShow();
    }

    const addImage = () => {

        let addImageData = {
            reviewId: props.data.reviewId,
            imageList: addImageList.concat(),
            content: props.data.reviewData.content
        }
        props.addImage(addImageData);
    }

    const deleteReview = () => {
        const data = {
            reviewId: props.data.reviewId
        }
        props.deleteReview(data)
    }

    const CustomToggle = React.forwardRef(({ children, onClick }, ref) => (
        <img
            ref={ref}
            alt={editImage}
            src={editImage}
            onClick={(e) => {
                e.preventDefault();
                onClick(e);
            }}
        >
            {children}
        </img>
    ));

    return (
        <div className="card-header">
            <div className="row justify-content-md-center">
                <div className="h5 col-md-auto">
                    <p>{props.data.movieTitle}</p>
                </div>
            </div>
            <div className="row">
                <div className="col-1 contents-center">
                    <img alt="" className="member-thumnail" src={props.data.thumnailUri} />
                </div>
                <div className="col-9">
                    <div className="row">
                        <div className="col">{props.data.writer.memberName}</div>
                    </div>
                    <div className="row">
                        <div className="col">{convertDateFormet(props.data.createDate)}</div>
                    </div>
                </div>
                <div className="col-1 contents-center">

                    {(props.data.changeable) ? (
                        <Dropdown>
                            <Dropdown.Toggle as={CustomToggle} id="dropdown-basic">

                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                <Dropdown.Item onClick={handleContentShow}>내용수정</Dropdown.Item>
                                <Modal show={contentModalShow} onHide={handleContentShow}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>내용변경</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <Form.Control as="textarea" value={contentModified}
                                            rows={8} onChange={(e) => { setContent(e.target.value); }} />

                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={handleContentShow}>
                                            Close
                                    </Button>
                                        <Button variant="primary" onClick={modifyReview}>
                                            Save Changes
                                    </Button>
                                    </Modal.Footer>
                                </Modal>


                                <Dropdown.Item onClick={handleImageShow}>사진추가</Dropdown.Item>
                                <Modal show={imageModalShow} onHide={handleImageShow}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>이미지 추가</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <ReactImageUploadComponent
                                            withIcon={true}
                                            withPreview={true}
                                            buttonText="사진 추가"
                                            onChange={onDrop}
                                            imgExtension={[".jpg", ".gif", ".png", ".gif"]}
                                            maxFileSize={5242880}>
                                        </ReactImageUploadComponent>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={handleImageShow}>
                                            Close
                                    </Button>
                                        <Button variant="primary" onClick={addImage}>
                                            Save Changes
                                    </Button>
                                    </Modal.Footer>
                                </Modal>

                                <Dropdown.Item onClick={deleteReview}>삭제</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    ) : null}

                </div>
            </div>

        </div>

    )
}

//Review 게시물 중앙
function ReviewBody(props) {

    const imageUrlList = [];

    const [like, setLike] = useState(props.data.likePressed);
    const [likeCount, setCount] = useState(props.data.likeCount);

    props.data.images.forEach(image => {
        imageUrlList.push({ url: IMAGE_RESOURCE_URL + image.fileName })
    })

    const handleLike = (event) => {
        const params = {
            reviewId: parseInt(props.data.reviewId)
        }

        const token = localStorage.getItem("token");

        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        if (!like) {
            axios.post(REST_API_SERVER_URL + "/api/review/like", params, config).then(response => {
                setLike(!like);
                setCount(likeCount + 1);
            });
        } else {
            config["params"] = params;
            axios.delete(REST_API_SERVER_URL + "/api/review/like", config).then(response => {
                setLike(!like);
                setCount(likeCount - 1);
            });

        }

    }

    return (
        <div className="card-body">
            <div className="review-content">
                <p>{props.data.content}</p>
            </div>
            <div className="review-images">
                <div className="slide-container">
                    <Slide autoplay={false} >
                        {(imageUrlList.length < 1) ? null :
                            imageUrlList.map(image => {
                                return (
                                    <div className="each-slide">
                                      <img alt="no image" src={image.url}></img>
                                    </div>
                                )
                            })
                        }
                    </Slide>
                </div>

            </div>
            <div className="row">
                <div className="col review-like-btn">
                    <button className={"btn " + ((like) ? "btn-primary" : "btn-outline-primary")} like={false} onClick={handleLike}>Like</button>
                </div>
                <div className="col">
                    <div className="review-like">{likeCount}명이 좋아합니다</div>
                </div>
            </div>
        </div>
    )
}

//Review 하단
function ReviewFooter(props) {
    const [inputValue, setValue] = useState("");
    const [reviewComments, setReviewComments] = useState({
        reviewCommentList: [...props.data.commentsList]
    });

    const setComment = (event) => {
        const { value } = event.target
        setValue(value)
    }
    const writeComment = () => {

        let myName = localStorage.getItem("authenticatedMember");
        const newComment = {
            reviewId: props.data.reviewId,
            isPossibleRemove: true,
            id: null,
            member: {
                memberName: myName,
            },
            content: inputValue,
        }

        setValue("")

        const token = localStorage.getItem("token");

        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        axios.post(REST_API_SERVER_URL + "/api/review/comment", newComment, config).then(response => {
            console.log(response.data);

            newComment.id = response.data.comment.commentId;
            setReviewComments({
                reviewCommentList: [...reviewComments.reviewCommentList, newComment]
            })
        }).catch(error => console.log(error));
    }

    const deleteComment = (commentId) => {
        const token = localStorage.getItem("token");
        const config = {
            headers: {
                'Authorization': token
            },
            params: {
                commentId: commentId,
            }
        }

        console.log(JSON.stringify(config, null, 4));
        axios.delete(REST_API_SERVER_URL + "/api/review/comment", config).then(response => {
            if (response.data.result === "SUCCESS") {
                let delCommentList = [...reviewComments.reviewCommentList];
                let delIndex = delCommentList.findIndex(data => data.id === commentId)
                delCommentList.splice(delIndex, 1);
                setReviewComments({
                    reviewCommentList: [...delCommentList]
                });

            }
        }).catch(error => console.log(error));
    }

    const handleKeyPress = (event) => {
        if (event.key === "Enter") {
            writeComment();
        }
    }


    return (

        <div className="card-footer">
            <Accordion>
                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                    댓글보기
                </Accordion.Toggle>
                <Accordion.Collapse eventKey="0">
                    <div>
                        <div className="comment-area">
                            {reviewComments.reviewCommentList.map(comment => {
                                return (
                                    <div className="bg-white comment" key={comment.id}>
                                        <div className="comment-top">

                                            <h5 className="comment-name">{comment.member.memberName}</h5>

                                            {(comment.isPossibleRemove) ? <img className="x-btn" alt={closeImage} src={closeImage} onClick={() => { deleteComment(comment.id) }}></img> : null}

                                        </div>
                                        <br></br>
                                        <div>
                                            <p className="comment-content">{comment.content}</p>
                                        </div>

                                    </div>
                                )
                            })}
                        </div>

                        <div className="input-group mt-3">
                            <input className="form-control" maxLength={100} value={inputValue} onChange={setComment} onKeyPress={handleKeyPress}></input>
                            <button className="btn btn-outline-secondary" type="button" onClick={writeComment} >작성</button>
                        </div>
                    </div>
                </Accordion.Collapse>
            </Accordion>
        </div>
    )
}



//ReviewBox Component
export default class ReviewContent extends Component {
    constructor(props) {
        super(props);

        let likePressed = false;
        let myName = localStorage.getItem("authenticatedMember");
        console.log("ReviewContents is rendering!");
        for (let i = 0; i < this.props.reviewData.likesList.length; i++) {
            if (this.props.reviewData.likesList[i].member.memberName === myName) {
                likePressed = true;
                console.log("is Pressed!!!!!!!!!!!");
            }
        }

        this.props.reviewData.commentsList.forEach(data => {
            if (data.member.memberName === myName) {
                data["isPossibleRemove"] = true;
            } else {
                data["isPossibleRemove"] = false;
            }
        })

        this.state = {
            headerInfo: {
                reviewId: this.props.reviewData.id,
                changeable: (this.props.reviewData.member.memberName === localStorage.getItem("authenticatedMember")),
                movieTitle: this.props.reviewData.movieTitle,
                writer: this.props.reviewData.member,
                createDate: this.props.reviewData.createDate,
                reviewData: this.props.reviewData,
            },
            bodyData: {
                reviewId: this.props.reviewData.id,
                content: this.props.reviewData.content,
                images: this.props.reviewData.imageList,
                likeCount: this.props.reviewData.likeCount,
                likesList: [...this.props.reviewData.likesList],
                likePressed: likePressed,
                rating: this.props.reviewData.rating,
            },
            footerData: {
                reviewId: this.props.reviewData.id,
                member: this.props.reviewData.member,
                commentsList: [...this.props.reviewData.commentsList]
            }
        }
    }

    render() {
        return (
            <div className="review-box">
                <div className="card">
                    <ReviewHeader data={this.state.headerInfo}
                        modifyReview={this.props.modifyReview}
                        deleteReview={this.props.deleteReview}
                        addImage={this.props.addImage} />
                    <ReviewBody data={this.state.bodyData} />
                    <ReviewFooter data={this.state.footerData} />
                </div>
            </div>
        )
    }
}
