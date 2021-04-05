import axios from 'axios';
import { IMAGE_RESOURCE_URL, REST_API_SERVER_URL } from 'component/constants/APIConstants';

const requestUrl = REST_API_SERVER_URL + '/api/review'

class ReviewRestAPI{
    getList(pageIndex , movieTitle){
        const token = localStorage.getItem("token");

        let config = {
            headers : {
                'Authorization': 'Bearer ' + token
            },
            params : {
                pageIndex : pageIndex
            }
        }

        if (movieTitle === "") {
            return axios.get(requestUrl, config);
        } else {
            config.params['movieTitle']  = movieTitle;
            return axios.get(requestUrl, config);
        }
    }

    modify( modifyData ){
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }

        let formData = new FormData();
        formData.append("reviewId" , modifyData.reviewId);
        formData.append("content" , modifyData.content);

        return axios.put( requestUrl , formData, config);
    }

    addImage( addImageData ){
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': token
            }
        }

        let formData = new FormData()
        formData.append("reviewId" , addImageData.reviewId);
        formData.append("content" , addImageData.content );
        addImageData.imageList.forEach( imageFile => {
            formData.append("newFiles" , imageFile );
        });

        return axios.put( requestUrl , formData, config);
    }

    delete( deleteData ){
        const token = localStorage.getItem("token");
        let config = {
            headers: {
                'Authorization': token
            },
            params :{
                reviewId: deleteData.reviewId
            }
        }
        return axios.delete( requestUrl, config);
    }


}

export default new ReviewRestAPI();