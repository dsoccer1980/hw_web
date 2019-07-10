import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import TableRowAuthors from './TableRowAuthors'
import axios from "axios";
import AuthenticationService from './AuthenticationService';
import {API_URL} from './Const';

export default class ListAuthors extends Component {

    constructor(props) {
        super(props);
        this.state = {authors: []};
    }

    componentDidMount() {
        axios.get(`${API_URL}/author`)
            .then(response => {
                this.setState({authors: response.data});
            })
    }

    tabRow() {
        return this.state.authors.map(function (object, i) {
            return <TableRowAuthors obj={object} key={i}/>;
        });
    }

    render() {
        const isAdmin = AuthenticationService.isUserAdmin();

        return (
            <div>
                <h3 align="center">Authors List</h3>
                <table className="table table-striped" style={{marginTop: 20}}>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th colSpan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tabRow()}
                    </tbody>
                </table>
                <div>
                    {isAdmin === 'true' && <Link to={'/createAuthor'} className="nav-link">
                        <button className="btn btn-primary">Create</button>
                    </Link>}
                </div>
            </div>
        );
    }
}