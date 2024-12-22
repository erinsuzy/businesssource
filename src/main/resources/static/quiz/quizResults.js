document.addEventListener("DOMContentLoaded", () => {
    // Get the result from the query parameter
    const urlParams = new URLSearchParams(window.location.search);
    const result = urlParams.get("result");
    console.log("Full URL Parameters:", window.location.search); // Debugging
    console.log("Parsed Result Parameter:", result); // Debugging

    // Split result into an array if present
    const resultArray = result ? result.split(",") : [];
    console.log("Parsed Result Array:", resultArray); // Debugging

    // Elements for displaying results
    const resultText = document.getElementById("result-text");
    const recommendationsDiv = document.getElementById("recommendations");

    if (resultArray.length > 0) {
        // Display the primary result(s)
        resultText.textContent = resultArray.length > 1
            ? `Your best matches are: ${resultArray.join(", ")}`
            : `Your best match is: ${resultArray[0]}`;

        // Generate and display recommendations
        /** @type {string[]} */
        const recommendations = getRecommendations(resultArray);
        console.log("Generated Recommendations:", recommendations); // Debugging

        recommendationsDiv.innerHTML = recommendations.map(rec => `<p>${rec}</p>`).join("");

        // Save quiz completion in a cookie
        setCookie("quizCompleted", "true", 30);
    } else {
        resultText.textContent = "No quiz results found. Please retake the quiz.";
    }
});

// Function to provide detailed recommendations
function getRecommendations(resultArray) {
    const recommendationDetails = {
        Trainer: "Trainers require flexibility and strong communication skills.",
        Groomer: "Groomers need an upfront investment in equipment and training.",
        Walker: "Walkers enjoy a flexible schedule and minimal upfront costs.",
        Sitter: "Sitters require great customer service.",
        Photographer: "Pet photographers work on their own schedules."
    };

    return resultArray.map(category => recommendationDetails[category] || "No recommendation available.");
}

// Utility function to set a cookie
function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    const expires = "expires=" + date.toUTCString();
    document.cookie = `${name}=${value};${expires};path=/`;
}
