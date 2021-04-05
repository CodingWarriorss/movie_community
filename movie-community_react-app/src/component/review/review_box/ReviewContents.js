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
import {Favorite, FavoriteBorder } from "@material-ui/icons";




//Review 게시물 상단
function ReviewHeader(props) {

    //수정될 택스트
    const [contentModified, setContent] = useState(props.reviewData.content);

    //내용 수정 Modal handle flag
    const [contentModalShow, setContentShow] = useState(false);

    //이미지 추가 Modal handle flag
    const [imageModalShow, setImageShow] = useState(false);

    let addImageList = [];

    const handleContentShow = () => {
        setContentShow(!contentModalShow);
    }

    const handleImageShow = () => {
        setImageShow(!imageModalShow);
    }

    function onDrop(pictureFiles) {
        addImageList = pictureFiles;
    }

    const convertDateFormet = (dateStr) => {
        let date = new Date(dateStr);
        return date.toLocaleString();
    }

    const modifyReview = () => {
        let dataModified = {
            reviewId: props.reviewData.id,
            content: contentModified,
        }
        props.modifyReview(dataModified);
        handleContentShow();
    }

    const addImage = () => {

        let addImageData = {
            reviewId: props.reviewData.id,
            imageList: addImageList.concat(),
            content: props.reviewData.content
        }
        props.addImage(addImageData);
        handleImageShow();
    }

    const deleteReview = () => {
        const data = {
            reviewId: props.reviewData.id
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
                    <p>{props.reviewData.movieTitle}</p>
                </div>
            </div>
            <div className="row">
                <div className="col-1 contents-center">
                    <img
                        src={IMAGE_RESOURCE_URL + props.reviewData.member.profileImg}
                        style={{width : 50, height : 50, borderRadius : 25}}/>
                </div>
                <div className="col-9">
                    <div className="row">
                        <div className="col">{props.reviewData.member.memberName}</div>
                    </div>
                    <div className="row">
                        <div className="col">{convertDateFormet(props.reviewData.createDate)}</div>
                    </div>
                </div>
                <div className="col-1 contents-center">

                    {(props.reviewData.member.memberName === localStorage.getItem("authenticatedMember")) ? (
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

    const handleLike = (event) => {
        // const params = {
        //     reviewId: parseInt(props.data.reviewId)
        // }

        // const token = localStorage.getItem("token");

        // let config = {
        //     headers: {
        //         'Authorization': 'Bearer ' + token
        //     }
        // }

        // if (!like) {
        //     axios.post(REST_API_SERVER_URL + "/api/review/like", params, config).then(response => {
        //         setLike(!like);
        //         setCount(likeCount + 1);
        //     });
        // } else {
        //     config["params"] = params;
        //     axios.delete(REST_API_SERVER_URL + "/api/review/like", config).then(response => {
        //         setLike(!like);
        //         setCount(likeCount - 1);
        //     });

        // }

    }

    let like = false;
    
    props.reviewData.likesList.forEach( likeinfo =>{
        if( likeinfo.member.memberName === localStorage.getItem("authenticatedMember") ) like = true;
    });

    return (
        <div className="card-body">
            <div className="review-content">
                <p>{props.reviewData.content}</p>
            </div>
            <div className="review-images">
                <div className="slide-container">
                    <Slide autoplay={false} >
                        {(props.reviewData.imageList.length < 1) ? null :
                            props.reviewData.imageList.map(image => {
                                return (
                                    <div className="each-slide" key={image.id}>
                                        <img alt="no image" src={ IMAGE_RESOURCE_URL+ image.fileName}></img>
                                    </div>
                                )
                            })
                        }
                    </Slide>
                </div>

            </div>
            <div className="row">
                <div className="col review-like-btn">
                    {(like) ?
                        <Favorite style={{
                            color: 'indianred',
                            fontSize: 40,
                            marginLeft : 10,
                        }} onClick={handleLike}/>
                        : <FavoriteBorder style={{
                            color: 'indianred',
                            fontSize : 40,
                            marginLeft : 10,
                        }} onClick={handleLike}/>
                    }
                </div>
                <div className="col">
                    <div className="review-like" style={{
                        marginRight : 15,
                    }}>{props.reviewData.likesList.length}명이 좋아합니다</div>
                </div>
            </div>
        </div>
    )
}

//Review 하단
function ReviewFooter(props) {
    const [inputValue, setValue] = useState("");

    const setComment = (event) => {
        const { value } = event.target
        setValue(value)
    }
    const writeComment = () => {

        const newComment = {
                reviewId : props.reviewData.id,
                id: null,
                member: {
                    memberName: localStorage.getItem("authenticatedMember"),
                },
                content: inputValue,
        }

        props.addComment( newComment );
        setValue("")
    }

    const deleteComment = (commentId) => {

        this.props.deleteComment( commentId );
        // const token = localStorage.getItem("token");
        // const config = {
        //     headers: {
        //         'Authorization': token
        //     },
        //     params: {
        //         commentId: commentId,
        //     }
        // }

        // console.log(JSON.stringify(config, null, 4));
        // axios.delete(REST_API_SERVER_URL + "/api/review/comment", config).then(response => {
        //     if (response.data.result === "SUCCESS") {
        //         let delCommentList = [...reviewComments.reviewCommentList];
        //         let delIndex = delCommentList.findIndex(data => data.id === commentId)
        //         delCommentList.splice(delIndex, 1);
        //         setReviewComments({
        //             reviewCommentList: [...delCommentList]
        //         });

        //     }
        // }).catch(error => console.log(error));
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
                            {props.reviewData.commentsList.map(comment => {
                                const myComment = ( comment.member.memberName === localStorage.getItem("authenticatedMember") );
                                return (
                                    <div className="bg-white comment" key={comment.id}>
                                        <div className="comment-top">
                                            <h5 className="comment-name">{comment.member.memberName}</h5>
                                            {(myComment) ? <img className="x-btn" alt={closeImage} src={closeImage} onClick={() => { deleteComment(comment.id) }}></img> : null}
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
                    <ReviewHeader reviewData={this.props.reviewData}
                                  modifyReview={this.props.modifyReview}
                                  deleteReview={this.props.deleteReview}
                                  addImage={this.props.addImage} />
                    <ReviewBody reviewData={this.props.reviewData} 
                                likeReview={this.props.likeReview}
                                unlikeReview={this.props.unlikeReview}
                    />
                    <ReviewFooter reviewData={this.props.reviewData} 
                                addComment={this.props.addComment}
                                deleteComment={this.props.deleteComment}
                    />
                </div>
            </div>
        )
    }
}