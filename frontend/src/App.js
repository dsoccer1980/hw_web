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
import DeleteAuthor from "./components/DeleteAuthor";
import DeleteBook from "./components/DeleteBook";
import LoginComponent from './components/LoginComponent';
import AuthenticatedRoute from './components/AuthenticatedRoute';
import LogoutComponent from './components/LogoutComponent';
import MenuComponent from './components/MenuComponent';


class App extends Component {


  render() {
    return (
        <Router>
          <div className="container">
            <MenuComponent/>

            <Switch>
              <Route exact path='/' component={LoginComponent} />
              <AuthenticatedRoute path='/book' exact component={Book} />
              <AuthenticatedRoute path='/book/edit/:id' component={EditBook} />
              <AuthenticatedRoute exact path='/createBook' component={CreateBook} />
              <AuthenticatedRoute exact path='/author' component={Author} />
              <AuthenticatedRoute path='/author/edit/:id' component={EditAuthor} />
              <AuthenticatedRoute exact path='/createAuthor' component={CreateAuthor} />
              <AuthenticatedRoute exact path='/genre' component={Genre} />
              <AuthenticatedRoute path='/genre/edit/:id' component={EditGenre} />
              <AuthenticatedRoute exact path='/createGenre' component={CreateGenre} />
              <AuthenticatedRoute path='/genre/delete/:id' component={DeleteGenre} />
              <AuthenticatedRoute path='/author/delete/:id' component={DeleteAuthor} />
              <AuthenticatedRoute path='/book/delete/:id' component={DeleteBook} />

              <Route path="/login" exact component={LoginComponent} />
              <AuthenticatedRoute path="/logout" exact component={LogoutComponent} />
            </Switch>
          </div>
        </Router>
    );
  }

}

export default App;
