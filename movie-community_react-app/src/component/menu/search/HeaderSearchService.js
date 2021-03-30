import axios from "axios";

class SearchService {

    getInfoByMovieTitle(input) {
        return axios.get(`http://localhost:8080/api/review`, {
            params : {
                title : input,
            }
        })
    }

    replaceTxt(text) {
        return text
            .replaceAll('&amp;', '&')
            .replaceAll('&lt;', '<')
            .replaceAll('&gt;', '>')
            .replaceAll('&apos;', '`')
            .replaceAll('&quot', '`')
            .replaceAll('<b>', '')
            .replaceAll('</b>', '')
            .replaceAll('<p>', '')
            .replaceAll('</p>', '');
    }

    setSelectedMovie(movie) {
        const image = movie['image'];
        const userRating = movie['userRating'];
        const pubDate = movie['pubDate'];

        let diretor = this.replaceTxt(movie['director'])
    }
}
