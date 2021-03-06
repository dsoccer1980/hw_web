import React, {Component} from 'react';
import SelectShow from './SelectShow';
import axios from 'axios';
import {API_URL} from './Const';


export default class CreateBook extends Component {

    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onCancelClick = this.onCancelClick.bind(this);

        this.state = {
            name: '',
            authorId: '',
            authors: [],
            genreId: '',
            genres: []
        }
    }

    componentDidMount() {
        axios.get(`${API_URL}/author`)
            .then(response => {
                this.setState({authors: response.data});
            });
        axios.get(`${API_URL}/genre`)
            .then(response => {
                this.setState({genres: response.data});
            })
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        })
    }

    onSubmit(e) {
        e.preventDefault();
        const obj = {
            name: this.state.name,
            authorId: this.state.authorId,
            genreId: this.state.genreId
        };
        axios.post(`${API_URL}/book`, JSON.stringify(obj), {
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            }
        })
            .then(res => {
                this.props.history.push('/book');
            });
    }

    onCancelClick(e) {
        e.preventDefault();
        this.props.history.push('/book');
    }

    tabRowAuthor() {
        return this.state.authors.map(function (object, i) {
            return <SelectShow obj={object} key={i}/>;
        });
    }

    tabRowGenre() {
        return this.state.genres.map(function (object, i) {
            return <SelectShow obj={object} key={i}/>;
        });
    }

    changeSelectAuthor = (e) => {
        this.setState({authorId: e.currentTarget.value})
    }

    changeSelectGenre = (e) => {
        this.setState({genreId: e.currentTarget.value})
    }

    render() {
        return (
            <div style={{marginTop: 10}}>
                <h3 align="center">Create Book</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Book Name: </label>
                        <input type="text" className="form-control" value={this.state.name}
                               onChange={this.onChangeName}/>
                    </div>
                    <div className="form-group">
                        <label>Author Name: </label>
                        <select className="custom-select"
                                name="authorId"
                                onChange={this.changeSelectAuthor}
                                value={this.state.authorId}>

                            <option disabled value="">Выберите автора</option>
                            {this.tabRowAuthor()}

                        </select>
                    </div>
                    <div className="form-group">
                        <label>Genre Name: </label>
                        <select className="custom-select"
                                name="genreId"
                                onChange={this.changeSelectGenre}
                                value={this.state.genreId}>
                            <option disabled value="">Выберите жанр</option>
                            {this.tabRowGenre()}

                        </select>
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Create" className="btn btn-primary"/> &nbsp;
                        <button type="button" className="btn btn-secondary" onClick={this.onCancelClick}>Cancel</button>
                    </div>
                </form>
            </div>
        )
    }
}