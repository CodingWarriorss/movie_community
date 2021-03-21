import React, {Component} from "react";
import '../css/StarBox.css';

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
                            <span className="blind">0.5</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={1} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">1</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={1.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">1.5</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={2} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">2</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={2.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">2.5</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={3} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">3</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={3.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">3.5</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={4} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">4</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={4.5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">4.5</span>
                        </span>
                    </label>
                    <label className="startRadio__box">
                        <input type="radio" name="star" value={5} onChange={this.changeHandler}/>
                        <span className="startRadio__img">
                            <span className="blind">5</span>
                        </span>
                    </label>
                </div>
                {/*<h2> 평점 : {this.state.star} </h2>*/}
            </>
        )
    }
}

export default StarBoxComponent