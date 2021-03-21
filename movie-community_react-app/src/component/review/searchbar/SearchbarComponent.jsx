import React, {Component} from "react";
import '../../css/SearchBar.css';
import SearchService from "./SearchService";

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
        this.setResult = this.setResult.bind(this);
        this.clearBar = this.clearBar.bind(this);
        this.setTitle = this.setTitle.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    // 실시간 영화검색
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
                            preview: this.setResult(data['items']) // 프리뷰 세팅
                        }
                    )
                }
            });
    }

    // 검색결과 프리뷰창 세팅[service에 위치시키면 부모로 title 보내기 까다로움]
    setResult(results) {// 검색 결과 배열
        let cnt = -1; // 영화개수 카운트

        return results.map(movie => {
            const title = SearchService.replaceTxt(movie['title']);
            const actor = SearchService.replaceTxt(movie['actor']).replaceAll('|', ', ');
            const director = SearchService.replaceTxt(movie['director']).replaceAll('|', '');

            const image = movie['image'];
            const userRating = movie['userRating'];
            const pubDate = movie['pubDate'];

            const index = ++cnt; // div 구별을 위한 key값
            return (
                <div key={index} className={`search-preview ${index === 0 ? "start" : ""}`}
                     onClick={() => {
                         this.setTitle(title); // 타이틀 세팅, 부모로 전송
                     }}>
                    <article className="first">
                        <p className="name">{title}</p>
                        <img src={image} alt={image} className="movie-img"/>
                        {userRating ? <p className="sub-header">[평점] {userRating}  </p> : ''}
                        {pubDate ? <p className="sub-header">[제작연도] {pubDate}  </p> : ''}
                        {director ? <p className="sub-header">[감독] {director}  </p> : ''}
                        {actor ? <p className="sub-header">[출연진] <br/> {actor}</p> : ''}
                    </article>
                </div>
            )
        });
    }

    // 선택된 영화 이름 세팅
    setTitle(title) {
        this.setState(
            {
                selected: title, // 영화 제목
                input: '', // 검색창과 결과 미리보기 창은 초기화한다.
                preview: '',
            }
        )

        // 부모 컴포넌트에 전달 (명시적으로 호출해야 함)
        // callbackFromParent : fix된 이름
        this.props.callbackFromParent(title);
    }

    // 검색어 지우기
    clearBar() {
        this.setState({
                input: '',
                preview: '',
            }
        )
    }

    render() {
        const {
            input, preview, selected,
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
                        placeholder="영화 제목을 입력하세요"
                        name="input"
                        value={input}
                        onChange={this.handleChange}
                        onKeyUp={this.update}
                    />
                    {input ?
                        <div className="search-results">{preview}</div> : ''
                    }
                    <h3 className="search-direction">{selected ?
                        <span className="selected-text">
                            <strong>{selected}</strong></span> : ''}에 대한 솔직한 리뷰를
                        작성해주세요</h3>
                </div>
            </>
        )
    }

}

export default SearchbarComponent