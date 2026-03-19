import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import PlanCard from '../components/PlanCard'
import { policyAPI } from '../services/api'

const PLANS = [
  {
    id: 'basic',
    name: 'Basic',
    description: 'Essential coverage for disruptions',
    price: 299,
    features: [
      'Up to ₹10,000 monthly payout',
      'Heavy rain coverage',
      'High pollution alert',
      '24/7 customer support',
      '7-day claim processing',
    ],
  },
  {
    id: 'standard',
    name: 'Standard',
    description: 'Comprehensive protection',
    price: 599,
    features: [
      'Up to ₹25,000 monthly payout',
      'All Basic features',
      'Curfew coverage',
      'Accident protection',
      'Priority claims processing',
      '24-hour claim settlement',
    ],
  },
  {
    id: 'pro',
    name: 'Pro',
    description: 'Maximum coverage',
    price: 999,
    features: [
      'Up to ₹50,000 monthly payout',
      'All Standard features',
      'Natural disaster coverage',
      'Hospitalization protection',
      'Income loss guarantee',
      '2-hour claim settlement',
      'Dedicated account manager',
    ],
  },
]

export default function Plans() {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [bubbleMessage, setBubbleMessage] = useState('')
  const workerId = localStorage.getItem('workerId')

  const handlePlanSelect = async (plan) => {
    setBubbleMessage('Feature will be available soon')
    setTimeout(() => setBubbleMessage(''), 3000)
  }

  if (!workerId) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">Please register first</h2>
          <button
            onClick={() => navigate('/')}
            className="btn-primary"
          >
            Go to Registration
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50 py-12 relative">
      {bubbleMessage && (
        <div className="fixed top-24 left-1/2 transform -translate-x-1/2 z-50 bg-blue-600 text-white px-6 py-3 rounded-full shadow-xl flex items-center space-x-2 animate-bounce">
          <span className="text-xl">ℹ️</span>
          <span className="font-semibold">{bubbleMessage}</span>
        </div>
      )}
      <div className="max-w-7xl mx-auto px-4">
        <h1 className="text-4xl font-bold text-center mb-4">Insurance Plans</h1>
        <p className="text-center text-gray-600 mb-12 max-w-2xl mx-auto">
          Choose a plan that best suits your needs. All plans include 24/7 support and instant claim processing.
        </p>

        {error && (
          <div className="max-w-7xl mx-auto mb-6 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            {error}
          </div>
        )}

        <div className="grid md:grid-cols-3 gap-8">
          {PLANS.map((plan) => (
            <PlanCard
              key={plan.id}
              plan={plan}
              onSelect={handlePlanSelect}
            />
          ))}
        </div>

        <div className="mt-12 bg-blue-50 border border-blue-200 rounded-lg p-6 max-w-4xl mx-auto">
          <h3 className="text-xl font-bold text-blue-900 mb-4">Why GigShield AI?</h3>
          <ul className="space-y-2 text-blue-800">
            <li>✓ AI-powered risk prediction for proactive alerts</li>
            <li>✓ Fair payout calculation based on actual income loss</li>
            <li>✓ Transparent pricing with no hidden charges</li>
            <li>✓ Instant claim settlement in most cases</li>
            <li>✓ Dedicated support for gig workers</li>
          </ul>
        </div>
      </div>
    </div>
  )
}
