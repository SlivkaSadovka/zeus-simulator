let FAITH = 50;
let lightningMode = false;
let gameEnded = false;
let creditsInterval = null;
const botsOnMap = new Set();

const map = document.getElementById("map");
const statusEl = document.getElementById("status");
const zeusMsg = document.getElementById("zeus-message");
