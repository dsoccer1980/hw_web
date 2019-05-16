import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

import Book from './components/ListBooks';
import EditBook from './components/EditBook';
import Author from './components/ListAuthors';
import EditAuthor from './components/EditAuthor';
import Genre from './components/ListGenres';
import EditGenre from './components/EditGenre';
import CreateAuthor from './components/CreateAuthor';
import CreateGenre from './components/CreateGenre';
import CreateBook from './components/CreateBook';
import DeleteGenre from './components/DeleteGenre';
import DeleteAuthor from './components/DeleteAuthor';
import DeleteBook from './components/DeleteBook';


class App extends Component {

  render() {
    return (
      <Router>
        <div className="container">
          <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav mr-auto">
                <li className="nav-item">
                  <Link to={'/book'} className="nav-link">Books</Link>
                </li>
                <li className="nav-item">
                  <Link to={'/author'} className="nav-link">Authors</Link>
                </li>
                <li className="nav-item">
                  <Link to={'/genre'} className="nav-link">Genres</Link>
                </li>
              </ul>
            </div>
          </nav>

          <Switch>
            <Route exact path='/' component={Book} />
            <Route exact path='/book' component={Book} />
            <Route path='/book/edit/:id' component={EditBook} />
            <Route exact path='/createBook' component={CreateBook} />
            <Route exact path='/author' component={Author} />
            <Route path='/author/edit/:id' component={EditAuthor} />
            <Route exact path='/createAuthor' component={CreateAuthor} />
            <Route exact path='/genre' component={Genre} />
            <Route path='/genre/edit/:id' component={EditGenre} />
            <Route exact path='/createGenre' component={CreateGenre} />
            <Route path='/genre/delete/:id' component={DeleteGenre} />
            <Route path='/author/delete/:id' component={DeleteAuthor} />
            <Route path='/book/delete/:id' component={DeleteBook} />
          </Switch>
        </div>
      </Router>
    );
  }
  
}

export default App;
