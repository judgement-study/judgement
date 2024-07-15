import React from 'react';
import { Link } from 'react-router-dom';

const problems = [
  { id: 1, title: 'A + B', level: 'Easy' },
  { id: 2, title: 'Binary Search', level: 'Medium' },
  // 더 많은 문제들 추가
];

function ProblemList() {
  return (
    <div>
      <h1>Problems</h1>
      <ul>
        {problems.map(problem => (
          <li key={problem.id}>
            <Link to={`/problem/${problem.id}`}>{problem.title} - {problem.level}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ProblemList;
