import axios from 'axios'

// Vite exposes environment variables under import.meta.env
// fallback to localhost if not provided
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8010/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Worker endpoints
export const workerAPI = {
  register: (workerData) => api.post('/workers/register', workerData),
  getById: (id) => api.get(`/workers/${id}`),
}

// Policy endpoints
export const policyAPI = {
  create: (policyData) => api.post('/policies/create', policyData),
  getByWorker: (workerId) => api.get(`/policies/${workerId}`),
}

// Claim endpoints
export const claimAPI = {
  trigger: (claimData) => api.post('/claims/trigger', claimData),
  getByWorker: (workerId) => api.get(`/claims/${workerId}`),
}

// Payout endpoints
export const payoutAPI = {
  calculate: (payoutData) => api.post('/payout/calculate', payoutData),
}

export default api
