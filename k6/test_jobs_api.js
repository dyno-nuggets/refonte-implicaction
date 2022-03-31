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

export default function () {

    const options = {
        headers: {
            Authorization: `Bearer ${getToken()}`,
        },
    };

    const res = http.get("http://"+baseUrl+":8080/api/companies");

    check(res, {
        'Status companies is 200': (r) => r.status === 200,
    })

    const payload = JSON.stringify({
        apply: true,
        archive: true,
        businessSector: "AGROALIMENTAIRE",
        company: {
            description: "string",
            id: 145,
            logo: "string",
            name: "string",
            url: "string"
        },
        contractType: "CDI",
        createdAt: "2022-03-31T12:53:50.194Z",
        description: "string",
        id: 0,
        keywords: "string",
        location: "string",
        posterId: 0,
        posterName: "string",
        salary: "string",
        shortDescription: "string",
        title: "string",
        valid: true
    });
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const resCreateJob = http.post("http://"+baseUrl+":8080/api/job-postings", payload, params)

    check(resCreateJob, {'Status create job is 200': (r) => r.status === 200})

    const jobId = resCreateJob.json().id;

    const resGetJobCreated = http.get("http://"+baseUrl+":8080/api/job-postings/" + jobId);

    check(resGetJobCreated, {'Status get created job is 200': (r) => r.status === 200})

    sleep(1);
}
