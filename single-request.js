import {sleep} from 'k6'
import http from 'k6/http'

// See https://k6.io/docs/using-k6/options
export const options = {
    stages: [
        {duration: '1m', target: 20},
        {duration: '3m', target: 20},
        {duration: '1m', target: 0},
    ],
    thresholds: {
        http_req_failed: ['rate<0.02'], // http errors should be less than 2%
        http_req_duration: ['p(95)<5000'], // 95% requests should be below 2s
    },
    ext: {
        loadimpact: {
            distribution: {
                'amazon:us:ashburn': {loadZone: 'amazon:us:ashburn', percent: 100},
            },
        },
    },
}
export default function main() {
    let response = http.get('https://implicaction.herokuapp.com/')
    sleep(1)
}