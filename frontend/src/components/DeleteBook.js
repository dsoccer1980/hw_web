import React, { Component } from 'react';
import axios from "axios";
import {API_URL} from './Const';


export default class DeleteBook extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        axios.delete(`${API_URL}/book/?id=${this.props.match.params.id}`)
            .then(res => {
                this.props.history.push('/book');
            });
    }

    render() {
        return (
            ""
        );
    }
}