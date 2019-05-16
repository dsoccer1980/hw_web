import React, { Component } from 'react';
import SelectShow from './SelectShow';


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
        fetch("/book/edit/" + this.props.match.params.id)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        id: result.id,
                        name: result.name,
                        authorId: result.author != null ? result.author.id : '',
                        genreId: result.genre != null ? result.genre.id : ''
                    })
                },
                (error) => {
                    this.setState({ error });
                }
            )

        fetch("/author/")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({ authors: result })
                },
                (error) => {
                    this.setState({ error });
                }
            )

        fetch("/genre/")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({ genres: result })
                },
                (error) => {
                    this.setState({ error });
                }
            )
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
        fetch('/book/save', {
            method: 'put',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        }).then(res => { res.json(); this.props.history.push('/book'); })


    }

    onCancelClick(e) {
        e.preventDefault();
        this.props.history.push('/book');
    }

    tabRowAuthor() {
        return this.state.authors.map(function (object, i) {
            return <SelectShow obj={object} key={i} />;
        });
    }

    tabRowGenre() {
        return this.state.genres.map(function (object, i) {
            return <SelectShow obj={object} key={i} />;
        });
    }

    changeSelectAuthor = (e) => {
        this.setState({ authorId: e.currentTarget.value })
    }

    changeSelectGenre = (e) => {
       this.setState({ genreId: e.currentTarget.value })
    }


    render() {
        return (
            <div style={{ marginTop: 10 }}>
                <h3 align="center">Update Book</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <input type="hidden" className="form-control" value={this.state.id} />
                    </div>
                    <div className="form-group">
                        <label>Book Name: </label>
                        <input type="text" className="form-control" value={this.state.name} onChange={this.onChangeName} />
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
                        <input type="submit" value="Update" className="btn btn-primary" /> &nbsp;
                        <button type="button" className="btn btn-secondary" onClick={this.onCancelClick}>Cancel</button>
                    </div>
                </form>
            </div>
        )
    }
}