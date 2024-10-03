const username = document.getElementById("username");
const save = document.getElementById("saveBtn");
const mostRecentScore = localStorage.getItem("mostRecentScore");
const final = document.getElementById("final-score");
final.innerText = mostRecentScore;
const highScore = JSON.parse(localStorage.getItem("highscores")) || [];
const MAX_HIGH = 5;


username.addEventListener("keyup", ()=>{
    save.disabled = !username.value;
})

saveHightScore = (e) =>{
    e.preventDefault();
    const score = {
        score : mostRecentScore,
        name : username.value,
    };
    
    highScore.push(score);
    highScore.sort((a,b)=>{
        return b.score -a.score;
        // this mean that if b score is greater than a, put b in front;
    });
    
    highScore.splice(MAX_HIGH);
    localStorage.setItem("highScore",JSON.stringify(highScore));

    window.location.assign('/');
};

