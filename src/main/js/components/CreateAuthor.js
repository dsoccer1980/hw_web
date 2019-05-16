import React, { Component } from 'react';

export default class CreateAuthor extends Component {
    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onCancelClick = this.onCancelClick.bind(this);

        this.state = {
            name: '',
        }
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        })
    }

    onCancelClick(e) {
        e.preventDefault();
        this.props.history.push('/author');
    }

    onSubmit(e) {
        e.preventDefault();
        const obj = {
            name: this.state.name,
        };
        fetch('/author/save', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        }).then(res => { res.json(); this.props.history.push('/author'); })
    }

    render() {
        return (
            <div style={{ marginTop: 10 }}>
                <h3 align="center">Create Author</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Author Name: </label>
                        <input type="text" className="form-control" value={this.state.name} onChange={this.onChangeName} />
                    </div>
                    <div className="row">
                        <div className="form-group">
                            <input type="submit" value="Create" className="btn btn-primary" /> &nbsp;
                        <button type="button" className="btn btn-secondary" onClick={this.onCancelClick}>Cancel</button>
                        </div>
                    </div>
                </form>
            </div>
        )
    }
}