import React, { Component } from 'react';

export default class EditGenre extends Component {

    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onCancelClick = this.onCancelClick.bind(this);

        this.state = {
            id: '',
            name: '',
        }
    }

    componentDidMount() {
        fetch("/genre/edit/" + this.props.match.params.id)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        id: result.id,
                        name: result.name
                    })
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

    onCancelClick(e) {
        e.preventDefault();
        this.props.history.push('/genre');
    }

    onSubmit(e) {
        e.preventDefault();
        const obj = {
            id: this.state.id,
            name: this.state.name,
        };
        fetch('/genre/save', {
            method: 'put',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        }).then(res => res.json())
            .then(res => {
                this.props.history.push('/genre');
            });

    }

    render() {
        return (
            <div style={{ marginTop: 10 }}>
                <h3 align="center">Update genre</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <input type="hidden" className="form-control" value={this.state.id} />
                    </div>
                    <div className="form-group">
                        <label>genre Name: </label>
                        <input type="text" className="form-control" value={this.state.name} onChange={this.onChangeName} />
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