import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from './AuthenticationService';

export default class TableRowAuthors extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const isAdmin = AuthenticationService.isUserAdmin();
        let linkEdit, linkDelete;

        if (isAdmin === 'true') {
            linkEdit = <Link to={"/author/edit/" + this.props.obj.id} className="btn btn-primary">Edit</Link>;
            linkDelete = <Link to={"/author/delete/" + this.props.obj.id} className="btn btn-danger">Delete</Link>;
        }

        return (
            <tr>
                <td>
                    {this.props.obj.name}
                </td>
                <td>
                    {linkEdit}
                </td>
                <td>
                    {linkDelete}
                </td>
            </tr>
        );
    }
}
