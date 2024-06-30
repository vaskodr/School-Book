import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import StudentButtons from '../UI/Student/StudentButtons/StudentButtons';
import StudentInfo from '../UI/Student/StudentInfo/StudentInfo';

const StudentDashboard = () => {
  const [studentData, setStudentData] = useState(null);
  const { schoolId } = useParams();
  const { authData } = useContext(AuthContext);

  useEffect(() => {
    console.log('Auth data in useEffect:', authData); // Debugging log

    if (!authData || !authData.userDetailsDTO) {
      console.error('Auth data or userDetailsDTO is not available');
      return;
    }

    const fetchStudentData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/student/${authData.userDetailsDTO.id}`, {
          headers: {
            Authorization: `Bearer ${authData.accessToken}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          console.log('Fetched student data:', data); // Debugging log
          setStudentData(data);
        } else {
          console.error('Failed to fetch student data');
        }
      } catch (error) {
        console.error('Error fetching student data:', error);
      }
    };

    fetchStudentData();
  }, [schoolId, authData]);

  if (!authData || !authData.userDetailsDTO) {
    return <div>Loading auth data...</div>;
  }

  const { firstName, lastName } = authData.userDetailsDTO;

  return (
    <div>
        <StudentInfo
        firstName={firstName}
        lastName={lastName}
        classDTO={studentData?.classDTO}
      />
      {studentData && studentData.classDTO ? (
        <StudentButtons
          schoolId={schoolId}
          classId={studentData.classDTO.id}
          authData={authData}
          studentId={studentData?.id}
        />
      ) : (
        <p>Loading program data...</p>
      )}
    </div>
  );
};

export default StudentDashboard;
