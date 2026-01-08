function getDisplayName(bot) {
  return bot.firstName || bot.name || "Безымянный";
}

function createBot(botData) {
  statusEl.style.display = "none";

  const container = document.createElement("div");
  container.className = "bot-container";
  container.dataset.id = botData.id;

  const label = document.createElement("div");
  label.className = "name-label";
  label.textContent = getDisplayName(botData);

  const sprite = document.createElement("img");
  sprite.className = "sprite";
  sprite.src =
    botData.behavior === "FAITHFUL" ? "/models/faithful.png" :
    botData.behavior === "SINNER"   ? "/models/sinner.png" :
                                     "/models/neutral.png";

  container.append(label, sprite);
  map.appendChild(container);
  botsOnMap.add(container);

  const W = map.clientWidth;
  const H = map.clientHeight;
  let x = Math.random() * (W - 40);
  let y = Math.random() * (H - 60);

  container._stats = {
    name: getDisplayName(botData),
    role: botData.behavior,
    faith: 0,
    sin: 0
  };

  function prayOrSin(container, botData) {
      if (gameEnded) return;
      let delta = 0;

      if (botData.behavior === "FAITHFUL") {
        delta = +1;
        container._stats.faith++;
      }

      if (botData.behavior === "SINNER") {
        delta = -1;
        container._stats.sin++;
      }

      if (delta !== 0) {
        updateFaith(delta);

        const eff = document.createElement("div");
        eff.className = "effect";
        eff.style.color = delta > 0 ? "#00ff7f" : "#ff4444";
        eff.textContent = (delta > 0 ? "+" : "") + delta;
        container.appendChild(eff);

        setTimeout(() => eff.remove(), 1000);
      }

      setTimeout(
        () => prayOrSin(container, botData),
        1000 + Math.random() * 9000
      );
    }

  function move() {
      const step = 35;
      x = Math.max(0, Math.min(W - 40, x + (Math.random()*2-1)*step));
      y = Math.max(0, Math.min(H - 60, y + (Math.random()*2-1)*step));
      container.style.transform = `translate(${x}px, ${y}px)`;
      setTimeout(move, 700 + Math.random()*3500);
  }

  move();
  prayOrSin(container, botData);
}


