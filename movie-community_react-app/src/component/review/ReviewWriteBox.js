import axios from "axios";
import React, {Component} from "react";
import SearchbarComponent from "./searchbar/SearchbarComponent";
import {Modal, Button, Form} from 'react-bootstrap';
import StarBoxComponent from "./StarBoxComponent";
import ImageUploader from "react-images-upload";

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
            memberName: localStorage.getItem('authenticatedMember'),
            pictures: [],
        }

        //Method binding
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleSummit = this.handleSummit.bind(this);
        this.setMovieTitle = this.setMovieTitle.bind(this);
        this.onDrop = this.onDrop.bind(this);

        //Input Value
        this.movieTitle = React.createRef();
        this.rating = React.createRef();
        this.comment = React.createRef();
        this.fileInput = React.createRef();
    }

    // 업로드 이미지 세팅
    onDrop(pictureFiles) {
        this.setState({
            pictures: this.state.pictures.concat(pictureFiles)
        });
    }

    // 자식 컴포넌트로부터 영화명 수신
    setMovieTitle = (selectedMovieTitle) => {
        this.setState({
            title: selectedMovieTitle,
        })
    }

    // 평점 세팅
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
        // formdata 세팅
        const formData = new FormData();
        formData.append("movieTitle", this.state.title); // 영화 제목
        formData.append("content", this.comment.current.value); // 작성글
        formData.append("file", this.state.pictures[0]); // 이미지 (현재 1개만 가능)
        formData.append("rating", this.state.rating); // 별점

        // cofig 세팅
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Content-Type': "multipart/form-data",
                'Authorization': 'Bearer ' + token,
            }
        }

        // 전송
        axios.post(`http://localhost:8080/api/review`, formData, config)
            .then(response => {
                alert("게시물이 성공적으로 등록되었습니다.");
                this.handleClose();
            })
            .catch(error => {
                console.log('게시물 등록에 실패하였습니다.', error)
            })
    }

    render() {
        return (
            <>
                <button className="btn btn-primary" onClick={this.handleShow}>
                    +
                </button>
                <Modal
                    size="lg"
                    show={this.state.show}
                    onHide={this.handleClose}>
                    {/*header*/}
                    <Modal.Header closeButton style={this.state.style}/>

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
                                <Form.Control as="textarea" placeholder={this.state.memberName + "님 관람하신 영화는 어떠셨나요?"}
                                              rows={8} ref={this.comment}/>
                            </Form.Group>
                            {/*이미지 업로드*/}
                            <ImageUploader
                                withIcon={true}
                                withPreview={true}
                                buttonText="사진 추가"
                                onChange={this.onDrop}
                                imgExtension={[".jpg", ".gif", ".png", ".gif"]}
                                maxFileSize={5242880}
                            />
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
