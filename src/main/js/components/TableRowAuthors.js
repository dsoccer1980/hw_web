import React, { Component } from 'react';
import { Link } from 'react-router-dom';


export default class TableRowAuthors extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <tr>
        <td>
          {this.props.obj.name}
        </td>
        <td>
          <Link to={"/author/edit/" + this.props.obj.id} className="btn btn-primary">Edit</Link>
        </td>
        <td>
            <Link to={"/author/delete/" + this.props.obj.id} className="btn btn-danger">Delete</Link>
        </td>
      </tr>
    );
  }
}
