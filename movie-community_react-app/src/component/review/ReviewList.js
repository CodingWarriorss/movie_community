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

    }

    /*
        Review Data를 갱신하는 Method
    */
    loadReview() {
        const requestUrl = REST_API_SERVER_URL+ '/api/review' ;
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            },
            params :{
                pageIndex : this.state.page
            }
        }

        axios.get( requestUrl, config)
        .then( (response) => {
            console.log( JSON.stringify( response.data , null , 4) );
            this.setState({
                reviewList: [...this.state.reviewList, ...response.data],
                page : (this.state.page +1)
            })
        }).catch( (error) => {

        })
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

        axios.put( requestUrl , data, config)
        .then( response => {
            this.setState({
                reviewList : this.state.reviewList.map( review => (review.id === data.reviewId) ? { ...review, ...data } : review ),
            })
            console.log( JSON.stringify( this.state , null , 4 ));
        }).catch( error => console.log( error ));
    }

    addImage = ( data ) => {
        console.log( JSON.stringify( data, null ,4 ));
    }

    deleteReview = ( data ) => {
        console.log( JSON.stringify( data, null ,4 ));
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
        this.loadReview();
        window.addEventListener("scroll", this.scrollCheck, true);
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