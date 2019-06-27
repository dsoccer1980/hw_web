import axios from 'axios'

const API_URL = ''

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';
export const IS_USER_ADMIN = 'false';

class AuthenticationService {

    executeJwtAuthenticationService(username, password) {
        return axios.post(`${API_URL}/authenticate`, {
            username,
            password
        })
    }

    registerSuccessfulLoginForJwt(username, token) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
        this.setupAxiosInterceptors(this.createJWTToken(token));
        this.setupIsAdmin(token);
    }

    createJWTToken(token) {
        return 'Bearer ' + token
    }

    setupIsAdmin(token) {
        axios.get(`${API_URL}/isadmin`, {
            },{
                headers: {
                    Authorization: 'Bearer ' + token
                }}
        )  .then(response => {
            sessionStorage.setItem(IS_USER_ADMIN, response.data)
        });
    }

    isUserAdmin() {
        let isAdmin = sessionStorage.getItem(IS_USER_ADMIN);
        if (isAdmin === null) return false;
        return isAdmin;
    }

    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(IS_USER_ADMIN);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return false
        return true
    }

    getLoggedInUserName() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return ''
        return user
    }

    setupAxiosInterceptors(token) {
        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedIn()) {
                    config.headers.authorization = token
                }
                return config
            }
        )
    }
}

export default new AuthenticationService()