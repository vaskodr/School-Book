import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../Auth/AuthContext';

const ParentDashboard = () => {
  const { authData } = useAuth();
  const navigate = useNavigate();

  const handleStudentClick = (studentId) => {
    navigate(`/parent/student/${studentId}/dashboard`);
  };

  return (
    <div>
      <h1>Parent Dashboard</h1>
      <p>Select a student to view their information:</p>
      <ul>
        {authData.children.map((student) => (
          <li key={student.id}>
            <button onClick={() => handleStudentClick(student.id)}>
              {student.firstName} {student.lastName}
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ParentDashboard;
