import React from 'react';
import ReviewWriteBox from "../review/ReviewWriteBox";
import LogoutComponent from "../login/LogoutComponent";

const Menu = () => {
    return (
        <ul className="navbar-nav ml-auto">
            <li className="nav-item">
                <LogoutComponent/>
            </li>
            <li className="nav-item">
                <ReviewWriteBox/>
            </li>
        </ul>
    )
};

export default Menu;