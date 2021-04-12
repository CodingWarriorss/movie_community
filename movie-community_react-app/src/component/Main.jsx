import React, {Component} from 'react'
import ReviewList from './review/ReviewList';
import "./MainTest.css";
import RankingComponent from "component/ranking/RankingComponent";
import { Redirect } from 'react-router';

class MainTest extends Component {
    constructor(props) {
        super(props);
        this.child = React.createRef();

        this.state = {
            movieTitle : ''
        }
    }

    componentDidUpdate() {
        console.log("I have updated");
        if (this.state.movieTitle !== this.props.movieTitle) {
            this.setState({
                movieTitle : this.props.movieTitle
            })
        }
        console.log(this.state.movieTitle);
    }
    
    render() {
        if( !(localStorage.getItem("token")) ) return <Redirect to="/login"></Redirect>
        return (
            <div className="main-container">
                <ReviewList movieTitle = {this.state.movieTitle}/>
                <RankingComponent></RankingComponent>
            </div>
        )
    }
}

export default MainTest