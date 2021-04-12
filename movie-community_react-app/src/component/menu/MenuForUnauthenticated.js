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
        </ul>
    )
};

export default Menu;