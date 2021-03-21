import React, {Component} from "react";
import '../css/StarBox.css';
import {Form} from "react-bootstrap";

class StarBoxComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            star: 0,
        }

        this.changeHandler = this.changeHandler.bind(this);
    }

    changeHandler = (e) => {
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        )
        this.props.callbackFromParent(e.target.value);
    }

    render() {
        return (
            <>
                <div className="startRadio">
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={0.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={1} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={1.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={2} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={2.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={3} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={3.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind"></span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={4} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">별 4.5개</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={4.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">별 5개</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">별 5.5개</span>
                        </span>
                    </label>
                </div>
                {/*<h2> 평점 : {this.state.star} </h2>*/}
            </>
        )
    }
}

export default StarBoxComponent