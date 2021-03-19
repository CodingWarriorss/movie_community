import React, {Component} from "react";
import '../css/SearchBar.css'
import SearchService from "./SearchService";

// 영화명 입력 후 배열로 받아옴.
// 프리뷰 컴포넌트 div 단위로 새로 파기
// 배열 개수 세어서 프리뷰 컴포넌트 만들기
// 누르면 그걸로 선택되게 처리

class SearchbarComponent extends Component {

    constructor(prop) {
        super(prop);
        this.state = {
            input: '', // 사용자가 입력한 영화 제목
            preview: '', // 검색된 결과 프리뷰
            selected: '영화', // 현재 선택된 영화
            detailed: '', // 선택된 영화 상세 정보
        }

        this.handleChange = this.handleChange.bind(this);
        this.update = this.update.bind(this);
        this.clearBar = this.clearBar.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    update() {
        const that = this;
        SearchService
            .getInfoByMovieTitle(this.state.input)
            .then((response) => {
                const data = response.data;
                if (data.hasOwnProperty('items')) {
                    that.setState(
                        {
                            selected: '영화',
                            detailed: '',
                            preview: SearchService.setResult(this, data['items']) // 프리뷰 세팅
                        }
                    )
                }
            });
    }

    clearBar() {
        this.setState({
                input: '',
            }
        )
    }

    render() {
        const {
            input, preview, selected, detailed,
        } = this.state;

        return (
            <>
                <div className="auto">
                    <button className={`cancel-btn ${input.length > 0 ? "active" : "inactive"}`}
                            onClick={this.clearBar}>
                        X
                    </button>
                    <input
                        className="search-bar"
                        placeholder="영화 제목을 입력하세요."
                        name="input"
                        value={input}
                        onChange={this.handleChange}
                        onKeyUp={this.update}
                    />
                    <div className="search-results">
                        {preview}
                    </div>
                    <h3 className="selected-text">{selected ?
                        <span style={{color: "lightcoral"}}><strong>{selected}</strong></span> : ''}에 대한 솔직한 리뷰를
                        작성해주세요.</h3>
                    <div>{detailed}</div>
                </div>
            </>
        )
    }

}

export default SearchbarComponent