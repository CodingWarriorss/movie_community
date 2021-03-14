import axios from "axios";
import React, {Component} from "react";

import { Col, Container, Row } from 'react-bootstrap'
import "./main.css"

export default class Main extends Component {

    render(){
        return (
            <Container>
                <Row>
                    <Col sm={8} className="review-area"  >
                        Review Contents List.
                    </Col>
                    <Col sm={4} className="review-area">여기는 아무것도 안보일 예정</Col>
                </Row>
            </Container>
        )
    }

}
