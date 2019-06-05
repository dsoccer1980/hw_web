import React, {Component} from 'react';
import SelectShow from './SelectShow';
import axios from "axios";


export default class EditBook extends Component {

    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onCancelClick = this.onCancelClick.bind(this);

        this.state = {
            id: '',
            name: '',
            authorId: '',
            authors: [],
            genreId: '',
            genres: []
        }
    }

    componentDidMount() {
        axios.get(`/book/${this.props.match.params.id}`)
            .then(response => {
                this.setState({
                    id: response.data.id,
                    name: response.data.name,
                    authorId: response.data.author != null ? response.data.author.id : '',
                    genreId: response.data.genre != null ? response.data.genre.id : ''
                });
            });

        axios.get('/author')
            .then(response => {
                this.setState({
                    authors: response.data,
                });
            });

        axios.get('/genre')
            .then(response => {
                this.setState({
                    genres: response.data,
                });
            });


    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        })
    }

    onSubmit(e) {
        e.preventDefault();
        const obj = {
            id: this.state.id,
            name: this.state.name,
            authorId: this.state.authorId,
            genreId: this.state.genreId
        };
        axios.put('/book', JSON.stringify(obj), {
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
                <h3 align="center">Update Book</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <input type="hidden" className="form-control" value={this.state.id}/>
                    </div>
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
                        <input type="submit" value="Update" className="btn btn-primary"/> &nbsp;
                        <button type="button" className="btn btn-secondary" onClick={this.onCancelClick}>Cancel</button>
                    </div>
                </form>
            </div>
        )
    }
}