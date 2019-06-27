import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from './AuthenticationService';

export default class TableRowAuthors extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const isAdmin = AuthenticationService.isUserAdmin();

        return (
            <tr>
                <td>
                    {this.props.obj.name}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={"/author/edit/" + this.props.obj.id} className="btn btn-primary">Edit</Link>}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={"/author/delete/" + this.props.obj.id} className="btn btn-danger">Delete</Link>}
                </td>
            </tr>
        );
    }
}
