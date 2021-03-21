import React, { Component } from "react";
import SimpleImageSlider from "react-simple-image-slider";
import { Accordion, Button, Card } from "react-bootstrap";


import '../../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './review-box.css';

/*
    한곳에 구성을 다 넣자니 코드가 너무 길어지는거 같고
    그렇다고 파일로 나누어서 각 컴포넌트로 나누어 관리자하자니 파일이 많아져서
    어떻게 하는게 답을지 고민을 해봅시다...;;;
*/
function ReviewHeader(props) {

    return (
        <div className="card-header">
            <div className="row">
                <div className="col-1 contents-center">
                    <img className="member-thumnail" src="https://lh3.googleusercontent.com/proxy/DT2jxRM-rrBqkFZRzWz7nMFzsB-GLFuBD6Cs03KsMCLFJ_yVXNhl-BDxmzz2mjTaGebbIbfrOy1SyJcTlqOtN7GVG-g5TthYUP0CXxQ-PyqhCEIzMCY6yG1zbuLM_Cs25OLFCKHMHjlHexmByZI4dRL1qC4CookSUGWM8UnJ2mm3Ws-tNVr9RZwKD-H3Eg" />
                </div>
                <div className="col-9">
                    <div className="row">
                        <div className="col">{props.writerName}</div>
                    </div>
                    <div className="row">
                        <div className="col">{props.createDate}</div>
                    </div>
                </div>
                <div className="col-1 contents-center">
                    <button className="btn-col" onClick={() => { alert("아직이다 조금만 참아") }}>edit</button>
                </div>
            </div>

        </div>

    )
}


function ReviewBody(props) {

    const imageList = [
        { url: "data:image/jpeg;base64,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" },
        { url: "https://lh3.googleusercontent.com/proxy/oeTj5eqktk9GrQbaZXEBhAnVmHZWPmguEn4SzaA3GEntRDxGKuZz7IBHo2HNsGZRkoFmiVZdiNCgr3fAyyL4CGf_pcRIfZiduyPU_qNbxmJ51zvkoIJn" },
        { url: "https://lh3.googleusercontent.com/proxy/IrkvjuGxm9hzcAx1DLOLjqGx_GPTm4gPhFRbYbKU6m7ZDQBPmPOSxZ1Mfy8jKPEo-V6y_vJLc6u_jKZ7eKBkF5ls1ToastVvJVOWl3R64ym9j0NJsPHuh2amyQ12_CdzjubLX8NAAODVBPZkRnWr8UD-L-SLeqz9oiAmqU1RZ4qEVMXU-ljOTz2RkqKOxnXmLynRgw1PWF_91_ncmyJ7MEupuFKjtwFuxu9d8OhdHoljLe0ND3hRbvmW-F2L1H51wYIsTWl-2pfS7OzeHnCumx-M7oj6xf0nNaQF1wfR4ujnGlwSCyQ3W8IOI4rttCnomrbokA" },
        { url: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeV50NWxAaiA2ssMg3vboqRihk4RyTSyAXOQ&usqp=CAU" },
    ]

    return (

        <div className="card-body">
            <div className="review-content">
                <p>감상문이 적혀져있습니다.</p>
            </div>
            <div className="review-images">
                <SimpleImageSlider width={"90%"}
                    height={300} images={imageList} showNavs={true} showBullets={true} />
            </div>
            <div className="row">
                <div className="col review-like-btn">
                    <button className="btn btn-primary" onClick={ () => { alert("아직 좋아요가 안돼요.")}}>Like</button>
                </div>
                <div className="col">
                    <div className="review-like">좋아요 개수</div>
                </div>
            </div>
        </div>
    )
}


function ReviewFooter(props) {
    return (
        <div className="card-footer">
            <Accordion>
                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                    댓글보기
                        </Accordion.Toggle>
                <Accordion.Collapse eventKey="0">
                    <Card.Body>아직 미구현</Card.Body>
                </Accordion.Collapse>
            </Accordion>
        </div>
    )
}


export default class ReviewBox extends Component {

    render() {
        const writerName = "강현명";
        const createDate = "2001.2.2 13:43:23";
        return (
            <div className="container review-box">
                <div className="card">
                    <ReviewHeader writerName={writerName} createDate={createDate}></ReviewHeader>
                    <ReviewBody></ReviewBody>
                    <ReviewFooter></ReviewFooter>
                </div>
            </div>
        )
    }
}

