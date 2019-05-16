import React, { Component } from 'react';


export default class SelectShow extends Component {

  render() {
    return (
      <option value={this.props.obj.id}>
        {this.props.obj.name}
      </option>
    );
  }
}
