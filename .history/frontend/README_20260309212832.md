# GigShield AI Frontend

This is the React frontend for the GigShield AI parametric insurance platform.

## Overview

- Built with **Vite + React** and styled with **Tailwind CSS**
- Uses **React Router** for navigation
- Communicates with backend via **Axios** (configured in `src/services/api.js`)
- Provides pages for worker registration, plan selection, dashboard, and payout calculator

## Setup & Development

1. **Install dependencies**
   ```sh
   cd frontend
   npm install
   ```

2. **Run development server**
   ```sh
   npm run dev
   ```
   The app will be available at `http://localhost:3000` by default.

3. **Environment variables**
   - Copy `.env.example` to `.env` if you need to override the API URL
   - By default the proxy in `vite.config.js` routes `/api` calls to `http://localhost:8080`

4. **Linting**
   ```sh
   npm run lint
   ```

## Folder Structure

```
frontend/
├── public/             # static assets (empty by default)
├── src/
│   ├── components/     # reusable UI components
│   ├── pages/          # route-level page components
│   ├── services/       # API client
│   ├── App.jsx         # router setup
│   ├── main.jsx        # entrypoint
│   └── index.css       # Tailwind imports & custom styles
├── vite.config.js      # Vite configuration & proxy
├── tailwind.config.js  # Tailwind configuration
└── package.json        # dependencies & scripts
```

## Backend Integration

The frontend expects a running backend API at `/api/*`. Configure `VITE_API_URL` in `.env` or adjust the proxy in `vite.config.js` to point at the appropriate host/port.

Example endpoints used in the app:

- `POST /workers/register`
- `POST /policies/create`
- `GET /policies/{workerId}`
- `POST /claims/trigger`
- `GET /claims/{workerId}`
- `POST /payout/calculate`

## Next Steps

- Implement form validation enhancements
- Add authentication/session handling
- Connect to AI engine when available for risk alerts
- Add unit/integration tests with Jest & React Testing Library

Happy hacking! 🚀
