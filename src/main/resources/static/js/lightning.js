// js/lightning.js

const RADIUS = 120;
const lightningSound = new Audio("/assets/lightning.mp3");
lightningSound.volume = 0.6;

function enableLightning(e) {
    // ❗ ОЧЕНЬ ВАЖНО
    e.stopPropagation();

    lightningMode = true;
    showZeusMessage("Выберите место удара ⚡");
}

map.addEventListener("click", e => {
    if (!lightningMode) return;

    lightningMode = false;

    const clickX = e.clientX;
    const clickY = e.clientY;

    showZeusMessage("⚡ Удар молнии!");

    spawnLightningEffect(clickX, clickY);

    lightningSound.currentTime = 0;
    lightningSound.play().catch(() => {});

    botsOnMap.forEach(bot => {
        const rect = bot.getBoundingClientRect();
        const botX = rect.left + rect.width / 2;
        const botY = rect.top + rect.height / 2;

        const dist = Math.hypot(botX - clickX, botY - clickY);

        if (dist <= RADIUS) {
            const id = bot.dataset.id;

            // удаление из БД
            fetch(`/bots/delete/${id}`, { method: "DELETE" }).catch(() => {});

            // удаление визуально
            bot.remove();
            botsOnMap.delete(bot);
        }
    });

    if (botsOnMap.size === 0) {
        statusEl.style.display = "block";
    }
});

function spawnLightningEffect(x, y) {
  const fx = document.createElement("img");
  fx.src = "/models/lightning.png";
  fx.style.position = "fixed";
  fx.style.left = x + "px";
  fx.style.top = y + "px";
  fx.style.width = "128px";
  fx.style.height = "128px";
  fx.style.transform = "translate(-50%, -50%)";
  fx.style.pointerEvents = "none";
  fx.style.zIndex = 100000;
  fx.style.imageRendering = "pixelated";

  document.body.appendChild(fx);

  setTimeout(() => fx.remove(), 500);
}
