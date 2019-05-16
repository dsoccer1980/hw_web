import React, { Component } from 'react';
import TableRowGenres from './TableRowGenres'
import { Link } from 'react-router-dom';

export default class ListGenres extends Component {

  constructor(props) {
    super(props);
    this.state = { genres: [] };
  }

  componentDidMount() {
    fetch("/genre")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({ genres: result })
        },
        (error) => {
          this.setState({ error });
        }
      )
  }

  tabRow() {
    return this.state.genres.map(function (object, i) {
      return <TableRowGenres obj={object} key={i} />;
    });
  }

  render() {
    return (
      <div>
        <h3 align="center">Genres List</h3>
        <table className="table table-striped" style={{ marginTop: 20 }}>
          <thead>
            <tr>
              <th>Name</th>
              <th colSpan="2">Action</th>
            </tr>
          </thead>
          <tbody>
            {this.tabRow()}
          </tbody>
        </table>
        <Link to={'/createGenre'} className="nav-link">
          <button className="btn btn-primary">Create</button>
        </Link>
      </div>
    );
  }
}