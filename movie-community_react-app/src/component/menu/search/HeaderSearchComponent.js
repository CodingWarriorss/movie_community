import { Component } from "react";
import '../../review/searchbar/SearchService'
import SearchService from "../../review/searchbar/SearchService";
import '../../css/SearchBar.css'

class HeaderSearchComponent extends Component {
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
        this.clearState = this.clearState.bind(this);
        this.setTitle = this.setTitle.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
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
                            detailed : '',
                            preview: this.setResult(data['items'])
                        }
                    )
                }
             });
    }

    setResult(results) {
        console.log(results);
        let cnt = -1;

        return results.map(movie => {
            const title = SearchService.replaceTxt(movie['title']);
            const actor = SearchService.replaceTxt(movie['actor']).replaceAll('|', ', ');
            const director = SearchService.replaceTxt(movie['director']).replaceAll('|', '');

            const image = movie['image'];
            const userRating = movie['userRating'];
            const pubDate = movie['pubDate'];

            const index = ++cnt;

            return(
                <div key={index} className={`search-previews ${index === 0 ? "start" : ""}`}
                     onClick={() => {
                         this.setTitle(title); // 타이틀 세팅, 부모로 전송
                     }}>
                    <nav className="first">
                        <p className="name">{title}</p>
                        <img src={image} alt={image} className="movie-img"/>
                        {userRating ? <p className="sub-header">[평점] {userRating}  </p> : ''}
                        {pubDate ? <p className="sub-header">[제작연도] {pubDate}  </p> : ''}
                        {director ? <p className="sub-header">[감독] {director}  </p> : ''}
                        {actor ? <p className="sub-header">[출연진] <br/> {actor}</p> : ''}
                    </nav>
                </div>
            )
        });
    }

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
        //this.props.selectedItem(title);
        console.log("HeaderSearchComponent");
        console.log(title);
        this.props.movieTitleSelected(title);
    }

    clearState() {
        this.setState(
            {
                selected : ''
            }
        )
        this.props.movieTitleSelected('');
    }

    render() {
        const { input, preview, selected } = this.state;

        return (
            <div style={{ width: 450 }}>
                <input
                    className="search w-100 `"
                    placeholder="영화 검색"
                    name="input"
                    value={input}
                    onChange={this.handleChange}
                    onKeyUp={this.update}
                />
                {input ?
                    <div className="search-direction" style={{
                        backgroundColor : "white"
                    }}>{preview}</div> : ''
                }
                <button
                            onClick={this.clearState}>
                    X
                </button>
            </div>
        )
    }
}

export default HeaderSearchComponent;