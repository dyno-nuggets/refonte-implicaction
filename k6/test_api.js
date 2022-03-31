import http from 'k6/http';
import { sleep, check } from 'k6';

//Local: host.docker.internal
const baseUrl = "localhost";

function getToken() {
    const payload = JSON.stringify({
        username: 'admin',
        password: 'password',
    });
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const loginRes = http.post("http://"+baseUrl+":8080/api/auth/login", payload, params);

    check(loginRes, {
        'Login status is 200': (r) => r.status === 200,
        'Is Authenticated': (r) => r.json().authenticationToken
    });

    return loginRes.json().authenticationToken;
}

function should_return_200(url) {
    const res = http.get(url);
    check(res, { 'Status is 200': (r) => r.status === 200 });
}

export default function () {

    const options = {
        headers: {
            Authorization: `Bearer ${getToken()}`,
        },
    };

    should_return_200("http://"+baseUrl+":8080/api/job-postings/validated");

    sleep(1);
}
