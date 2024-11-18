const questions = [
    {
        text: "Are you looking for a full-time or part-time commitment for your business?",
        options: [
            { answer: "Full-time", categories: ["Trainer", "Groomer"] },
            { answer: "Part-time", categories: ["Sitter", "Walker"] },
            { answer: "Don't know yet", categories: ["Photographer"] }
                ]
    },
    {
        text: "What is your budget for starting your business?",
        options: [
            { answer: "I'm looking for something with minimal upfront costs", categories: ["Walker", "Sitter"] },
            { answer: "I'm ok with a moderate investment to get started", categories: ["Photographer"] },
            { answer: "I'm ready to make a significant investment in equipment or setup", categories: ["Trainer", "Groomer"] }
            ]
    },
    {
        text: "Do you have a flexible schedule to dedicate to your business?",
        options: [
            { answer: "I have a highly flexible schedule and can work at different times throughout the day", categories: ["Walker", "Trainer"] },
            { answer: "I have some flexibility but prefer set hours for my workday", categories: ["Groomer", "Photographer"] },
            { answer: "I need a consistent, predictable schedule for my work", categories: ["Sitter"] }
            ]
    },
    {
        text: "Are you willing to take courses or training to prepare for your business? (Education is essential for groomers and trainers.)",
        options: [
            { answer: "Yes, I’m open to taking courses or getting certifications if needed", categories: ["Trainer", "Groomer"] },
            { answer: "Maybe, but I’d prefer a business that doesn’t need extensive training", categories: ["Photographer"] },
            { answer: "No, I want a business I can start without additional training", categories: ["Sitter", "Walker"] }
            ]
    },
    {
        text: "How comfortable are you with customer service and frequent communication with clients?",
        options: [
            { answer: "I’m very comfortable and enjoy interacting with clients regularly", categories: ["Trainer", "Sitter"] },
            { answer: "I’m okay with some communication but prefer to keep it moderate", categories: ["Walker"] },
            { answer: "I prefer minimal client interaction and would rather focus on the work itself", categories: ["Photographer", "Groomer"] }
            ]
    }
];

let currentQuestionIndex = 0;
const answers = [];

const questionTitle = document.getElementById("question-title");
const questionText = document.getElementById("question-text");
const quizForm = document.getElementById("quiz-form");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const submitBtn = document.getElementById("submit-btn");

function loadQuestion(index) {
    const question = questions[index];
    questionTitle.textContent = `Question ${index + 1} of ${questions.length}`;
    questionText.textContent = question.text;

    quizForm.innerHTML = question.options
        .map(
            (option, i) =>
                `<label>
                    <input type="radio" name="answer" value="${option.answer}" ${
                    answers[index] === option.answer ? "checked" : ""
                } />
                    ${option.answer}
                 </label><br />`
        )
        .join("");

    prevBtn.disabled = index === 0;
    nextBtn.style.display = index === questions.length - 1 ? "none" : "inline-block";
    submitBtn.style.display = index === questions.length - 1 ? "inline-block" : "none";
}



function saveAnswer() {
    const selectedOption = document.querySelector('input[name="answer"]:checked');
    if (selectedOption) {
        answers[currentQuestionIndex] = selectedOption.value;
    } else {
        alert("Please select an answer before proceeding.");
    }
}


prevBtn.addEventListener("click", () => {
    saveAnswer();
    if (currentQuestionIndex > 0) {
        currentQuestionIndex--;
        loadQuestion(currentQuestionIndex);
    }
});

nextBtn.addEventListener("click", () => {
    saveAnswer();
    if (currentQuestionIndex < questions.length - 1) {
        currentQuestionIndex++;
        loadQuestion(currentQuestionIndex);
    }
});

submitBtn.addEventListener("click", () => {
    saveAnswer();
    const result = calculateResults(answers);

    // Convert result to query string
    const resultParam = encodeURIComponent(
        Array.isArray(result) ? result.join(",") : result
    );

    // Redirect to results page
    window.location.href = `/results.html?result=${resultParam}`;
});


document.addEventListener("DOMContentLoaded", () => {
    loadQuestion(currentQuestionIndex); // Initialize quiz
});


function calculateResults(userAnswers) {
    const categoryScores = {};

    userAnswers.forEach((answer, index) => {
        const selectedOption = questions[index].options.find(opt => opt.answer === answer);
        if (selectedOption) {
            selectedOption.categories.forEach(category => {
                categoryScores[category] = (categoryScores[category] || 0) + 1;
            });
        }
    });

    // Get all categories with the highest score
    const maxScore = Math.max(...Object.values(categoryScores));
    const bestMatches = Object.keys(categoryScores).filter(
        category => categoryScores[category] === maxScore
    );

    // Return the best match or matches
    return bestMatches.length > 1 ? bestMatches : bestMatches[0];
}





