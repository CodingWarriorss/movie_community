import axios from "axios";
import React, { Component } from "react";
import ReviewContents from './review_box/ReviewContents';

import { REST_API_SERVER_URL } from '../constants/APIConstants';

export default class ReviewList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            reviewList: [],
            page: 0
        }

        this.loadReview = this.loadReview.bind(this);
        this.scrollCheck = this.scrollCheck.bind(this);
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

        if (parseInt(scrollTop + clientHeight) + 10 >= scrollHeight) {
            this.loadReview();
        }
    }



    //테스트 용은 key가 유니크 하지 않아서 생기는 오류 메세지가 있으나 추후 수정예정
    render() {
        return (
            <div className="container start-margin">
                {this.state.reviewList.map(
                    (reviewData) => {
                        return <ReviewContents reviewData={reviewData} key={reviewData.reviewId} />;
                    }
                )}
            </div>
        )
    }
}