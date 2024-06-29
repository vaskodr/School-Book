import React from 'react';
import { useParams } from 'react-router-dom';

const StudentAbsences = () => {
  const { studentId } = useParams();
  return (
    <div>
      <h1>Absences for Student {studentId}</h1>
      {/* Display absences here */}
    </div>
  );
};

export default StudentAbsences;
