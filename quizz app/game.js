const question = document.getElementById("question");
const choices = Array.from(document.getElementsByClassName("choice-text"));
const progressText = document.getElementById("progressText");
const scoreText = document.getElementById('score');
progressBar = document.getElementById('progressBarFull');

let currentQuestion = {};
let acceptingAnswer = false;

let score = 0;
let questionCounter = 0;

let availableQuestions = [];
let questions = [];

fetch("questions.json").then(res => {
    return res.json();
}).then(loadedQuestions => {
    questions = loadedQuestions;
    startGame();
}).catch(err =>{
    console.log(err);
})

const CORRECT_BONNUS = 10;
const MAX_QUESTION = 3;

startGame = () => {
    questionCounter = 0;
    score = 0;
    availableQuestions = [...questions];
    console.log(availableQuestions);
    getNewQuestion();
};

getNewQuestion = () => {
    if(availableQuestions.length===0 || questionCounter >= MAX_QUESTION){
        window.location.assign('/end.html')
    }
    localStorage.setItem("mostRecentScore",score)
    questionCounter++;
    progressText.innerText = `Question ${questionCounter}/${MAX_QUESTION}`;
    //update the progress bar
    progressBar.style.width = `${(questionCounter/MAX_QUESTION) * 100 }%`;

    const questionIndex = Math.floor(Math.random() * availableQuestions.length);
    currentQuestion = availableQuestions[questionIndex];
    question.innerText = currentQuestion.question;

    choices.forEach(choice => {
        const number = choice.dataset["number"];
        choice.innerText = currentQuestion["choice" + number];
    });
    // this will remove the question that we have already seen
    availableQuestions.splice(questionIndex,1);

    acceptingAnswer = true;
};
choices.forEach(choice => {
    choice.addEventListener("click",e =>{
        if (!acceptingAnswer) return;

        acceptingAnswer = false;
        const selectedChoice = e.target;
        const selectedAnswer = selectedChoice.dataset['number'];

        let classToApply = 'incorrect';
        if(selectedAnswer == currentQuestion.answer){
            classToApply = 'correct';
        };

        if(classToApply==='correct'){
            incrementScore(CORRECT_BONNUS);
        }

        selectedChoice.parentElement.classList.add(classToApply);
        setTimeout(()=>{
            selectedChoice.parentElement.classList.remove(classToApply);
            getNewQuestion();
        },1000);
       
    });
});
incrementScore = num =>{
    score += num;
    scoreText.innerText = score;
}
//startGame();
