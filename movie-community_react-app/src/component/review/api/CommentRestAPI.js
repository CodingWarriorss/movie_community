import axios from 'axios';
import { IMAGE_RESOURCE_URL, REST_API_SERVER_URL } from 'component/constants/APIConstants';

const requestUrl = REST_API_SERVER_URL + '/api/review/comment'

class CommentRestAPI{
    add( commentData ){
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': token
            }
        }

        return axios.post(requestUrl, commentData, config);
    }

    delete( commentId ){
        const token = localStorage.getItem("token");
        const config = {
            headers: {
                'Authorization': token
            },
            params: {
                commentId: commentId,
            }
        }

        return axios.delete(requestUrl, config);
    }
}

export default new CommentRestAPI();