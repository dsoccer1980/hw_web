import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from "./AuthenticationService";


export default class TableRowBooks extends Component {

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
                    {obj.author != null ? obj.author.name : ""}
                </td>
                <td>
                    {obj.genre != null ? obj.genre.name : ""}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={`/book/edit/${obj.id}`} className="btn btn-primary">Edit</Link>}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={`/book/delete/${obj.id}`} className="btn btn-danger">Delete</Link>}
                </td>
            </tr>
        );
    }
}
