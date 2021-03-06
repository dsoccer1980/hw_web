import React, {Component} from 'react';
import TableRowBooks from './TableRowBooks'
import {Link} from 'react-router-dom';
import axios from 'axios';
import AuthenticationService from "./AuthenticationService";
import {API_URL} from './Const';

export default class ListBooks extends Component {

    constructor(props) {
        super(props);
        this.state = {books: []};
    }

    componentDidMount() {
        axios.get(`${API_URL}/book`)
            .then(response => {
                this.setState({books: response.data});
            })
    }

    tabRow() {
        return this.state.books.map(function (object, i) {
            return <TableRowBooks obj={object} key={i}/>;
        });
    }

    render() {
        const isAdmin = AuthenticationService.isUserAdmin();

        return (
            <div>
                <h3 align="center">Books List</h3>
                <table className="table table-striped table-hover" style={{marginTop: 20}}>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th colSpan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tabRow()}
                    </tbody>
                </table>
                <div>
                    {isAdmin === 'true' && <Link to={'/createBook'} className="nav-link">
                        <button className="btn btn-primary">Create</button>
                    </Link>
                    }
                </div>
            </div>
        );
    }
}