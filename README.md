# ScamGuard AI

ScamGuard AI is a full-stack scam detection and risk analysis application built with Java Spring Boot and React.

## Project structure

- `backend/` — Spring Boot API and JPA persistence
- `frontend/` — React + Vite client application

## Run the backend

```bash
cd backend
mvn spring-boot:run
```

## Run the frontend

```bash
cd frontend
npm install
npm run dev
```

## Notes

- Frontend expects the backend at `http://localhost:8080`.
- MySQL is configured with default credentials `root` / `root` and database name `scamguard_ai`.
- The app uses a detection engine that reviews urgency, financial pressure, phishing patterns, impersonation, credential theft, and suspicious URLs.
