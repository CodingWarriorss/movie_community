import React, { useState } from 'react';
import ReviewEditBox from "../review/ReviewEditBox";
import LogoutComponent from "../login/LogoutComponent";
import HeaderSearchComponent from './search/HeaderSearchComponent';
import {AddCircle} from "@material-ui/icons";

const Menu = (props) => {
    const [isShow, setShow] = useState(false);

    const handleShow = () => {
        setShow(!isShow);
    }

    const movieTitleSelected = (data) => {
        props.getMovieTitle(data);
    }

    return (
        <div className="navbar-nav d-flex align-items-ceter justify-content-end w-100 mr-2">
            <ul className="navbar-nav">
                <HeaderSearchComponent movieTitleSelected = {movieTitleSelected}/>
                <li className="nav-item" style={{marginRight : 5}}>
                    <LogoutComponent/>
                </li>
                <li className="nav-item">
                    <AddCircle onClick={handleShow} style={{
                        fontSize : 46,
                        color : '#009688',
                    }}/>
                    <ReviewEditBox handleShow={handleShow} isShow={isShow} isModify={false} />
                </li>
                {/*테스트용 프로필 버튼*/}
                <li className="nav-item">
                    <button style={{ border: "none", backgroundColor: "white" }}>
                        <img
                            alt="https://user-images.githubusercontent.com/62331803/111857083-870d3980-8972-11eb-8f75-fa10bb859997.png"
                            src="https://user-images.githubusercontent.com/62331803/111857083-870d3980-8972-11eb-8f75-fa10bb859997.png"
                            width={40} height={40} style={{ borderRadius: 50 }} />
                    </button>
                </li>
            </ul>
        </div>
    )
};

export default Menu;