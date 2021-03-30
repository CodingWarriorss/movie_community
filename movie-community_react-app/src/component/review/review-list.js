import axios from "axios";
import React, { Component } from "react";

import ReviewContens from './review_box/ReviewContents';

import { REST_API_SERVER_URL } from '../constants/APIConstants';

export default class ReviewList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            movieTitle: '',
            reviewList: [],
            page: 0
        }
        this.loadReview = this.loadReview.bind(this);
        this.scrollCheck = this.scrollCheck.bind(this);
    }

    loadReview() {
        const requestUrl = REST_API_SERVER_URL + '/api/review'
        
        const token = localStorage.getItem("token");

        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            },
            params : {
                pageIndex : this.state.page
            }
        }


        // //요청 형태 프레임만 작성해둠.
        if (this.state.movieTitle === "") {
            
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
            config['params']['movieTitle'] = this.state.movieTitle
            axios.get(requestUrl, config)
                .then((response) => {
                    console.log(JSON.stringify(response.data, null, 4));
                    this.setState({
                        movieTitle : this.state.movieTitle,
                        reviewList: [...response.data.content],
                        page: (this.state.page + 1)
                    })
                }).catch((error) => {

                })
        }
    }

    //스크롤이 마지막에 있는지 체크
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

        if (parseInt(scrollTop + clientHeight) + 1 >= scrollHeight) {
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
                        return <ReviewContens reviewData={reviewData} key={reviewData.reviewId} />;
                    }
                )}
            </div>
        )
    }
}