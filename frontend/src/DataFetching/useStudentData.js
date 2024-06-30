import { useEffect, useState } from 'react';

const useStudentData = (studentId, authData) => {
  const [schoolName, setSchoolName] = useState('');
  const [studentData, setStudentData] = useState(null);
  const [program, setProgram] = useState(null);

  useEffect(() => {
    if (!authData || !studentId) {
      console.error('Auth data or student ID is not available');
      return;
    }

    const fetchSchoolName = async (schoolId) => {
      try {
        const response = await fetch(`http://localhost:8080/api/school/${schoolId}`, {
          headers: {
            Authorization: `Bearer ${authData.accessToken}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setSchoolName(data.name);
        } else {
          console.error('Failed to fetch school name');
        }
      } catch (error) {
        console.error('Error fetching school name:', error);
      }
    };

    const fetchStudentData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/student/${studentId}`, {
          headers: {
            Authorization: `Bearer ${authData.accessToken}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setStudentData(data);

          if (data.classDTO && data.classDTO.id) {
            fetchProgramData(data.classDTO.id, data.classDTO.schoolId);
          }
        } else {
          console.error('Failed to fetch student data');
        }
      } catch (error) {
        console.error('Error fetching student data:', error);
      }
    };

    const fetchProgramData = async (classId, schoolId) => {
      try {
        const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/program`, {
          headers: {
            Authorization: `Bearer ${authData.accessToken}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setProgram(data);
        } else {
          console.error('Failed to fetch program data');
        }
      } catch (error) {
        console.error('Error fetching program data:', error);
      }
    };

    if (studentData && studentData.classDTO) {
      fetchSchoolName(studentData.classDTO.schoolId);
    }

    fetchStudentData();
  }, [studentId, authData]);

  return { schoolName, studentData, program };
};

export default useStudentData;
