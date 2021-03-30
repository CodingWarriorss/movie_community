import React, { Component } from 'react'
import "./RankingComponent.css";
import axios from "axios";
import { REST_API_SERVER_URL } from "component/constants/APIConstants"

class RankingCommponent extends Component {

    constructor(props){
        super(props);

        this.state = {
            movieRangking : []
        }
    }

    componentDidMount(e){
        axios.get(REST_API_SERVER_URL + "/api/ranking" ).then( response => {
            let rangkingList = response.data;
            this.setState({
                movieRangking : [...rangkingList]
            })
        }).catch( error =>{
            console.log( error );
        })
    }

    render() {
        return (
            <div className="ranking-box">
                <table class="table">
  <thead>
    <tr>
      <th scope="col">No</th>
      <th scope="col">Title</th>
      <th scope="col">Last</th>
    </tr>
  </thead>
  <tbody>
      {
          this.state.movieRangking.map( movie =>{
              return (
                  <tr>
                      <th scope="row">{movie.rank}</th>
                    <td>{movie.movieNm}</td>
                    <td>{movie.rankInten}</td>
                  </tr>
              )
          })
      }
  </tbody>
</table>
            </div>
        )
    }
}

export default RankingCommponent