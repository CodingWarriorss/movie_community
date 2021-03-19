import React, {Component} from 'react'
import axios from "axios";

class SearchbarTest extends Component {

    constructor(prop) {
        super(prop);
        this.state = {
            movie: '',
            result: '',
        }

        this.handleChange = this.handleChange.bind(this);
        this.search = this.search.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    search = () => {
        const config = {
            params: {
                title: this.state.movie,
                // display: 20
            }
        }
        // console.log(config);

        axios.get(`http://localhost:8080/api/review`, config)
            .then(response => {
                const data = response.data;
                if (data.hasOwnProperty('items')) { // 검색 결과가 있을 때만 보여주게
                    const arr = data['items'];
                    for (let i = 0; i < arr.length; i++) {
                        const movie = arr[i];
                        console.log(i + 1);
                        if (movie['title']) {
                            console.log('title: ' + movie['title'].replaceAll('<b>', '').replaceAll('</b>', ''));
                        }
                        if (movie['actor']) {
                            console.log('actor: ' + movie['actor'].replaceAll('|', ','));
                        }
                        if (movie['director']) {
                            console.log('director: ' + movie['director'].replaceAll('|', ''));
                        }
                    }
                }
            })
    }

    render() {
        const {
            result
        } = this.state;

        return (
            <>
                <h1>Search bar test</h1>
                <div className="form-group">
                    <label>검색창</label>
                    <input type="text" className="form-control" placeholder="영화 검색"
                           name="movie" value={this.state.movie}
                           onChange={this.handleChange} onKeyUp={this.search}/>
                </div>
                <div className="form-group">
                </div>
            </>
        )
    }
}

export default SearchbarTest