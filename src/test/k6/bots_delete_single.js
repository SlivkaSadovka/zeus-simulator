import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  const list = http.get('http://localhost:8080/bots/state');
  const bots = JSON.parse(list.body);

  if (bots.length > 0) {
    const id = bots[0].id;
    http.del(`http://localhost:8080/bots/delete/${id}`);
  }

  sleep(0.3);
}
