function updateFaith(delta) {
  FAITH = Math.max(0, Math.min(100, FAITH + delta));
  document.getElementById("faithbar-fill").style.width = FAITH + "%";
  document.getElementById("faith-value").textContent = FAITH;
  if (FAITH >= 100) {
    triggerElysium();
  }
}

function devFaith(delta) {
  updateFaith(delta);

  // проверка на финал
  if (FAITH >= 100) {
    triggerElysium();
  }
}

function showZeusMessage(text) {
  zeusMsg.textContent = text;
  zeusMsg.style.opacity = 1;
  setTimeout(() => zeusMsg.style.opacity = 0, 1200);
}

function triggerElysium() {
  if (gameEnded) return;
  gameEnded = true;

  showZeusMessage("✨ Все души вознеслись в Элизиум ✨");

  // убиваем всех ботов
  botsOnMap.forEach(bot => {
    const id = bot.dataset.id;

    // удалить из БД
    fetch(`/bots/delete/${id}`, { method: "DELETE" }).catch(() => {});

    // эффект исчезновения
    bot.style.transition = "opacity 1s, transform 1s";
    bot.style.opacity = "0";
    bot.style.transform += " scale(0.7)";

    setTimeout(() => bot.remove(), 1000);
  });

  botsOnMap.clear();

  // статус
  statusEl.style.display = "none";

  // блокируем молнию и кнопки
  lightningMode = false;
  disableControls();
  map.classList.add("elysium");
}

function disableControls() {
  document.querySelectorAll(".btn").forEach(btn => {
    btn.style.pointerEvents = "none";
    btn.style.opacity = "0.6";
  });
}


