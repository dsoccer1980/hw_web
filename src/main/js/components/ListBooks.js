import React, { Component } from 'react';
import TableRowBooks from './TableRowBooks'
import { Link } from 'react-router-dom';

export default class ListBooks extends Component {

  constructor(props) {
    super(props);
    this.state = { books: [] };
  }

  componentDidMount() {
    fetch("/book")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({ books: result })
        },
        (error) => {
          this.setState({ error });
        }
      )
  }
  tabRow() {
    return this.state.books.map(function (object, i) {
      return <TableRowBooks obj={object} key={i} />;
    });
  }

  render() {
    return (
      <div>
        <h3 align="center">Books List</h3>
        <table className="table table-striped table-hover" style={{ marginTop: 20 }}>
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
          <Link to={'/createBook'} className="nav-link">
            <button className="btn btn-primary">Create</button>
          </Link>
        </div>
      </div>
    );
  }
}