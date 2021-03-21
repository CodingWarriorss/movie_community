import axios from "axios";

class SearchService {

    // 1. naver 영화 api 요청
    getInfoByMovieTitle(input) {
        return axios.get(`http://localhost:8080/api/review`, {
            params: {
                title: input,
                // display: 20
            }
        })
    }

    // 마크업 언어 처리
    replaceTxt(text) {
        return text
            .replaceAll('&amp;', '&')
            .replaceAll('&lt;', '<')
            .replaceAll('&gt;', '>')
            .replaceAll('&apos;', '`')
            .replaceAll('&quot', '`')
            .replaceAll('*js*', 'javascript:')
            .replaceAll('<b>', '')
            .replaceAll('</b>', '')
            .replaceAll('<p>', '')
            .replaceAll('</p>', '');
    }

    // 2. (1)에 대한 후처리
    // setResult(prop, results) {// 현재 prop, 검색 결과 배열
    //     let cnt = -1; // 영화개수 카운트
    //     return results.map(movie => {
    //         const title = this.replaceTxt(movie['title']);
    //         const actor = this.replaceTxt(movie['actor']).replaceAll('|', ', ');
    //         const director = this.replaceTxt(movie['director']).replaceAll('|', '');
    //
    //         const image = movie['image'];
    //         const userRating = movie['userRating'];
    //         const pubDate = movie['pubDate'];
    //
    //         const index = ++cnt; // div 구별을 위한 key값
    //         return (
    //             <div key={index} className={`search-preview ${index === 0 ? "start" : ""}`}
    //                  onClick={(() => { // <div/>가 클릭되면 선택된 div의 영화정보 출력
    //                      prop.setState(
    //                          {
    //                              selected: title, // 영화 제목
    //                              input: '', // 검색창과 결과 미리보기 창은 초기화한다.
    //                              preview: '',
    //                          }
    //                      )
    //                  })}>
    //                 <article className="first">
    //                     <p className="name">{title}</p>
    //                     <img src={image} alt={image} className="selected-img"/>
    //                     {userRating ? <p className="sub-header">[평점] {userRating}  </p> : ''}
    //                     {pubDate ? <p className="sub-header">[제작연도] {pubDate}  </p> : ''}
    //                     {director ? <p className="sub-header">[감독] {director}  </p> : ''}
    //                     {actor ? <p className="sub-header">[출연진] <br/> {actor}</p> : ''}
    //                 </article>
    //             </div>
    //         )
    //     });
    // }

    // 3. 선택된 영화 정보 세팅
    setSelectedMovie(movie) {
        const image = movie['image'];
        const userRating = movie['userRating'];
        const pubDate = movie['pubDate'];
        let director = this.replaceTxt(movie['director']).replaceAll('|', ',').split(',')[0];
        let actor = this.replaceTxt(movie['actor']).replaceAll('|', ',');
        if (actor.length > 10) {
            actor = actor.split(',').slice(0, 2).join(',');
        }

        return (
            <div className="first">
                <img src={image} alt={image} className="selected-img"/>
                <span className="selected-movie">
                {userRating ? <p>[평점] {userRating}  </p> : ''}
                    {pubDate ? <p>[제작연도] {pubDate}  </p> : ''}
                    {director ? <p>[감독] {director}  </p> : ''}
                    {actor ? <p>[출연진] <br/> {actor}</p> : ''}
                </span>
            </div>)
    }

}

export default new SearchService();