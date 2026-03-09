export default function PlanCard({ plan, onSelect }) {
  return (
    <div className="card border-2 border-gray-200 hover:border-blue-500 transform hover:scale-105 transition-all">
      <div className="text-center mb-4">
        <h3 className="text-2xl font-bold text-gray-800">{plan.name}</h3>
        <p className="text-gray-600 mt-2">{plan.description}</p>
      </div>

      <div className="text-center mb-6">
        <span className="text-4xl font-bold text-blue-600">₹{plan.price}</span>
        <p className="text-gray-600 text-sm">per month</p>
      </div>

      <ul className="space-y-3 mb-6">
        {plan.features.map((feature, index) => (
          <li key={index} className="flex items-center text-gray-700">
            <span className="text-green-500 mr-2">✓</span>
            {feature}
          </li>
        ))}
      </ul>

      <button
        onClick={() => onSelect(plan)}
        className="w-full btn-primary"
      >
        Select {plan.name}
      </button>
    </div>
  )
}
