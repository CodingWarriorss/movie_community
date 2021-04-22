import React, {Component} from 'react'
import {Modal, Button} from "react-bootstrap";
import axios from "axios";
import {REST_API_SERVER_URL} from "../constants/APIConstants";
import AuthenticationService from "../login/AuthenticationService";

class DeleteMemberComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            close: false,
            password: ''
        }

        this.handleClose = this.handleClose.bind(this);
        this.withdraw = this.withdraw.bind(this);
        this.isWithdrawPossible = this.isWithdrawPossible.bind(this);
    }

    isWithdrawPossible() {
        const {password} = this.state
        const memberName = localStorage.getItem('authenticatedMember');

        axios.post(REST_API_SERVER_URL + '/login', {memberName, password})
            .then(response => {
                if (response.headers['authorization'].startsWith('Bearer')) {
                    this.withdraw();
                } else {
                    alert('비밀번호가 틀렸습니다.');
                }
            })
    }

    withdraw() {
        const config = {
            headers: {
                'Authorization': "Bearer " +localStorage.getItem('token')
            }
        }

        axios.delete(REST_API_SERVER_URL + '/api/member', config)
            .then(function (response) {
                console.log(response.data);
                if (response.data === 1) {
                    alert('회원 정보가 삭제되었습니다.');
                    AuthenticationService.logout();
                } else {
                    alert('비밀번호가 틀렸습니다.');
                }
            })
    }

    changeHandler = (e) => {
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        )
    }

    handleClose() {
        this.setState({
            close: true
        });
    }

    render() {
        const {
            isOpen, close,
        } = this.props;

        return (
            <Modal
                size="xs"
                show={isOpen}
                onHide={close}>
                <Modal.Header closeButton>회원 탈퇴</Modal.Header>

                <Modal.Body>
                    <div className="form-group">
                        <label>비밀번호</label>
                        <input type="password" className="form-control" placeholder="비밀번호"
                               name="password" value={this.state.password} onChange={this.changeHandler}/>
                    </div>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={close}>
                        취소
                    </Button>
                    <Button variant="danger" onClick={this.isWithdrawPossible}>
                        탈퇴
                    </Button>
                </Modal.Footer>
            </Modal>
        )
    }
}

export default DeleteMemberComponent