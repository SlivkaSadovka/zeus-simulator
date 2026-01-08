// js/music.js

let musicStarted = false;

const bgMusic = new Audio("/assets/music.mp3");
bgMusic.loop = true;
bgMusic.volume = 0.4; // 0.0 – 1.0 (можешь подобрать)

// запуск музыки ТОЛЬКО после первого действия пользователя
function startMusicOnce() {
  if (musicStarted) return;
  musicStarted = true;

  bgMusic.play().catch(() => {
    // если браузер всё ещё запрещает — просто молча игнорим
  });

  // больше не слушаем
  document.removeEventListener("click", startMusicOnce);
  document.removeEventListener("keydown", startMusicOnce);
}

// любое действие пользователя
document.addEventListener("click", startMusicOnce);
document.addEventListener("keydown", startMusicOnce);
