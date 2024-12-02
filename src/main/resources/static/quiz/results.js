document.addEventListener("DOMContentLoaded", () => {
    // Get the result from the query parameter
    const urlParams = new URLSearchParams(window.location.search);
    const result = urlParams.get("result");

    // Split result into an array if needed
    const resultArray = result ? result.split(",") : [];

    // Elements for displaying results
    const resultText = document.getElementById("result-text");
    const recommendationsDiv = document.getElementById("recommendations");

    if (resultArray.length > 0) {
        // Display the primary result
        if (resultArray.length > 1) {
            resultText.textContent = `Your best matches are: ${resultArray.join(", ")}`;
        } else {
            resultText.textContent = `Your best match is: ${resultArray[0]}`;
        }

        // Add recommendations based on the result
        const recommendations = getRecommendations(resultArray);
        recommendationsDiv.innerHTML = recommendations.map(rec => `<p>${rec}</p>`).join("");
    } else {
        resultText.textContent = "No result found. Please take the quiz again.";
    }
});

// Function to provide detailed recommendations
function getRecommendations(resultArray) {
    const recommendationDetails = {
        Trainer: "Trainers require flexibility and strong communication skills. Consider taking certifications to improve your training techniques.",
        Groomer: "Groomers need an upfront investment in equipment and training but often have a more predictable schedule.",
        Walker: "Walkers enjoy a flexible schedule and minimal upfront costs. It's a great option if you love spending time outdoors with pets.",
        Sitter: "Sitters require great customer service and enjoy spending time with pets at their clients' homes. This is an excellent low-cost startup option.",
        Photographer: "Pet photographers can work on their own schedules and focus on creating beautiful memories for pet owners. A moderate investment in equipment is required."
    };

    return resultArray.map(category => recommendationDetails[category] || "No recommendation available.");
}

