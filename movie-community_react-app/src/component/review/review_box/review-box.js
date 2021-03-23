import React, { Component, useState } from "react";
import SimpleImageSlider from "react-simple-image-slider";
import { Accordion, Button } from "react-bootstrap";


import './review-box.css';

/*
    한곳에 구성을 다 넣자니 코드가 너무 길어지는거 같고
    그렇다고 파일로 나누어서 각 컴포넌트로 나누어 관리자하자니 파일이 많아져서
    어떻게 하는게 답을지 고민을 해봅시다...;;;
*/

//Review 게시물 상단
function ReviewHeader(props) {

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
                        <div className="col">{props.data.writerName}</div>
                    </div>
                    <div className="row">
                        <div className="col">{props.data.createDate}</div>
                    </div>
                </div>
                <div className="col-1 contents-center">
                    <button className="btn-col" onClick={() => { alert("이 기능은 어떻게 만들지?...") }}>edit</button>
                </div>
            </div>

        </div>

    )
}

//Review 게시물 중앙
function ReviewBody(props) {

    const imageUrlList = [];

    const [ like, setLike ] = useState(false);

    props.data.images.forEach(image => {
        imageUrlList.push({ url: image.imageUri })
    })

    const handleLike = ( event ) => {
        setLike( !like );
    }


    /*
        react-simple-image-slider 라이브러리 이용. 하여 이미지를 보여주는 부분을 사용.
        현재 이미지가 원하는 크기로 조정이 안되는 오류.
        Fix가 안되면 다른 것을 찾아보겠습다.
    */
    return (
        <div className="card-body">
            <div className="review-content">
                <p>{props.data.content}</p>
            </div>
            <div className="review-images">
                <SimpleImageSlider width={"100%"}
                    height={400} images={imageUrlList} showNavs={true} showBullets={true} />
            </div>
            <div className="row">
                <div className="col review-like-btn">
                    <button className={"btn "+ ((like) ? "btn-primary":"btn-outline-primary") } like={false} onClick={handleLike}>Like</button>
                </div>
                <div className="col">
                    <div className="review-like">{props.data.like}명이 좋아합니다</div>
                </div>
            </div>
        </div>
    )
}

//Review 하단
function ReviewFooter(props) {
    const [ inputValue , setValue ] = useState("");
    const [ reviewComments, setReviewComments ] = useState({
        reviewCommentList : [...props.data.comments]
    });
    
    const setComment = (event) =>{
        const { value } = event.target
        setValue( value )
    }
    const writeComment = () =>{
        const newComment = {
            commenterName : "여기에 사용자 찾아넣기",
            content : inputValue
        }

        setReviewComments( {
            reviewCommentList : [...reviewComments.reviewCommentList , newComment]
        })
        setValue( "" )

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
                                            <p className="comment-name">{comment.commenterName}</p>
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
export default class ReviewBox extends Component {
    constructor(props) {
        super(props);

        //좀더 보기좋은 코드를 위함 구조는 어떻게 해야할것인가...
        this.state = {
            headerInfo: {
                movieTitle: this.props.reviewData.movieTitle,
                writerName: this.props.reviewData.writerName,
                createDate: this.props.reviewData.createData,
                thumnailUri: this.props.reviewData.thumnailUri
            },
            bodyData: {
                content: this.props.reviewData.content,
                images: this.props.reviewData.images,
                like: this.props.reviewData.like
            },
            footerData: {
                comments : this.props.reviewData.comments
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

