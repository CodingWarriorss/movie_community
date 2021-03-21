import React, {Component} from 'react'
import ReviewList from './review/review-list';

class MainTest extends Component {

    render() {
        const access = localStorage.getItem('token');
        // return access == null ? <h1> 로그인 안된 상태 </h1> : <h1>로그인 된 상태</h1>
        return (
            <div className="test">

                <ReviewList></ReviewList>

            </div>
        )
    }
}

export default MainTest