import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 30,
  duration: '1m',
};

export default function () {
  // игрок зашёл
  http.get('http://localhost:8080/bots/state');

  // иногда создаёт бота
  if (Math.random() < 0.3) {
    http.post('http://localhost:8080/bots/create');
  }

  // иногда читает состояние
  http.get('http://localhost:8080/bots/state');

  sleep(0.5);
}
