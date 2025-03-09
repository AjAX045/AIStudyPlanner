from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import google.generativeai as genai
from pydantic import BaseModel
import json
import math

# ✅ Set up FastAPI app
app = FastAPI()

# ✅ Allow CORS so frontend (HTML) can call API
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allow requests from all origins
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ✅ Set API Key
API_KEY = "AIzaSyAB37LoW88Uy4XKa85WQHDNbuPUi1hKHr4"  # Replace with actual key
genai.configure(api_key=API_KEY)

# ✅ Define request model
class StudyPlanRequest(BaseModel):
    subject: str
    hours_per_day: int
    duration: int
    learning_style: str
    difficulty: str

@app.post("/generate-study-plan")
def generate_study_plan(request: StudyPlanRequest):
    try:
        model = genai.GenerativeModel("gemini-1.5-pro-latest")
        response = model.generate_content(
            f"Create a {request.duration}-day structured study plan for {request.subject}. "
            f"Each day should have {request.hours_per_day} hours of study. "
            f"Customize for a {request.difficulty} level learner with a {request.learning_style} learning style. "
            f"For a {request.learning_style} learner, focus on using the best learning methods. "
            f"Provide **at least 3 resources per day**, including **direct video links, interactive resources, and reference materials**. "
            f"Format the output in JSON with fields: day, topic, hours, key points, and recommended resources. "
            f"For each resource, include 'type', 'title', and 'link'."
        )

        # ✅ Clean response and parse as JSON
        cleaned_text = response.text.strip().replace("```json", "").replace("```", "").strip()
        study_plan = json.loads(cleaned_text)

        # ✅ Ensure response is always an array
        if isinstance(study_plan, dict):
            study_plan = [study_plan]  # Convert single object to list

        # ✅ Generate Learning Pods based on Study Plan
        for day in study_plan:
            total_minutes = day["hours"] * 60
            pod_count = math.ceil(total_minutes / 45)  # Ensure max 45 min per pod
            pod_duration = min(45, total_minutes // pod_count)  # Ensure fair distribution

            key_points = day.get("key points", [])
            resources = day.get("recommended resources", [])

            key_points_per_pod = max(1, math.ceil(len(key_points) / pod_count))
            resources_per_pod = max(1, math.ceil(len(resources) / pod_count))

            learning_pods = []
            for i in range(pod_count):
                start_k = i * key_points_per_pod
                end_k = min(start_k + key_points_per_pod, len(key_points))
                
                start_r = i * resources_per_pod
                end_r = min(start_r + resources_per_pod, len(resources))

                pod_key_points = key_points[start_k:end_k]
                pod_resources = resources[start_r:end_r]

                learning_pods.append({
                    "pod_number": i + 1,
                    "pod_topic": f"{day['topic']} - Pod {i + 1}",
                    "duration_minutes": pod_duration,
                    "key_points": pod_key_points,
                    "resources": pod_resources,
                })

            # ✅ Attach learning pods to each day's schedule
            day["learning_pods"] = learning_pods

        return study_plan  # ✅ Always returning a list (array)

    except Exception as e:
        return {"error": str(e)}
   

