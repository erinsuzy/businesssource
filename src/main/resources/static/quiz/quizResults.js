document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const result = urlParams.get("result");

    console.log("Full URL Parameters:", window.location.search);
    console.log("Parsed Result Parameter:", result);

    const resultArray = result ? result.split(",") : [];
    console.log("Parsed Result Array:", resultArray);

    const resultText = document.getElementById("result-text");
    const recommendationsDiv = document.getElementById("recommendations");
    const errorMessage = document.getElementById("error-message");

    if (resultArray.length > 0 && resultArray[0].trim() !== "") {
        resultText.textContent = resultArray.length > 1
            ? `Your best matches are: ${resultArray.join(", ")}`
            : `Your best match is: ${resultArray[0]}`;

        const recommendations = getRecommendations(resultArray);
        recommendationsDiv.innerHTML = recommendations.map(rec => `<p>${rec}</p>`).join("");

        setCookie("quizCompleted", "true", 30);

        // Hide error message if results exist
        if (errorMessage) {
            errorMessage.style.display = "none";
        }
    } else {
        resultText.textContent = "No quiz results found. Please retake the quiz.";

        // Show error message only if no result exists
        if (errorMessage) {
            errorMessage.style.display = "block";
        }
    }
});


// Function to provide detailed recommendations
function getRecommendations(resultArray) {
    const recommendationDetails = {
        Trainer: "As a trainer, you'll have the rewarding job of helping pet owners build stronger bonds with their pets. This role is perfect if you have a flexible schedule and enjoy working closely with people and their animals.",
        Groomer: "Grooming is a great fit if you enjoy hands-on work with pets and are detail-oriented. While it does require an upfront investment in equipment and training, it’s an excellent way to build a loyal client base.",
        Walker: "Dog walking offers a wonderfully flexible schedule with minimal startup costs. It’s a great way to stay active, spend time with dogs, and enjoy the outdoors.",
        Sitter: "As a pet sitter, your role is to provide peace of mind to pet owners while they’re away. This is a fantastic choice if you love providing exceptional care and have strong customer service skills.",
        Photographer: "Pet photography lets you combine creativity with your love for animals. This role allows you to work independently, set your own schedule, and capture precious moments between pets and their owners."
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
