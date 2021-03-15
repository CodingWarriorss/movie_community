import { Component, useState } from "react";
import { Link } from "react-router-dom";
import ReviewWriteBox from "../body/main/review/review_write_box";

export default class MovieNavi extends Component {
    renderModal() {
        return <ReviewWriteBox></ReviewWriteBox>;
    }

    render() {
        return (
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to={"/api/members/login"}>로그인</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to={"/api/members/join"}>회원 가입</Link>
                    </li>

                    <li>
                        <ReviewWriteBox></ReviewWriteBox>
                    </li>

                </ul>
            </div>
        )
    }
}