import React, { Component } from 'react'
import { Link, withRouter } from 'react-router-dom'
import AuthenticationService from './AuthenticationService';

class MenuComponent extends Component {

    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

        return (
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item">
                            <Link to='/book' className="nav-link">Books</Link>
                        </li>
                        <li className="nav-item">
                            <Link to='/author' className="nav-link">Authors</Link>
                        </li>
                        <li className="nav-item">
                            <Link to='/genre' className="nav-link">Genres</Link>
                        </li>
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>}

                    </ul>
                </div>
            </nav>

        )
    }
}

export default withRouter(MenuComponent)