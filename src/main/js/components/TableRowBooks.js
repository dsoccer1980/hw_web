import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from "./AuthenticationService";


export default class TableRowBooks extends Component {

    constructor(props) {
        super(props);
    }


    render() {
        const isAdmin = AuthenticationService.isUserAdmin();
        let linkEdit, linkDelete;

        if (isAdmin === 'true') {
            linkEdit = <Link to={"/book/edit/" + this.props.obj.id} className="btn btn-primary">Edit</Link>;
            linkDelete = <Link to={"/book/delete/" + this.props.obj.id} className="btn btn-danger">Delete</Link>;
        }

        var authorNameProp = this.props.obj.author != null ? this.props.obj.author.name : "";
        var genreNameProp = this.props.obj.genre != null ? this.props.obj.genre.name : "";
        return (
            <tr>
                <td>
                    {this.props.obj.name}
                </td>
                <td>
                    {authorNameProp}
                </td>
                <td>
                    {genreNameProp}
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
