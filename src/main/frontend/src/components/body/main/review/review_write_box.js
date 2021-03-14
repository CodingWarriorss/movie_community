import axios from "axios";
import React, { Component, useState } from "react";

import { Modal, Button } from 'react-bootstrap';

export default function ReviewWriteBox() {

    const [ show , setShow ] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <Modal show={show} onHide={handleClose}>
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
                    <Button variant="secondary" onClick={handleClose}>
                        취소
                        </Button>
                    <Button variant="primary" onClick={handleClose}>
                        등록
                        </Button>
                </Form>

            </Modal.Body>
        </Modal>
    

}
