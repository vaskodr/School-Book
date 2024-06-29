import React from 'react';
import { useParams } from 'react-router-dom';

const StudentProgram = () => {
  const { studentId } = useParams();
  return (
    <div>
      <h1>School Program for Student {studentId}</h1>
      {/* Display school program here */}
    </div>
  );
};

export default StudentProgram;
