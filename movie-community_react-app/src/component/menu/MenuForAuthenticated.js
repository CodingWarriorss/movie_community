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
            {/*테스트용 프로필 버튼*/}
            <li className="nav-item">
                <button style={{border: "none", backgroundColor: "white"}}>
                    <img
                        alt="https://user-images.githubusercontent.com/62331803/111857083-870d3980-8972-11eb-8f75-fa10bb859997.png"
                        src="https://user-images.githubusercontent.com/62331803/111857083-870d3980-8972-11eb-8f75-fa10bb859997.png"
                        width={40} height={40} style={{borderRadius: 50}}/>
                </button>
            </li>
        </ul>
    )
};

export default Menu;