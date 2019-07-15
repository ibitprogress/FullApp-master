const SERVER_URL = "http://localhost:8090/";

function getAuthData() {
    let data = {
        token : localStorage.getItem('auth_token'),
        role : localStorage.getItem('auth_role')
    }
    return data;
}

function parseTokenData(token) {
    return JSON.parse(atob(token.split(".")[1]));
}
