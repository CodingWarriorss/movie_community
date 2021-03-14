import { Component, useState } from "react";
import { Link } from "react-router-dom";
import { Button, Modal, Form } from "react-bootstrap";
import ReviewWriteBox from "../body/main/review/review_write_box";

function Example() {
    /*
    useState는 함수형 컴포넌트에서 state를 사용가능하게 함.
    useState의 인자는 state의 초기값. 그리고 쌍으로 출력값이 주어지는데
    뒤에는 앞의 값을 변화시킬수 있는 함수를 반환한다.
    */
    const [show, setShow] = useState(false);    

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Button variant="primary" onClick={handleShow}>
                글쓰기
            </Button>

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
                            <Form.Control as="text"/>
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
        </>
    );
}



export default class MovieNavi extends Component {
    renderModal() {
        return <ReviewWriteBox></ReviewWriteBox>;
    }

    render() {
        return (
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to={"/api/members/login"}>로그인</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to={"/api/members/join"}>회원 가입</Link>
                    </li>

                    <li>
                        <Example></Example>
                    </li>

                </ul>
            </div>
        )
    }
}