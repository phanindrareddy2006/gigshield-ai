import { Link } from 'react-router-dom'

export default function Navbar() {
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
          </div>
        </div>
      </div>
    </nav>
  )
}
