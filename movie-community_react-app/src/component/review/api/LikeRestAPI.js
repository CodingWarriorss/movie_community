import axios from 'axios';
import { REST_API_SERVER_URL } from 'component/constants/APIConstants';

const requestUrl = REST_API_SERVER_URL + '/api/review/like'
class CommentRestAPI {
    likeReview(likeData) {
        const token = localStorage.getItem("token");
        const config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        return axios.post(requestUrl, likeData, config);
    }

    unlikeReview(likeData) {
        const token = localStorage.getItem("token");
        const config = {
            headers: {
                'Authorization': 'Bearer ' + token
            },
            params : {
                reviewId : likeData.reviewId
            }
        }

        return axios.delete(requestUrl, config);
    }
}

export default new CommentRestAPI();