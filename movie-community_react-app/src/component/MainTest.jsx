import React, {Component} from 'react'
import ReviewList from './review/review-list';
import "./MainTest.css";
import RankingComponent from "component/ranking/RankingComponent";

class MainTest extends Component {
    constructor(props) {
        super(props);
        this.child = React.createRef();

        this.state = {
            movieTitle : ''
        }
    }

    componentDidMount() {
        console.log("I have Mounted");
        console.log(this.state.movieTitle);
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
        const access = localStorage.getItem('token');
        console.log("MainTest");
        console.log(this.props.movieTitle);
        
        return (
            <div>
                <ReviewList movieTitle = {this.state.movieTitle}/>
                <RankingComponent></RankingComponent>
            </div>
        )
    }
}

export default MainTest