import axios from "axios";
import React, { Component } from "react";

import { Modal, Button, Form } from 'react-bootstrap';
import StarPoints from "./star/starPoint";

export default class ReviewWriteBox extends Component {

    constructor(props) {
        super(props);
        this.state = {
            show: false,
            reviewData: {
                movieTitle: null,
                comment : null,
                rating: null,
                imgFiles : null,
            }
        }

        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);

        this.handleSummit = this.handleSummit.bind(this);
        this.fileChange = this.fileChange.bind(this);
    }

    handleClose(e) {
        this.setState({ show: false });
    }

    handleShow(e) {
        this.setState({ show: true });
    }

    handleSummit(e){
        const formData = new FormData();
        formData.append("movieTitle" , "임시");
        formData.append("comment" , "아무거나");
        formData.append("imgFiles" , this.state.reviewData.imgFiles );
        formData.append("rating" , 10+"");

        console.log( formData );

        axios.post(`http://localhost:8080/api/review`, 
        { 
            headers: { 'Content-type': 'multipart/form-data', },
            data : formData
        }
        ).then(response => { console.log('response', JSON.stringify(response, null, 2)) }).catch(error => { console.log('failed', error) })

    }

    fileChange(e){
        const files = e.target.files;
        this.setState({ reviewData : {
            imgFiles : files
        }});
    }

    reviewRegist(e) {
        const formData = new FormData();
        formData.append("movieTitle" , "임시");
        formData.append("comment" , "아무거나");
        formData.append("imgFiles" , this.state.reviewData.imgFiles );
        formData.append("rating" , 10+"");

        console.log( formData );
        axios.post(`http://localhost:8080/api/review`, 
        { 
            headers: { 'Content-type': 'multipart/form-data', },
            data : formData
        }
        ).then(response => { console.log('response', JSON.stringify(response, null, 2)) }).catch(error => { console.log('failed', error) })

    }


    render() {
        return (
            <>
                <button className="btn btn-primary" onClick={this.handleShow}>
                    글쓰기
                </button>
                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Form.Group controlId="reviewTitle">
                            <Form.Control type="text" placeholder="영화" ref={this.movieTitle} />
                        </Form.Group>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="reviewMovieSearch">
                                <Form.Label>Contents</Form.Label>
                                <Form.Control as="textarea" />
                            </Form.Group>
                            <Form.Group controlId="reviewContent">

                            </Form.Group>
                            <Form.Group controlId="reviewContent">
                                <Form.Label>Contents</Form.Label>
                                <Form.Control as="textarea" rows={3} />
                            </Form.Group>
                            <Form.Group controlId="reviewImgFile">
                                <input type="file" multiple className="reviewImage" ref={this.fileInput} onChange={this.fileChange} />
                            </Form.Group>
                            <Button variant="secondary" onClick={this.handleClose}>
                                취소
                            </Button>
                            <Button variant="primary" onClick={this.handleSummit}>
                                등록
                        </Button>
                        </Form>

                    </Modal.Body>
                </Modal>

            </>
        )
    }

}
