import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
import { FaSearch } from 'react-icons/fa';

function Header() {
  return (
    <header>
      <nav className="nav-container">
        <div className="logo">
          <Link to="/">AlgoMaster</Link>
        </div>
        <ul className="menu">
          <li className="dropdown">
            <span>Problems</span>
            <ul className="dropdown-content">
              <li><Link to="/problems">View Problems</Link></li>
              <li><Link to="/add-problem">Add Problem</Link></li>
            </ul>
          </li>
          <li>
            <Link to="/ranking">Ranking</Link>
          </li>
          <li>
            <Link to="/board">Board</Link>
          </li>
          <li className="search">
            <Link to="/search"><FaSearch /></Link>
          </li>
          <li>
            <Link to="/signin" className="nav-link">Sign In</Link>
          </li>
          <li>
            <Link to="/signup" className="nav-link">Sign Up</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default Header;
