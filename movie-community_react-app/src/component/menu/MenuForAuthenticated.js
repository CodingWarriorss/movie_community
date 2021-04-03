import React, { useState } from 'react';
import ReviewEditBox from "../review/ReviewEditBox";
import LogoutComponent from "../login/LogoutComponent";
import HeaderSearchComponent from './search/HeaderSearchComponent';
import {Link} from "react-router-dom";

const Menu = (props) => {
    const [isShow, setShow] = useState(false);

    const handleShow = () => {
        setShow(!isShow);
    }

    const movieTitleSelected = (data) => {
        console.log(data);
        props.getMovieTitle(data);
    }

    return (
        <div className="navbar-nav d-flex align-items-ceter justify-content-end w-100 mr-2">
            <ul className="navbar-nav">
                <HeaderSearchComponent movieTitleSelected = {movieTitleSelected}/>
                <li className="nav-item">
                    <LogoutComponent/>
                </li>
                <li className="nav-item">
                    <button className="btn btn-primary" onClick={handleShow}>
                        +
                    </button>
                    <ReviewEditBox handleShow={handleShow} isShow={isShow} isModify={false} />
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to={"/mypage"}>
                        <img
                            src={localStorage.getItem('profileImg')}
                            width={40} height={40} style={{ borderRadius: 50 }} />
                    </Link>
                </li>
            </ul>
        </div>
    )
};

export default Menu;