const questions = [

    {
        text: "When you imagine running your own business, do you see yourself dedicating most of your time to it, or would you prefer it to fit around other commitments?",
        options: [
            { answer: "I’d love to dive in full-time and make it my primary focus!", categories: ["Trainer", "Groomer"] },
            { answer: "I’d prefer something part-time that complements my current schedule.", categories: ["Sitter", "Walker"] },
            { answer: "I’m not sure yet—I’m still exploring my options.", categories: ["Photographer"] }
        ]
    },
    {
        text: "Starting a business can involve different levels of financial commitment. What’s your comfort level when it comes to upfront costs?",
        options: [
            { answer: "I’d like to keep my initial expenses as low as possible—minimal upfront costs are ideal for me.", categories: ["Walker", "Sitter"] },
            { answer: "I’m willing to invest a moderate amount to get things started.", categories: ["Photographer"] },
            { answer: "I’m ready to make a significant investment if it means I can build something really special.", categories: ["Trainer", "Groomer"] }
        ]
    },
    {
        text: "Your availability can make a big difference in the type of business you run. How much flexibility do you have in your daily schedule?",
        options: [
            { answer: "My schedule is very open—I can be flexible and adjust my time as needed.", categories: ["Walker", "Trainer"] },
            { answer: "I have some flexibility, but I’d prefer a bit of structure.", categories: ["Groomer", "Photographer"] },
            { answer: "I thrive on routine and prefer a consistent, predictable schedule.", categories: ["Sitter"] }
        ]
    },
    {
        text: "Some businesses require formal training or certifications. How do you feel about taking courses or learning new skills to prepare for your business?",
        options: [
            { answer: "Absolutely! I’m excited to learn and take any training that will help me succeed.", categories: ["Trainer", "Groomer"] },
            { answer: "I might consider training if it’s necessary or beneficial.", categories: ["Photographer"] },
            { answer: "I’d prefer to stick with something that doesn’t require formal training or courses.", categories: ["Sitter", "Walker"] }
        ]
    },
    {
        text: "Customer service is a big part of running a business. How do you feel about interacting with clients and customers?",
        options: [
            { answer: "I love working with people and feel very comfortable interacting with clients.", categories: ["Trainer", "Sitter"] },
            { answer: "I don’t mind customer interactions but prefer to keep things straightforward.", categories: ["Walker"] },
            { answer: "I’d rather focus on the work itself and have minimal client interaction.", categories: ["Photographer", "Groomer"] }
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





