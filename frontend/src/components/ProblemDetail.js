import React from 'react';
import { useParams } from 'react-router-dom';

function ProblemDetail() {
  let { id } = useParams();
  return (
    <div>
      <h1>Problem {id}</h1>
      <p>Problem details go here...</p>
    </div>
  );
}

export default ProblemDetail;
