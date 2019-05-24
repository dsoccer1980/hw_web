import React, { Component } from 'react';


export default class DeleteGenre extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        fetch('http://localhost:8080/genre/?id=' + this.props.match.params.id, {
            method: 'delete',
        }).then(res => {
            this.props.history.push('/genre');
        })
            .catch(err => console.log(err));
    }

    render() {
        return (
            ""
        );
    }
}