const hsList = document.getElementById("hslist");
const highScores = JSON.parse(localStorage.getItem("highScore")) || [];

hsList.innerHTML = highScores.map(score =>{
  return `<li class = "high- score"> ${score.name} - ${score.score}</li>`;
}).join("");