import React, { Component } from 'react'
import ReviewList from './review/review-list';
import "./MainTest.css";

class MainTest extends Component {

    render() {
        const access = localStorage.getItem('token');
        // return access == null ? <h1> 로그인 안된 상태 </h1> : <h1>로그인 된 상태</h1>
        return (
            <div className="test">
                <div className="row" style={ { marginTop: "40px"}}>

                    <div className="col">
test
                    </div>
                    <div className="col">
                        <ReviewList />
                    </div>
                    <div className="col">
test2 
<div className="box-office-area">
dksdflkasndklfjhasrkjghadklrjghlakdfjglkadj
</div>
                    </div>

                </div>

            </div>
        )
    }
}

export default MainTest