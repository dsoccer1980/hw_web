import React, {Component} from 'react';
import axios from 'axios';


export default class DeleteAuthor extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        axios.delete('/author/?id=' + this.props.match.params.id)
            .then(res => {
                this.props.history.push('/author');
            });
    }

    render() {
        return (
            ""
        );
    }
}