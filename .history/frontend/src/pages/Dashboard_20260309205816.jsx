import { useState, useEffect } from 'react'
import { policyAPI, claimAPI } from '../services/api'

const RISK_ALERTS = [
  { type: 'rain', severity: 'high', message: 'Heavy rain expected in next 3 hours', time: '2 hours ago' },
  { type: 'pollution', severity: 'medium', message: 'Air quality index at 156 (Unhealthy)', time: '1 hour ago' },
  { type: 'curfew', severity: 'critical', message: 'Traffic curfew from 8 PM - 6 AM', time: '30 minutes ago' },
]

export default function Dashboard() {
  const [policy, setPolicy] = useState(null)
  const [claims, setClaims] = useState([])
  const [loading, setLoading] = useState(true)
  const workerId = localStorage.getItem('workerId')

  useEffect(() => {
    const fetchData = async () => {
      try {
        const policyResponse = await policyAPI.getByWorker(workerId)
        setPolicy(policyResponse.data)

        const claimsResponse = await claimAPI.getByWorker(workerId)
        setClaims(claimsResponse.data)
      } catch (err) {
        console.error('Failed to fetch data:', err)
      } finally {
        setLoading(false)
      }
    }

    if (workerId) {
      fetchData()
    }
  }, [workerId])

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl text-gray-600">Loading dashboard...</div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50 py-12">
      <div className="max-w-7xl mx-auto px-4">
        <h1 className="text-4xl font-bold mb-8">Dashboard</h1>

        <div className="grid md:grid-cols-3 gap-6 mb-8">
          {/* Active Policy Card */}
          <div className="card">
            <h3 className="text-lg font-semibold text-gray-700 mb-4">Active Policy</h3>
            {policy ? (
              <div>
                <p className="text-2xl font-bold text-green-600 mb-2">
                  {policy.planType?.toUpperCase()}
                </p>
                <p className="text-gray-600 mb-1">Premium: ₹{policy.monthlyPremium}/month</p>
                <p className="text-gray-600">Status: <span className="text-green-600 font-semibold">Active</span></p>
              </div>
            ) : (
              <p className="text-gray-600">No active policy</p>
            )}
          </div>

          {/* Recent Compensation Card */}
          <div className="card">
            <h3 className="text-lg font-semibold text-gray-700 mb-4">Recent Compensation</h3>
            <p className="text-2xl font-bold text-blue-600">₹2,450</p>
            <p className="text-gray-600 text-sm">Last compensated: 3 days ago</p>
            <p className="text-gray-600 text-sm">Reason: Heavy rainfall</p>
          </div>

          {/* Monthly Payout Limit */}
          <div className="card">
            <h3 className="text-lg font-semibold text-gray-700 mb-4">Payout Limit Used</h3>
            <p className="text-2xl font-bold text-orange-600">₹4,800 / ₹25,000</p>
            <div className="mt-3 bg-gray-200 rounded-full h-2">
              <div className="bg-orange-500 h-2 rounded-full" style={{ width: '19.2%' }}></div>
            </div>
          </div>
        </div>

        {/* Risk Alerts Section */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold mb-4">⚠️ Risk Alerts</h2>
          <div className="space-y-3">
            {RISK_ALERTS.map((alert, index) => (
              <div
                key={index}
                className={`card border-l-4 ${
                  alert.severity === 'critical'
                    ? 'border-red-500 bg-red-50'
                    : alert.severity === 'high'
                    ? 'border-orange-500 bg-orange-50'
                    : 'border-yellow-500 bg-yellow-50'
                }`}
              >
                <div className="flex justify-between items-start">
                  <div>
                    <p className="font-semibold text-gray-800">{alert.message}</p>
                    <p className="text-sm text-gray-600 mt-1">{alert.time}</p>
                  </div>
                  <span
                    className={`px-3 py-1 rounded text-sm font-medium ${
                      alert.severity === 'critical'
                        ? 'bg-red-200 text-red-800'
                        : alert.severity === 'high'
                        ? 'bg-orange-200 text-orange-800'
                        : 'bg-yellow-200 text-yellow-800'
                    }`}
                  >
                    {alert.severity.toUpperCase()}
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Recent Claims Section */}
        <div>
          <h2 className="text-2xl font-bold mb-4">Recent Claims</h2>
          <div className="card overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b">
                  <th className="text-left py-3 px-4 font-semibold">Date</th>
                  <th className="text-left py-3 px-4 font-semibold">Reason</th>
                  <th className="text-left py-3 px-4 font-semibold">Amount</th>
                  <th className="text-left py-3 px-4 font-semibold">Status</th>
                </tr>
              </thead>
              <tbody>
                {claims.length > 0 ? (
                  claims.map((claim, index) => (
                    <tr key={index} className="border-b hover:bg-gray-50">
                      <td className="py-3 px-4">{claim.dateCreated}</td>
                      <td className="py-3 px-4">{claim.disruptionType}</td>
                      <td className="py-3 px-4 font-semibold">₹{claim.claimAmount}</td>
                      <td className="py-3 px-4">
                        <span
                          className={`px-3 py-1 rounded text-sm font-medium ${
                            claim.status === 'APPROVED'
                              ? 'bg-green-100 text-green-800'
                              : claim.status === 'PENDING'
                              ? 'bg-yellow-100 text-yellow-800'
                              : 'bg-red-100 text-red-800'
                          }`}
                        >
                          {claim.status}
                        </span>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4" className="py-4 px-4 text-center text-gray-600">
                      No claims yet
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}
