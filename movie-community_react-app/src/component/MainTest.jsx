import React, {Component} from 'react'
import { ThemeConsumer } from 'react-bootstrap/esm/ThemeProvider';
import ReviewList from './review/review-list';
import "./MainTest.css";

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
        
        // return access == null ? <h1> 로그인 안된 상태 </h1> : <h1>로그인 된 상태</h1>
        return (
            <div className="test">
                <ReviewList movieTitle = {this.state.movieTitle}/>
            </div>
        )
    }
}

export default MainTest