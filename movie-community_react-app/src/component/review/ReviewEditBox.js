import axios from "axios";
import React, {Component} from "react";
import SearchbarComponent from "./searchbar/SearchbarComponent";
import {Modal, Button, Form} from 'react-bootstrap';
import StarBoxComponent from "./StarBoxComponent";
import ImageUploader from "react-images-upload";

export default class ReviewEditBox extends Component {

    //boolean -> props.isModify로 수정작업인지 체크

    constructor(props) {
        super(props);
        this.state = {
            show: false,
            title: '', // 영화 제목
            rating: 0, // 별점
            pictures: [], // 업로드한 이미지
            memberName: localStorage.getItem('authenticatedMember'), // 유저 아이디
        }

        //Method binding
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleSummit = this.handleSummit.bind(this);
        this.setMovieTitle = this.setMovieTitle.bind(this);
        this.onDrop = this.onDrop.bind(this);
        this.isSummitPossible = this.isSummitPossible.bind(this);

        //Input Value
        // this.movieTitle = React.createRef();
        // this.rating = React.createRef();
        this.comment = React.createRef();
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

    // 평점 세팅 (자식에서 number로 변환해도 string으로 세팅되기 때문에 여기서 number로 변환하여 저장.)
    setStar = (star) => {
        this.setState({
                rating: parseInt(star)
            })
    }

    isSummitPossible() {
        if (!this.state.title) {
            alert('리뷰할 영화를 선택해주세요.');
            return false;
        }
        if (!this.comment.current.value) {
            alert('리뷰 내용을 입력해주세요.');
            return false;
        }
        if (this.state.rating === 0) {
            alert('영화 평점을 선택해주세요.');
            return false;
        }
        return true;
    }

    handleClose(e) {
        this.setState({show: false});
    }

    handleShow(e) {
        this.setState({show: true});
    }

    handleSummit(e) {
        if (!this.isSummitPossible()) {
            return;
        }

        // formdata 세팅
        const formData = new FormData();
        formData.append("movieTitle", this.state.title); // 영화 제목
        formData.append("content", this.comment.current.value); // 작성글
        formData.append("rating", parseInt( this.state.rating ) ); // 별점

        // 이미지 존재여부 확인
        let url = 'http://localhost:8080/api/review';
        const pictures = this.state.pictures;
        if (pictures.length > 0) {
            pictures.forEach(picture => {
                formData.append("files", picture);
            })
        }

        // cofig 세팅
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Content-Type': "multipart/form-data",
                'Authorization': 'Bearer ' + token,
            }
        }

        // 전송
        axios.post(url, formData, config)
            .then(response => {
                alert("게시물이 성공적으로 등록되었습니다.");
                this.props.handleShow();
            })
            .catch(error => {
                console.log('게시물 등록에 실패하였습니다.', error)
            })
    }

    render() {

        if( this.props.isModify ) console.log("수정모드");
        else console.log("등록모드");

        const testText = ( this.props.isModify ) ? "수정모드" : "등록모드";
        
        return (
                <Modal
                    size="lg"
                    show={this.props.isShow}
                    onHide={this.props.handleShow}>
                    {/*header*/}
                    <Modal.Header closeButton style={this.state.style}>
                            <div>{testText}</div>
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
                        <Button variant="secondary" onClick={this.props.handleShow}>
                            취소
                        </Button>
                        <Button variant="primary" onClick={this.handleSummit}>
                            등록
                        </Button>
                    </Modal.Footer>
                </Modal>
        )
    }

}
