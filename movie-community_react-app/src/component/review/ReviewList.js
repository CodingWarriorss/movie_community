import axios from "axios";
import React, { Component } from "react";

import ReviewContent from './review_box/ReviewContents';

import {REST_API_SERVER_URL} from '../constants/APIConstants';

import "./ReviewList.css";

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

    }

    /*
        Review Data를 갱신하는 Method
    */
        loadReview() {
            const requestUrl = REST_API_SERVER_URL + '/api/review'
            const token = localStorage.getItem("token");
    
            let config = {
                headers : {
                    'Authorization': 'Bearer ' + token
                },
                params : {
                    pageIndex : this.state.page
                }
            }
    
            let configWithMovieTitle = {
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                params : {
                    pageIndex : this.state.page,
                    movieTitle : this.props.movieTitle
                }
            }
    
            if (this.props.movieTitle === "") {
                axios.get(requestUrl, config)
                    .then((response) => {
                        this.setState({
                            reviewList: [...this.state.reviewList, ...response.data],
                            page: (this.state.page + 1)
                        })
                    }).catch((error) => {
    
                    })
            } else {
                axios.get(requestUrl, configWithMovieTitle)
                    .then((response) => {
                        this.setState({
                            reviewList: [...this.state.reviewList, ...response.data],
                            page: (this.state.page + 1)
                        })
                    }).catch((error) => {
    
                    })
            }
    
        }

    modifyReview = ( data ) => {
        const requestUrl = REST_API_SERVER_URL+ '/api/review' ;
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        let formData = new FormData()
        formData.append("reviewId" , data.reviewId);
        formData.append("content" , data.content);

        axios.put( requestUrl , formData, config)
        .then( response => {
            this.setState({
                reviewList : this.state.reviewList.map( review => (review.id === data.reviewId) ? { ...review, ...data } : review ),
            })
        }).catch( error => console.log( error ));
    }

    addImage = ( data ) => {
        const requestUrl = REST_API_SERVER_URL+ '/api/review' ;
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        let formData = new FormData()
        formData.append("reviewId" , data.reviewId);
        formData.append("content" , data.content );
        data.imageList.forEach( imageFile => {
            formData.append("newFiles" , imageFile );
        });

        axios.put( requestUrl , formData, config)
        .then( response => {
            this.setState({
                // reviewList : this.state.reviewList.map( review => (review.id === data.reviewId) ? { ...review, ...data } : review ),
            })
        }).catch( error => console.log( error ));
    }

    deleteReview = ( data ) => {
        const requestUrl = REST_API_SERVER_URL+ '/api/review' ;
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            },
            params :{
                reviewId: data.reviewId
            }
        }


        axios.delete( requestUrl, config)
        .then( response => {
            this.deleteReviewSet(data.reviewId);
        }).catch( error => console.log( error ));
    }

    deleteReviewSet = ( id ) => {
        this.setState({
            reviewList : this.state.reviewList.filter( review => ( review.id !== id )),
        })
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

    render() {
        return (
            <div className="container review-container start-margin">
                {this.state.reviewList.map(
                    (reviewData) => {
                        return <ReviewContent reviewData={reviewData} key={reviewData.id}
                            modifyReview={this.modifyReview}
                            deleteReview={this.deleteReview}
                            addImage={this.addImage}
                        />;
                    }
                )}
            </div>
        )
    }
}