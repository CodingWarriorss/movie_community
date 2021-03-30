import axios from "axios";
import React, { Component } from "react";
import ReviewBox from './review_box/review-box';

import { REST_API_SERVER_URL } from '../constants/APIConstants';

export default class ReviewList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            movieTitle: localStorage.getItem('movieTitle') || '',
            reviewList: [],
            page: 0
        }
        this.loadReview = this.loadReview.bind(this);
        this.scrollCheck = this.scrollCheck.bind(this);
        this.loadReview();
    }

    componentDidMount() {
        console.log('review-list mounted');
        this.setState({
            movieTitle: this.props.movieTitle
        })
    }

    componentDidUpdate() {
        console.log('review-list update');
        if (this.state.movieTitle !== this.props.movieTitle) {
            this.setState({
                movieTitle: this.props.movieTitle,
                reviewList: [],
                page : 0
            });
            window.history.pushState(this.state, '', '/');
        }
        console.log(this.state.movieTitle);
    }

    loadReview() {
        // this.setState({
        //     reviewList: [...this.state.reviewList, ...dumyData]
        // })

        const requestUrl = REST_API_SERVER_URL + '/api/review'
        
        console.log("review_list");
        console.log(this.state.movieTitle);

        const token = localStorage.getItem("token");

        console.log(token);
        let config = {
            headers: {
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
                movieTitle : this.state.movieTitle
            }
        }

        // //요청 형태 프레임만 작성해둠.
        if (this.state.movieTitle === "") {
            axios.get(requestUrl, config)
                .then((response) => {
                    console.log(JSON.stringify(response.data, null, 4));
                    this.setState({
                        reviewList: [...this.state.reviewList, ...response.data.content],
                        page: (this.state.page + 1)
                    })
                }).catch((error) => {

                })
        } else {
            axios.get(requestUrl, configWithMovieTitle)
                .then((response) => {
                    console.log(JSON.stringify(response.data, null, 4));
                    this.setState({
                        movieTitle : this.state.movieTitle,
                        reviewList: [...this.state.reviewList, ...response.data.content],
                        page: (this.state.page + 1)
                    })
                }).catch((error) => {

                })
        }
    }

    //스크롤이 마지막에 있는지 체크
    scrollCheck() {

        /*
            document요소에 접근하여 스크롤의 위치를 체크
            body와 documentElement의 스크롤이 다를때도 있어서
            두개중 비교해서 사용한다고 합니다.
        */
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
        window.addEventListener("scroll", this.scrollCheck, true);
    }


    //테스트 용은 key가 유니크 하지 않아서 생기는 오류 메세지가 있으나 추후 수정예정
    render() {
        return (
            <div className="container start-margin">
                {this.state.reviewList.map(
                    (reviewData) => {
                        return <ReviewBox reviewData={reviewData} key={reviewData.reviewId} />;
                    }
                )}
            </div>
        )
    }
}