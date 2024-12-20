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
            { answer: "Minimal upfront costs", categories: ["Walker", "Sitter"] },
            { answer: "Moderate investment", categories: ["Photographer"] },
            { answer: "Significant investment", categories: ["Trainer", "Groomer"] }
        ]
    },
    {
        text: "Do you have a flexible schedule to dedicate to your business?",
        options: [
            { answer: "Highly flexible", categories: ["Walker", "Trainer"] },
            { answer: "Some flexibility", categories: ["Groomer", "Photographer"] },
            { answer: "Consistent schedule", categories: ["Sitter"] }
        ]
    },
    {
        text: "Are you willing to take courses or training to prepare for your business?",
        options: [
            { answer: "Yes", categories: ["Trainer", "Groomer"] },
            { answer: "Maybe", categories: ["Photographer"] },
            { answer: "No", categories: ["Sitter", "Walker"] }
        ]
    },
    {
        text: "How comfortable are you with customer service?",
        options: [
            { answer: "Very comfortable", categories: ["Trainer", "Sitter"] },
            { answer: "Moderately comfortable", categories: ["Walker"] },
            { answer: "Prefer minimal interaction", categories: ["Photographer", "Groomer"] }
        ]
    }
];

let currentQuestionIndex = 0;
const answers = [];

const elements = {
    questionTitle: document.getElementById("question-title"),
    questionText: document.getElementById("question-text"),
    quizForm: document.getElementById("quiz-form"),
    prevBtn: document.getElementById("prev-btn"),
    nextBtn: document.getElementById("next-btn"),
    submitBtn: document.getElementById("submit-btn")
};

// Load a question based on its index
function loadQuestion(index) {
    const question = questions[index];
    elements.questionTitle.textContent = `Question ${index + 1} of ${questions.length}`;
    elements.questionText.textContent = question.text;

    elements.quizForm.innerHTML = question.options
        .map((option, i) => `
            <label>
                <input type="radio" name="answer" value="${option.answer}" ${answers[index] === option.answer ? "checked" : ""} />
                ${option.answer}
            </label><br />
        `)
        .join("");

    elements.prevBtn.disabled = index === 0;
    elements.nextBtn.style.display = index === questions.length - 1 ? "none" : "inline-block";
    elements.submitBtn.style.display = index === questions.length - 1 ? "inline-block" : "none";
}

// Save the selected answer
function saveAnswer() {
    const selectedOption = document.querySelector('input[name="answer"]:checked');
    if (selectedOption) {
        answers[currentQuestionIndex] = selectedOption.value;
    } else {
        alert("Please select an answer before proceeding.");
    }
}

// Navigate to the next or previous question
function handleNavigation(direction) {
    saveAnswer();
    currentQuestionIndex += direction;
    loadQuestion(currentQuestionIndex);
}

// Calculate quiz results
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
    const maxScore = Math.max(...Object.values(categoryScores));
    return Object.keys(categoryScores).filter(category => categoryScores[category] === maxScore);
}

// Save a cookie
function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    document.cookie = `${name}=${value};expires=${date.toUTCString()};path=/`;
}

// Event listeners
elements.prevBtn.addEventListener("click", () => handleNavigation(-1));
elements.nextBtn.addEventListener("click", () => handleNavigation(1));

elements.submitBtn.addEventListener("click", () => {
    saveAnswer();
    const results = calculateResults(answers);
    console.log("Calculated Results:", results); // Debugging

    if (results && results.length > 0) {
        const resultParam = encodeURIComponent(results.join(","));
        console.log("Redirect URL:", `/results?result=${resultParam}`); // Debugging

        // Save quiz completion in a cookie
        setCookie("quizCompleted", "true", 30);

        // Redirect to results page
        window.location.href = `/results?result=${resultParam}`;
    } else {
        alert("Error: No results calculated. Please try again.");
    }
});

// Initialize quiz
document.addEventListener("DOMContentLoaded", () => {
    loadQuestion(currentQuestionIndex);
});





