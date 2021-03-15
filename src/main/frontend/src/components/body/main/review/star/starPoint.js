import React from 'react';
import './starPoints.css';

export default class StarPoints extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            starStyle: {
                width: "50%",
            }
        }

        this.starClick = this.starClick.bind(this);
    }
    starClick(e) {
        e.preventDefault()
        let clickedPoint = e.clientX - e.target.offsetLeft;
        let rating = parseInt(clickedPoint / (205 / 10) + 1);
        let starWidth = rating * 10;
        console.log("평점은 " + rating + "점 입니다.");

        this.setState((state) => {
            return { starStyle: { width: starWidth + "% " } }
        }
        );
    }


    render() {
        const tempStyle = {
            width: "30%"
        }
        return (
            <div className="wrap-star">
                <div className='star-rating' onClick={this.starClick}>
                    <span style={this.state.starStyle}></span>
                </div>
            </div>

        )
    }
}

