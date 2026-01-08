import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 10,
  duration: '5s',
};

export default function () {
  const res = http.post('http://localhost:8080/bots/create');

  check(res, {
    'created': r => r.status === 200,
    'has id': r => JSON.parse(r.body).id !== undefined,
  });

  sleep(0.1);
}