import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from "./AuthenticationService";


export default class TableRowGenres extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const isAdmin = AuthenticationService.isUserAdmin();
        const {obj} = this.props;

        return (
            <tr>
                <td>
                    {obj.name}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={`/genre/edit/${obj.id}`} className="btn btn-primary">Edit</Link>}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={`/genre/delete/${obj.id}`} className="btn btn-danger">Delete</Link>}
                </td>
            </tr>
        );
    }
}
