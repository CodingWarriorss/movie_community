import React from 'react';
import { Link } from "react-router-dom";

const Menu = () => {
    return (
        <ul className="navbar-nav ml-auto">
            <li className="nav-item">
                <Link className="nav-link" to={"/login"}>로그인</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to={"/signup"}>회원가입</Link>
            </li>
            <li>
            <a className="btn btn-block social-btn google" href={'http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:3000/'}>구글로그인</a>
            </li>
        </ul>
    )
};

export default Menu;