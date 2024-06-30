import React, { useEffect, useState } from 'react';
import { useAuth } from '../Auth/AuthContext';
import StudentButtons from '../UI/Student/StudentButtons/StudentButtons';
import StudentInfo from '../UI/Student/StudentInfo/StudentInfo';

const ParentDashboard = () => {
  const { authData } = useAuth();
  const [children, setChildren] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState(null);

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
        } else {
          console.error('Failed to fetch children data');
        }
      } catch (error) {
        console.error('Error fetching children data:', error);
      }
    };

    fetchChildren();
  }, [authData]);

  const handleStudentClick = (student) => {
    setSelectedStudent(student);
  };

  const { firstName, lastName } = authData.userDetailsDTO;

  return (
    <div>
      <h1>Parent Dashboard</h1>
      <h1>Welcome {firstName} {lastName}</h1>
      <p>Select a student to view their information:</p>
      <ul>
        {children.map((student) => (
          <li key={student.id}>
            <button onClick={() => handleStudentClick(student)}>
              {student.firstName} {student.lastName}
            </button>
          </li>
        ))}
      </ul>
      {selectedStudent && (
        <div>
          <StudentInfo
            firstName={selectedStudent.firstName}
            lastName={selectedStudent.lastName}
            classDTO={selectedStudent.classDTO}
          />
          <StudentButtons
            schoolId={selectedStudent.classDTO.schoolId}
            classId={selectedStudent.classDTO.id}
            authData={authData}
            studentId={selectedStudent.id}
          />
        </div>
      )}
    </div>
  );
};

export default ParentDashboard;
