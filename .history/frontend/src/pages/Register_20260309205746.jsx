import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { workerAPI } from '../services/api'

export default function Register() {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [formData, setFormData] = useState({
    name: '',
    phone: '',
    platform: 'Swiggy',
    city: '',
    averageDailyIncome: '',
    workingHours: '',
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'averageDailyIncome' || name === 'workingHours' ? parseFloat(value) : value,
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')

    try {
      const response = await workerAPI.register(formData)
      localStorage.setItem('workerId', response.data.id)
      navigate('/plans')
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-blue-100">
      <div className="card w-full max-w-md">
        <h1 className="text-3xl text-center mb-8 text-blue-600">Worker Registration</h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              className="input-field"
              placeholder="Enter your full name"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Phone Number</label>
            <input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              required
              className="input-field"
              placeholder="+91 9999999999"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Delivery Platform</label>
            <select
              name="platform"
              value={formData.platform}
              onChange={handleChange}
              className="input-field"
            >
              <option>Swiggy</option>
              <option>Zomato</option>
              <option>Uber Eats</option>
              <option>Other</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">City</label>
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
              required
              className="input-field"
              placeholder="Enter your city"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Average Daily Income (₹)</label>
            <input
              type="number"
              name="averageDailyIncome"
              value={formData.averageDailyIncome}
              onChange={handleChange}
              required
              step="0.01"
              className="input-field"
              placeholder="1500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Working Hours per Day</label>
            <input
              type="number"
              name="workingHours"
              value={formData.workingHours}
              onChange={handleChange}
              required
              step="0.5"
              className="input-field"
              placeholder="8"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full btn-primary disabled:opacity-50"
          >
            {loading ? 'Registering...' : 'Register & Continue'}
          </button>
        </form>
      </div>
    </div>
  )
}
