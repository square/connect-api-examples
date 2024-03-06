import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './routes/Home';
import CustomerOverview from './routes/CustomerOverview';
import { AppContext, AppDispatchContext, appReducer, initialState } from './context/AppContext';
import { useReducer } from 'react';
import NavBar from './components/Navbar';
import SubscriptionDetails from './routes/SubscriptionDetails';




const App = () => {
  const [state, dispatch] = useReducer(appReducer, initialState);
  return (
    <Router>
      <AppContext.Provider value={state}>
        <AppDispatchContext.Provider value={dispatch}>
      <div>
        <div className="min-h-screen flex flex-col bg-gray-100">
            <NavBar />
            {/* Main Content */}
            <main className="flex-1 container mx-auto my-8">
          <Routes>
            <Route path="/" element={
                <Home/>
            } />
            <Route path="/customer/:customerId" element={<CustomerOverview />} />
            <Route path="/subscription/:subscriptionId" element={<SubscriptionDetails/>} />
          </Routes>
            </main>
          </div>
      </div>
      </AppDispatchContext.Provider>
    </AppContext.Provider>
    </Router>
  );
}

export default App;
