import React, { Component, useState } from "react";
import SimpleImageSlider from "react-simple-image-slider";
import { Accordion, Button } from "react-bootstrap";


import './ReviewContens.css';
import ReviewEditBox from "../ReviewEditBox";
import axios from "axios";
import { REST_API_SERVER_URL, IMAGE_RESOURCE_URL } from "component/constants/APIConstants";

//Review 게시물 상단
function ReviewHeader(props) {

    const [ isShow , setShow ] = useState(false);
    const handleShow = () =>{
        setShow(!isShow);
    }

    const convertDateFormet = (dateStr) =>{
        let date = new Date(dateStr);
        console.log( date);
        return date.toLocaleString();
    }

    return (
        <div className="card-header">
            <div className="row justify-content-md-center">
                <div className="h5 col-md-auto">
                    <p>{props.data.movieTitle}</p>
                </div>
            </div>
            <div className="row">
                <div className="col-1 contents-center">
                    <img className="member-thumnail" src={props.data.thumnailUri} />
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
                    <button className="btn-col" onClick={handleShow}>edit</button>
                    <ReviewEditBox handleShow={handleShow} isShow={isShow} isModify={true} ></ReviewEditBox>
                </div>
            </div>

        </div>

    )
}

//Review 게시물 중앙
function ReviewBody(props) {

    const imageUrlList = [];

    const [ like, setLike ] = useState(props.data.likePressed);
    const [ likeCount , setCount ] = useState(props.data.likeCount);

    props.data.images.forEach(image => {
        imageUrlList.push({ url: IMAGE_RESOURCE_URL + image.fileName })
    })

    const handleLike = ( event ) => {
        const param = {
            reviewId : props.data.id
        }

        const token = localStorage.getItem("token");

        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }


        if( !like ){
            axios.post( REST_API_SERVER_URL + "/api/review/like" , param , config ).then( response => {
                setLike( !like );
                setCount( likeCount + 1);
            });
        }else{

            axios.delete( REST_API_SERVER_URL + "/api/review/like" , config ).then( response => {
                setLike( !like );
                setCount( likeCount - 1);
            });

        }
       
    }

    return (
        <div className="card-body">
            <div className="review-content">
                <p>{props.data.content}</p>
            </div>
            <div className="review-images">
                { ( imageUrlList.length < 1) ? null:<SimpleImageSlider width={"100%"}
                    height={400} images={imageUrlList} showNavs={true} showBullets={true} />
                
                }
                
            </div>
            <div className="row">
                <div className="col review-like-btn">
                    <button className={"btn "+ ((like) ? "btn-primary":"btn-outline-primary") } like={false} onClick={handleLike}>Like</button>
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
    const [ inputValue , setValue ] = useState("");
    const [ reviewComments, setReviewComments ] = useState({
        reviewCommentList : [...props.data.commentsList]
    });
    
    const setComment = (event) =>{
        const { value } = event.target
        setValue( value )
    }
    
    const writeComment = () =>{
        const newComment = {
            reviewId : props.data.reviewId,
            content : inputValue
        }


        setValue( "" )

        const token = localStorage.getItem("token");

        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        axios.post( REST_API_SERVER_URL +"/api/review/comment" , newComment , config ).then( response => {
            console.log(response.data.id);
            setReviewComments( {
                reviewCommentList : [...reviewComments.reviewCommentList , newComment]
            })
        }).catch( error => console.log(error) );

    }

    const handleKeyPress = (event) => {
        if(event.key === "Enter") {
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
                                        <div className="bg-white comment">
                                            <p className="comment-name">{comment.member.memberName}</p>
                                            <p className="comment-content">{comment.content}</p>
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
export default class ReviewContens extends Component {
    constructor(props) {
        super(props);

        //좀더 보기좋은 코드를 위함 구조는 어떻게 해야할것인가...
        this.state = {
            headerInfo: {
                reviewId : this.props.reviewData.id,
                movieTitle: this.props.reviewData.movieTitle,
                writer: this.props.reviewData.member,
                createDate: this.props.reviewData.createDate,
            },
            bodyData: {
                reviewId : this.props.reviewData.id,
                content: this.props.reviewData.content,
                images: this.props.reviewData.imageList,
                likeCount: this.props.reviewData.likeCount,
                likesList :  this.props.reviewData.likesList,
                rating : this.props.reviewData.rating,
            },
            footerData: {
                reviewId : this.props.reviewData.id,
                commentsList : this.props.reviewData.commentsList
            }
        }
    }

    render() {
        return (
            <div className=" review-box">
                <div className="card">
                    <ReviewHeader data={this.state.headerInfo}/>
                    <ReviewBody data={this.state.bodyData}/>
                    <ReviewFooter data={this.state.footerData}/>
                </div>
            </div>
        )
    }
}

