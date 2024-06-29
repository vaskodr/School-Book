import React from 'react';
import { useParams } from 'react-router-dom';

const StudentGrades = () => {
  const { studentId } = useParams();
  return (
    <div>
      <h1>Grades for Student {studentId}</h1>
      {/* Display grades here */}
    </div>
  );
};

export default StudentGrades;
