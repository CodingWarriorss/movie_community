import React, { Component } from "react";

import ReviewContent from './review_box/ReviewContents';

import "./ReviewList.css";

import ReviewAPI from './api/ReviewRestAPI';
import CommentAPI from './api/CommentRestAPI';
import LikeAPI from './api/LikeRestAPI';

export default class ReviewList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            reviewList: [],
            page : 0
        }
        this.loadReview = this.loadReview.bind(this);
        this.scrollCheck = this.scrollCheck.bind(this);
        this.modifyReview = this.modifyReview.bind(this);
        this.addImage = this.addImage.bind(this);
        this.deleteReview = this.deleteReview.bind(this);

        this.addComment = this.addComment.bind(this);
        this.deleteComment = this.deleteComment.bind(this);
        this.likeReview = this.likeReview.bind(this);
        this.unlikeReview = this.unlikeReview.bind(this);

    }

    /*
        Review Data를 갱신하는 Method
    */
    loadReview() {

        ReviewAPI.getList(this.state.page , this.props.movieTitle)
        .then( response => {
            console.log(response.data);
            this.setState({
                reviewList: [...this.state.reviewList, ...response.data],
                page: (this.state.page + 1)
            })
        }).catch( error =>{
            console.log(error);
        });

    }

    scrollCheck() {
        let scrollHeight = Math.max(
            document.documentElement.scrollHeight,
            document.body.scrollHeight
        );
        let scrollTop = Math.max(
            document.documentElement.scrollTop,
            document.body.scrollTop
        );
        let clientHeight = document.documentElement.clientHeight;

        if ( parseInt(scrollTop + clientHeight ) + 1 >= scrollHeight) {
            this.loadReview();
        }
    }

    componentDidMount() {
        window.addEventListener("scroll", this.scrollCheck, true);
        this.loadReview();
    }

    componentDidUpdate(prevProps) {
        if (prevProps.movieTitle !== this.props.movieTitle) {
            this.setState({
                reviewList : [],
                page : 0
            }, () => {
                this.loadReview();
            })
        }
    }

    modifyReview = ( data ) => {

        ReviewAPI.modify(data)
        .then(response => {
            this.setState({
                reviewList : this.state.reviewList.map( review => (review.id === data.reviewId) ? { ...review, ...data } : review ),
            })
        }).catch( error => console.log(error) );

    }

    addImage = ( data ) => {

        ReviewAPI.addImage( data )
        .then( response => {
            const updateReviewData = response.data.review;
            const updateList = this.state.reviewList.map( review =>
                (review.id === updateReviewData.id) ? updateReviewData : review
            );
            this.setState({
                reviewList : updateList,
            })
        }).catch( error => console.log( error ));

    }

    deleteReview = ( data ) => {

        ReviewAPI.delete( data )
        .then( response =>{
            let id = data.reviewId;
            this.setState({
                reviewList : this.state.reviewList.filter( review => ( review.id !== id )),
            })
        }).catch( error => console.log( error ));

    }
  

    

    addComment( commentData ){

        CommentAPI.add( commentData )
        .then(response => {
            console.log(response.data);
            commentData.id = response.data.commentId;
            let updateList = this.state.reviewList.map( review =>{
                if( review.id === commentData.reviewId){
                    review.commentsList = review.commentsList.concat(commentData);
                }
                return review;
            })
            this.setState({
                reviewList : updateList
            })
        }).catch(error => console.log(error));
    }

    deleteComment( commentData ){
        CommentAPI.delete( commentData )
        .then( response => {
                console.log(response.data);
                console.log(commentData);
                console.log(this.state.reviewList);
                if (response.data.result === "SUCCESS") {
                    let updateList = this.state.reviewList.map( review =>{
                        if( review.id === commentData.reviewId){
                            review.commentsList = review.commentsList.filter( comment => (comment.id !== commentData.commentId));
                        }
                        return review;
                    })

                    console.log(updateList);
                    this.setState({
                        reviewList: updateList
                    });
    
                }
            }).catch(error => console.log(error));

    }

    likeReview( likeData ){
        LikeAPI.likeReview(likeData)
        .then( response =>{
            console.log( JSON.stringify( response.data , null , 4));

            let updateList = this.state.reviewList.map( review => {
                if( review.id === likeData.reviewId){
                    review.likesList = review.likesList.concat( 
                        {
                            id : response.data.likeId,
                            member : {
                                memberName : localStorage.getItem("authenticatedMember")
                            },
                        }
                    )
                }
                return review;
            })

            this.setState({
                reviewList : updateList
            })

        }).catch( error => console.log(error) );
    }

    unlikeReview( likeData ){

        LikeAPI.unlikeReview(likeData)
        .then( response =>{
            console.log( JSON.stringify( response.data , null ,4 ));
            let updateList = this.state.reviewList.map( review => {
                if( review.id === likeData.reviewId){
                    review.likesList = review.likesList.filter( like => ( like.id !== response.data.likeId));
                }
                return review;
            })

            this.setState({
                reviewList : updateList
            })

        }).catch( error => console.log(error) );
    }

    render() {
        return (
            <div className="container review-container start-margin">
                {this.state.reviewList.map(
                    (reviewData) => {
                        return <ReviewContent reviewData={reviewData} key={reviewData.id}
                                              modifyReview={this.modifyReview}
                                              deleteReview={this.deleteReview}
                                              addImage={this.addImage}
                                              addComment={this.addComment}
                                              deleteComment={this.deleteComment}
                                              likeReview={this.likeReview}
                                              unlikeReview={this.unlikeReview}
                        />;
                    }
                )}
            </div>
        )
    }
}