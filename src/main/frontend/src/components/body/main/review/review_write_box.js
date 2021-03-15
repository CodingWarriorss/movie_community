import axios from "axios";
import React, { Component } from "react";

import { Modal, Button, Form } from 'react-bootstrap';

export default class ReviewWriteBox extends Component {

    constructor(props){
        super(props);
        this.state = {
            show: false,
        }

        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
    }

    handleClose(e){
        this.setState( {show:false});
    }

    handleShow(e){
        this.setState( {show:true});
    }


    render() {
        return (
            <>
            <button onClick={this.handleShow}>
                <i class="fas fa-plus"></i>
            </button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Modal heading</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="reviewTitle">
                            <Form.Label>제목</Form.Label>
                            <Form.Control type="text" placeholder="title" />
                        </Form.Group>
    
                        <Form.Group controlId="reviewMovieSearch">
                            <Form.Label>Contents</Form.Label>
                            <Form.Control as="text" />
                        </Form.Group>
    
                        <Form.Group controlId="reviewContent">
                            <Form.Label>Contents</Form.Label>
                            <Form.Control as="textarea" rows={3} />
                        </Form.Group>
                        <Form.Group controlId="reviewImgFile">
                            <Form.File id="exampleFormControlFile1" label="insert Image" />
                        </Form.Group>
                        <Button variant="secondary" onClick={this.handleClose}>
                            취소
                            </Button>
                        <Button variant="primary" onClick={this.handleClose}>
                            등록
                        </Button>
                    </Form>
    
                </Modal.Body>
            </Modal>
    
            </>
        )
    }

}
