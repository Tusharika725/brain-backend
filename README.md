# UpScroll — Backend (Spring Boot)

This repository contains the **Spring Boot backend** for **UpScroll**: a web app built to reduce doomscrolling by giving users short “brain break” activities.

- Live app (frontend): https://upscroll-tau.vercel.app/
- Live API (backend): https://brain-backend-3.onrender.com

**Team:** 2 people  
**My contribution:** I built and owned the **entire backend** (API design, database integration, and LLM integration).

---

## What this backend does

UpScroll offers:
- **Word Search** activity support
- **AI Caption Writing**: users select a category + write a caption; the backend uses an LLM to **rate** the caption and provide **feedback**
- **FactStation**: users can fetch unlimited **random facts**
- Basic **user auth** endpoints (register/login)
- A **health** endpoint for uptime checks

---

## Tech Stack

- **Java 17**
- **Spring Boot** (Maven Wrapper included)
- **Spring WebMVC** (REST API)
- **Spring Data JPA**
- **PostgreSQL** (configured via env vars; suitable for Neon.tech or any Postgres)
- **Gemini API** for LLM scoring/feedback
- Dockerfile included (`backend/Dockerfile`)

---

## Project structure

- `backend/` — Spring Boot application
  - `pom.xml` — Maven project
  - `src/main/java/...` — controllers/services/models/repositories
  - `src/main/resources/application.properties` — config
  - `Dockerfile` — container build

---

## Setup & Run (Local)

### Prerequisites
- Java **17**
- A PostgreSQL database (local or hosted)
- Gemini API key

### Clone
```bash
git clone https://github.com/Tusharika725/brain-backend.git
cd brain-backend/backend
```

### Configure environment variables
This project expects these environment variables (see `application.properties`):

- `PORT` (optional, defaults to 8080)
- `SPRING_DATASOURCE_URL` (required)
- `SPRING_DATASOURCE_USERNAME` (required)
- `SPRING_DATASOURCE_PASSWORD` (required)
- `GEMINI_API_KEY` (required)

### Run
```bash
./mvnw spring-boot:run
```

Server starts on:
- `http://localhost:8080` (or whatever `PORT` you set)

---

## API Endpoints (from controllers)

### Health
- `GET /api/health` — basic health check  
  - Deployed example: `https://brain-backend-3.onrender.com/api/health`

### Users
- `POST /api/users/register` — register a new user
- `POST /api/users/login` — login

### Facts (FactStation)
- `GET /api/facts/random` — fetch a random fact

### Captions (AI Caption Writing)
- `GET /api/captions/image?category=...` — fetch a random image for a category
- `POST /api/captions/evaluate?email=...&category=...&caption=...&imageUrl=...` — evaluate caption with LLM and return rating + feedback  
  - `email` is optional

> CORS is configured on controllers to allow requests from: `https://upscroll-tau.vercel.app`

---

## Quick Demo (curl)

### Health
```bash
curl https://brain-backend-3.onrender.com/api/health
```

### Random fact
```bash
curl https://brain-backend-3.onrender.com/api/facts/random
```

### Get an image for a caption category
```bash
curl "https://brain-backend-3.onrender.com/api/captions/image?category=nature"
```

### Evaluate a caption (LLM)
```bash
curl -X POST "https://brain-backend-3.onrender.com/api/captions/evaluate?category=nature&caption=Sunsets%20hit%20different&imageUrl=https://example.com/image.jpg"
```

(You can also include `email=...` if you want.)

---

## Notes on the LLM feature (Gemini)

The AI Caption Writing flow:
1. Frontend sends `category`, `caption`, and the selected `imageUrl`
2. Backend calls Gemini to grade the caption and generate feedback
3. Backend returns a structured response to the frontend (rating + comments)

Gemini configuration:
- `GEMINI_API_KEY` (env var)
- Gemini endpoint is configured in `application.properties`

---

## Database

The backend uses Spring Data JPA and PostgreSQL. Hibernate is configured with:
- `spring.jpa.hibernate.ddl-auto=update` (creates/updates tables automatically)

---

## Deployment

The backend is deployed on Render:
- Base URL: https://brain-backend-3.onrender.com

The frontend is deployed at:
- https://upscroll-tau.vercel.app/
