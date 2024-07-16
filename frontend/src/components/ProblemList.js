import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ProblemList() {
  const [problems, setProblems] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:5000/problems')
      .then(response => {
        setProblems(response.data);
      })
      .catch(error => {
        console.error("There was an error fetching the problems!", error);
      });
  }, []);

  return (
    <div>
      <h1>Problems</h1>
      <ul>
        {problems.map(problem => (
          <li key={problem._id}>
            <a href={`/problem/${problem._id}`}>{problem.title} - {problem.level}</a>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ProblemList;
