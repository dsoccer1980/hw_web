import React, { Component } from 'react';


export default class DeleteAuthor extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        fetch('http://localhost:8080/author/delete/?id=' + this.props.match.params.id, {
            method: 'delete',
        }).then(res => {
            this.props.history.push('/author');
        })
            .catch(err => console.log(err));
    }

    render() {
        return (
            ""
        );
    }
}