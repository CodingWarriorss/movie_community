import React, {Component} from "react";
import dumyData from '../../test_data/review_test_data.json';
import ReviewBox from "./review_box/review-box";

export default class ReviewList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            reviewList: dumyData
        }

        this.loadReview = this.loadReview.bind(this);
        this.scrollCheck = this.scrollCheck.bind(this);
    }

    /*
        Review Data를 갱신하는 Method
        현재 Demo
        API와 연동하여 Review 데이터 로드로 수정
    */
    loadReview() {
        this.setState({
            reviewList: [...this.state.reviewList, ...dumyData]
        })
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

        if (scrollTop + clientHeight >= scrollHeight) {
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
                        return <ReviewBox reviewData={reviewData} key={reviewData.reviewId}/>;
                    }
                )}
            </div>
        )
    }
}