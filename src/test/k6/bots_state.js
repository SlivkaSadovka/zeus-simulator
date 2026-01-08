import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 50,          // 50 одновременных игроков
  duration: '30s',  // 30 секунд
};

export default function () {
  const res = http.get('http://localhost:8080/bots/state');

  check(res, {
    'status is 200': r => r.status === 200,
  });

  sleep(0.2);
}