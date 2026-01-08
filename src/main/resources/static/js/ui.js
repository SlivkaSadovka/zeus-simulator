function updateFaith(delta) {
  FAITH = Math.max(0, Math.min(100, FAITH + delta));
  document.getElementById("faithbar-fill").style.width = FAITH + "%";
  document.getElementById("faith-value").textContent = FAITH;
}

function showZeusMessage(text) {
  zeusMsg.textContent = text;
  zeusMsg.style.opacity = 1;
  setTimeout(() => zeusMsg.style.opacity = 0, 1200);
}
