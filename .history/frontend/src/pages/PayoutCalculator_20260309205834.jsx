import { useState } from 'react'
import { payoutAPI } from '../services/api'

export default function PayoutCalculator() {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [result, setResult] = useState(null)
  const [formData, setFormData] = useState({
    dailyIncome: '',
    workingHours: '',
    disruptionDuration: '',
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }))
  }

  const handleCalculate = async (e) => {
    e.preventDefault()
    
    if (!formData.dailyIncome || !formData.workingHours || !formData.disruptionDuration) {
      setError('Please fill in all fields')
      return
    }

    setLoading(true)
    setError('')
    setResult(null)

    try {
      const response = await payoutAPI.calculate({
        dailyIncome: parseFloat(formData.dailyIncome),
        workingHours: parseFloat(formData.workingHours),
        disruptionDuration: parseFloat(formData.disruptionDuration),
      })
      setResult(response.data)
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to calculate payout. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 py-12">
      <div className="max-w-2xl mx-auto px-4">
        <h1 className="text-4xl font-bold text-center mb-4">Smart Payout Calculator</h1>
        <p className="text-center text-gray-600 mb-12">
          Calculate your potential compensation based on your income and disruption duration
        </p>

        <div className="grid md:grid-cols-2 gap-8">
          {/* Calculator Form */}
          <div className="card">
            <h2 className="text-2xl font-bold mb-6">Enter Details</h2>

            {error && (
              <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                {error}
              </div>
            )}

            <form onSubmit={handleCalculate} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Daily Average Income (₹)
                </label>
                <input
                  type="number"
                  name="dailyIncome"
                  value={formData.dailyIncome}
                  onChange={handleChange}
                  step="0.01"
                  min="0"
                  className="input-field"
                  placeholder="1500"
                />
                <p className="text-xs text-gray-500 mt-1">Your average daily earnings</p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Working Hours per Day
                </label>
                <input
                  type="number"
                  name="workingHours"
                  value={formData.workingHours}
                  onChange={handleChange}
                  step="0.5"
                  min="0"
                  className="input-field"
                  placeholder="8"
                />
                <p className="text-xs text-gray-500 mt-1">Your typical daily working hours</p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Disruption Duration (hours)
                </label>
                <input
                  type="number"
                  name="disruptionDuration"
                  value={formData.disruptionDuration}
                  onChange={handleChange}
                  step="0.5"
                  min="0"
                  className="input-field"
                  placeholder="4"
                />
                <p className="text-xs text-gray-500 mt-1">Hours during which work was disrupted</p>
              </div>

              <button
                type="submit"
                disabled={loading}
                className="w-full btn-primary disabled:opacity-50 mt-6"
              >
                {loading ? 'Calculating...' : 'Calculate Payout'}
              </button>
            </form>

            <div className="mt-6 bg-blue-50 border border-blue-200 rounded-lg p-4">
              <h4 className="font-semibold text-blue-900 mb-2">Formula:</h4>
              <p className="text-sm text-blue-800">
                Payout = (Daily Income ÷ Working Hours) × Lost Hours
              </p>
            </div>
          </div>

          {/* Result Display */}
          <div>
            {result ? (
              <div className="card bg-gradient-to-br from-green-50 to-emerald-50 border-2 border-green-200">
                <h2 className="text-2xl font-bold text-green-700 mb-6">Calculation Result</h2>

                <div className="space-y-4">
                  <div className="bg-white rounded-lg p-4">
                    <p className="text-gray-600 text-sm">Hourly Rate</p>
                    <p className="text-3xl font-bold text-blue-600">
                      ₹{(result.dailyIncome / result.workingHours).toFixed(2)}
                    </p>
                  </div>

                  <div className="bg-white rounded-lg p-4">
                    <p className="text-gray-600 text-sm">Lost Income</p>
                    <p className="text-3xl font-bold text-orange-600">
                      ₹{(result.hourlyRate * result.disruptionDuration).toFixed(2)}
                    </p>
                  </div>

                  <div className="bg-green-100 rounded-lg p-4 border-2 border-green-300">
                    <p className="text-gray-700 text-sm font-medium">Estimated Payout</p>
                    <p className="text-4xl font-bold text-green-700">₹{result.estimatedPayout.toFixed(2)}</p>
                  </div>

                  <div className="bg-gray-50 rounded-lg p-3 text-xs text-gray-700 space-y-1">
                    <p>📊 Based on:</p>
                    <p>• Daily Income: ₹{result.dailyIncome.toFixed(2)}</p>
                    <p>• Working Hours: {result.workingHours}h</p>
                    <p>• Disruption Duration: {result.disruptionDuration}h</p>
                  </div>
                </div>

                <button
                  onClick={() => {
                    setResult(null)
                    setFormData({
                      dailyIncome: '',
                      workingHours: '',
                      disruptionDuration: '',
                    })
                  }}
                  className="w-full btn-secondary mt-6"
                >
                  Calculate Again
                </button>
              </div>
            ) : (
              <div className="card bg-gradient-to-br from-gray-50 to-gray-100 border-2 border-dashed border-gray-300 h-full flex items-center justify-center">
                <div className="text-center">
                  <p className="text-5xl mb-4">📱</p>
                  <p className="text-gray-600 font-medium">
                    Enter your details and calculate your potential payout
                  </p>
                </div>
              </div>
            )}
          </div>
        </div>

        {/* Information Boxes */}
        <div className="mt-12 grid md:grid-cols-3 gap-6">
          <div className="card">
            <h3 className="text-lg font-bold text-blue-600 mb-2">📝 How It Works</h3>
            <p className="text-sm text-gray-700">
              Our calculator multiplies your hourly rate by the duration of disruption to determine compensation.
            </p>
          </div>
          <div className="card">
            <h3 className="text-lg font-bold text-blue-600 mb-2">✅ Example</h3>
            <p className="text-sm text-gray-700">
              Income: ₹1,600 | Hours: 8 | Disruption: 4h = ₹800 payout
            </p>
          </div>
          <div className="card">
            <h3 className="text-lg font-bold text-blue-600 mb-2">🛡️ Coverage</h3>
            <p className="text-sm text-gray-700">
              Coverage limits depend on your selected plan (Basic: ₹10k, Standard: ₹25k, Pro: ₹50k)
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}
