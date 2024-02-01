import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './routes/Home';
import CustomerOverview from './routes/CustomerOverview';

const App = () => {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/customer/:customerId" element={<CustomerOverview />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
