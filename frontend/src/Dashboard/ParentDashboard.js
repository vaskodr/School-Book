import React, { useEffect, useState } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../Auth/AuthContext';

const ParentDashboard = () => {
  const { authData } = useAuth();
  const [children, setChildren] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchChildren = async () => {
      if (!authData || !authData.userDetailsDTO || !authData.userDetailsDTO.id) {
        console.error('Parent ID is not available');
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/api/parent/${authData.userDetailsDTO.id}/children`, {
          headers: {
            Authorization: `Bearer ${authData.accessToken}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setChildren(data);
          console.log(data);
        } else {
          console.error('Failed to fetch children data');
        }
      } catch (error) {
        console.error('Error fetching children data:', error);
      }
    };

    fetchChildren();
  }, [authData]);

  const handleStudentClick = (studentId) => {
    navigate(`/parent/dashboard/student/${studentId}/dashboard`);
  };

  return (
    <div>
      <h1>Parent Dashboard</h1>
      <p>Select a student to view their information:</p>
      <ul>
        {children.map((student) => (
          <li key={student.id}>
            <button onClick={() => handleStudentClick(student.id)}>
              {student.firstName} {student.lastName}
            </button>
          </li>
        ))}
      </ul>
      <Outlet/>
    </div>
  );
};

export default ParentDashboard;
