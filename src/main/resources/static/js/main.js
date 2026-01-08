fetch("/bots/state")
  .then(r => r.json())
  .then(bots => {
    if (bots.length === 0) statusEl.style.display = "block";
    else bots.forEach(createBot);
  });

function addBot() {
  fetch("/bots/create", { method:"POST" })
    .then(r => r.json())
    .then(bot => {
      if (!bot || !bot.id) {
        showZeusMessage("Новая душа не появилась");
        return;
      }
      createBot(bot);
    });
}

function deleteAllBots() {
  fetch("/bots/delete-all", { method:"DELETE" })
    .then(() => location.reload());
}
