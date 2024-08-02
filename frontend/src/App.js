import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './components/Home';  // 새로운 Home 컴포넌트 추가
import ProblemList from './components/ProblemList';  // 기존 ProblemList 컴포넌트 사용
import ProblemDetail from './components/ProblemDetail';
import SignIn from './components/SignIn'; // SignIn 컴포넌트를 import
import SignUp from './components/SignUp'; // SignUp 컴포넌트를 import

function App() {
  return (
    <Router>
      <div>
        <Header />
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/problems" element={<ProblemList />} />
            <Route path="/problem/:id" element={<ProblemDetail />} />
            <Route path="/signin" element={<SignIn />} /> 
            <Route path="/signup" element={<SignUp />} /> 
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
