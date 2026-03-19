import { Link, useNavigate } from 'react-router-dom'
import { workerAPI } from '../services/api'

export default function Navbar() {
  const navigate = useNavigate()
  const workerId = localStorage.getItem('workerId')

  const logout = () => {
    localStorage.removeItem('workerId')
    localStorage.removeItem('workerName')
    workerAPI.logout().catch(() => {})
    navigate('/')
  }

  return (
    <nav className="bg-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to="/" className="text-2xl font-bold text-blue-600">
              🛡️ GigShield AI
            </Link>
          </div>
          <div className="flex items-center space-x-8">
            <Link to="/" className="text-gray-700 hover:text-blue-600 font-medium">
              Register
            </Link>
            <Link to="/plans" className="text-gray-700 hover:text-blue-600 font-medium">
              Plans
            </Link>
            <Link to="/dashboard" className="text-gray-700 hover:text-blue-600 font-medium">
              Dashboard
            </Link>
            <Link to="/calculator" className="text-gray-700 hover:text-blue-600 font-medium">
              Calculator
            </Link>
            {workerId && (
              <button onClick={logout} className="text-red-600 hover:text-red-800 font-medium">
                Logout
              </button>
            )}
          </div>
        </div>
      </div>
    </nav>
  )
}
