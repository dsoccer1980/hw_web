import React, { Component } from 'react';


export default class DeleteBook extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        fetch('http://localhost:8080/book/delete/?id=' + this.props.match.params.id, {
            method: 'delete',
        }).then(res => {
            this.props.history.push('/book');
        })
            .catch(err => console.log(err));
    }

    render() {
        return (
            ""
        );
    }
}