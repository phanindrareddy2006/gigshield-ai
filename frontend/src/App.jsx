import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Register from './pages/Register'
import Plans from './pages/Plans'
import Dashboard from './pages/Dashboard'
import PayoutCalculator from './pages/PayoutCalculator'

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <Routes>
          <Route path="/" element={<Register />} />
          <Route path="/plans" element={<Plans />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/calculator" element={<PayoutCalculator />} />
        </Routes>
      </div>
    </Router>
  )
}

export default App
