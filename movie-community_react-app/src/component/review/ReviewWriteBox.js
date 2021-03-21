import axios from "axios";
import React, {Component} from "react";
import SearchbarComponent from "./searchbar/SearchbarComponent";
import {Modal, Button, Form} from 'react-bootstrap';
import StarBoxComponent from "./StarBoxComponent";

export default class ReviewWriteBox extends Component {

    constructor(props) {
        super(props);
        this.state = {
            show: false,
            reviewData: {
                movieTitle: null,
                comment: null,
                rating: null,
                imgFiles: null,
            },
            title: '',
            rating: 0,
        }

        //Method binding
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleSummit = this.handleSummit.bind(this);
        this.setMovieTitle = this.setMovieTitle.bind(this);

        //Input Value
        this.movieTitle = React.createRef();
        this.rating = React.createRef();
        this.comment = React.createRef();
        this.fileInput = React.createRef();
    }

    // 자식 컴포넌트로부터 영화명 수신 (확인 완료)
    setMovieTitle = (selectedMovieTitle) => {
        this.setState({
            title: selectedMovieTitle,
        })
    }

    setStar = (star) => {
        this.setState(
            {
                rating: star
            }
        )
    }

    handleClose(e) {
        this.setState({show: false});
    }

    handleShow(e) {
        this.setState({show: true});
    }

    handleSummit(e) {
        const formData = new FormData();
        formData.append("movieTitle", this.state.title);
        formData.append("content", this.comment.current.value);

        let files = [];
        for (let i = 0; i < this.fileInput.current.files.length; i++) {
            files.push(this.fileInput.current.files[i]);
        }
        formData.append("file", this.fileInput.current.files[0]);
        formData.append("rating", this.state.rating);

        const token = localStorage.getItem("token");

        for (let i = 0; i < localStorage.length; i++) {
            console.log(localStorage.key(i) + " : " + localStorage.getItem(localStorage.key(i)));
        }

        let config = {
            headers: {
                'Content-Type': "multipart/form-data",
                'Authorization': 'Bearer ' + token,
            }
        }

        axios.post(`http://localhost:8080/api/review`, formData, config)
            .then(response => {
                alert("리뷰등록 성공(Demo)");
                this.handleClose();
                // console.log('response', JSON.stringify(response, null, 2))
            })
            .catch(error => {
                console.log('failed', error)
            })

    }

    render() {
        return (
            <>
                <button className="btn btn-primary" onClick={this.handleShow}>
                    +
                </button>
                <Modal show={this.state.show} onHide={this.handleClose}>
                    {/*header*/}
                    <Modal.Header closeButton style={this.state.style}>
                    {/*    <Form.Group controlId="reviewTitle">*/}
                        {/*</Form.Group>*/}
                    </Modal.Header>
                    
                    {/*body*/}
                    <Modal.Body style={this.state.style}>
                        {/*검색바*/}
                        <SearchbarComponent callbackFromParent={this.setMovieTitle}/>
                        <Form>
                            {/*평점 라디오 버튼*/}
                            <Form.Group controlId="reviewRating">
                                <StarBoxComponent callbackFromParent={this.setStar}/>
                            </Form.Group>
                            <Form.Group controlId="reviewContent">
                                <Form.Control as="textarea" placeholder="관람하신 영화는 어떠셨나요?" rows={3} ref={this.comment}/>
                            </Form.Group>
                            <Form.Group controlId="reviewImgFile">
                                <input type="file" multiple className="reviewImage" ref={this.fileInput}/>
                            </Form.Group>
                        </Form>
                    </Modal.Body>

                    {/*footer*/}
                    <Modal.Footer style={this.state.style}>
                        <Button variant="secondary" onClick={this.handleClose}>
                            취소
                        </Button>
                        <Button variant="primary" onClick={this.handleSummit}>
                            등록
                        </Button>
                    </Modal.Footer>
                </Modal>

            </>
        )
    }

}
