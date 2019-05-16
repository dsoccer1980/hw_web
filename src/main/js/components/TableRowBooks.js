import React, { Component } from 'react';
import { Link } from 'react-router-dom';


export default class TableRowBooks extends Component {

  constructor(props) {
    super(props);
  }


  render() {
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
          <Link to={"/book/edit/" + this.props.obj.id} className="btn btn-primary">Edit</Link>
        </td>
        <td>
            <Link to={"/book/delete/" + this.props.obj.id} className="btn btn-danger">Delete</Link>
        </td>
      </tr>
    );
  }
}
