// Function to fetch the list of all study plans and find the ID by subject
function fetchStudyPlan() {
    const subjectInput = document.getElementById("subjectName").value.trim().toLowerCase();

    if (!subjectInput) {
        alert("⚠️ Please enter a subject name!");
        return;
    }

    // Fetch all study plans with correct endpoint
    fetch(`http://localhost:8082/api/studyplan/all`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        })
        .then(studyPlans => {
            // Find study plan ID for the given subject
            const matchedPlan = studyPlans.find(plan => plan.subject.toLowerCase() === subjectInput);

            if (!matchedPlan) {
                alert("⚠️ No study plan found for this subject!");
                return;
            }

            // Fetch details of the matched study plan
            fetchStudyPlanById(matchedPlan.id);
        })
        .catch(error => {
            console.error("Error fetching study plans:", error);
            alert("⚠️ Unable to fetch study plans. Please try again later.");
        });
}

// Function to fetch study plan by ID
function fetchStudyPlanById(studyPlanId) {
    fetch(`http://localhost:8082/api/studyplan/${studyPlanId}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        })
        .then(data => {
            displayStudyPlan(data);
        })
        .catch(error => {
            console.error("Error fetching study plan:", error);
            alert("⚠️ Study Plan not found!");
        });
}

// Function to render the study plan dynamically with delete button
function displayStudyPlan(data) {
    const studyPlanContainer = document.getElementById("study-plan");
    
    studyPlanContainer.innerHTML = `
        <h2>${data.subject} Study Plan</h2>
        <div class="study-info">
            <p><strong>⏳ Hours per day:</strong> ${data.hoursPerDay}</p>
            <p><strong>📆 Duration:</strong> ${data.duration} days</p>
            <p><strong>🎨 Learning Style:</strong> ${data.learningStyle}</p>
            <p><strong>📈 Difficulty:</strong> ${data.difficulty}</p>
        </div>
        <h3>📅 Study Plan Details:</h3>
        <div>${data.studyData || "<p>No details available.</p>"}</div>
        <button onclick="deleteStudyPlan(${data.id})" class="delete-btn">❌ Delete</button>
    `;

    renderSchedule(data);
}

// Function to render the study schedule
function renderSchedule(data) {
    const scheduleContainer = document.getElementById("schedule");
    scheduleContainer.innerHTML = "";

    const daySection = document.createElement("div");
    daySection.classList.add("day-section");
    daySection.innerHTML = `<h2>📆 ${data.subject} Schedule</h2>`;

    const item = document.createElement("div");
    item.classList.add("schedule-item");
    item.innerHTML = `
        <span class="subject">${data.subject}</span>
        <p><strong>⏳ Hours per day:</strong> ${data.hoursPerDay}</p>
        <p><strong>📆 Duration:</strong> ${data.duration} days</p>
        <p><strong>🎨 Learning Style:</strong> ${data.learningStyle}</p>
    `;

    daySection.appendChild(item);
    scheduleContainer.appendChild(daySection);
}

// Function to delete a study plan
function deleteStudyPlan(studyPlanId) {
    if (!confirm("Are you sure you want to delete this study plan?")) return;

    fetch(`http://localhost:8082/api/studyplan/${studyPlanId}`, { method: "DELETE" })
        .then(response => response.text())
        .then(message => {
            alert(message);
            document.getElementById("study-plan").innerHTML = ""; // Clear UI after deletion
            document.getElementById("schedule").innerHTML = ""; // Clear schedule after deletion
        })
        .catch(error => {
            console.error("Error deleting study plan:", error);
            alert("⚠️ Failed to delete the study plan.");
        });
}
