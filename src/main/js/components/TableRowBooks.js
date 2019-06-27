import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AuthenticationService from "./AuthenticationService";


export default class TableRowBooks extends Component {

    constructor(props) {
        super(props);
    }


    render() {
        const isAdmin = AuthenticationService.isUserAdmin();

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
                    {isAdmin === 'true' &&
                    <Link to={"/book/edit/" + this.props.obj.id} className="btn btn-primary">Edit</Link>}
                </td>
                <td>
                    {isAdmin === 'true' &&
                    <Link to={"/book/delete/" + this.props.obj.id} className="btn btn-danger">Delete</Link>}
                </td>
            </tr>
        );
    }
}
