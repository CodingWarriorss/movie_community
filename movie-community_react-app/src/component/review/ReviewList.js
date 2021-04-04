import axios from "axios";
import React, { Component } from "react";

import ReviewContent from './review_box/ReviewContents';

import {REST_API_SERVER_URL} from '../constants/APIConstants';

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
        this.deleteReviewSet = this.deleteReviewSet.bind(this);

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
    
            // //요청 형태 프레임만 작성해둠.
            if (this.props.movieTitle === "") {
                axios.get(requestUrl, config)
                    .then((response) => {
                        console.log(JSON.stringify(response.data, null, 4));
                        this.setState({
                            reviewList: [...this.state.reviewList, ...response.data],
                            page: (this.state.page + 1)
                        })
                    }).catch((error) => {
    
                    })
            } else {
                axios.get(requestUrl, configWithMovieTitle)
                    .then((response) => {
                        console.log(JSON.stringify(response.data, null, 4));
                        this.setState({
                            reviewList: [...this.state.reviewList, ...response.data],
                            page: (this.state.page + 1)
                        })
                    }).catch((error) => {
    
                    })
            }
    
        }

    modifyReview = ( data ) => {
        console.log( JSON.stringify( data, null ,4 ));
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
        console.log( data );
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
                reviewList : this.state.reviewList.map( review => (review.id === data.reviewId) ? { ...review, ...data } : review ),
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

        console.log( JSON.stringify( data , null ,4 ))

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
        console.log( "update component!!!!!");
        console.log( JSON.stringify( this.state.reviewList , null, 4) );
    }

    render() {
        return (
            <div className="container start-margin">
                {this.state.reviewList.map(
                    (reviewData) => {
                        return <ReviewContent reviewData={reviewData} key={reviewData.reviewId}
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