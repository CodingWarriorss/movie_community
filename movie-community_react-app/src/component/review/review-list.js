import axios from "axios";
import React, {Component} from "react";
import SearchbarComponent from "../searchbar/SearchbarComponent";
import {Modal, Button, Form} from 'react-bootstrap';

import ReviewBox from './review_box/review-box';

export default class ReviewList extends Component {
    
    render(){

        const testList = [ 1,2,3,4,5];

        return (
            <div className="container">
                {testList.map(
                    (value) => <ReviewBox></ReviewBox>
                )}
               
            </div>
        )
    }
}