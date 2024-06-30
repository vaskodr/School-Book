import React, { useEffect, useState } from 'react';
import { useAuth } from '../Auth/AuthContext';
import StudentButtons from '../UI/Student/StudentButtons/StudentButtons';
import StudentInfo from '../UI/Student/StudentInfo/StudentInfo';
import 'bootstrap/dist/css/bootstrap.min.css';
import './style/ParentDashboard.css'; // Import custom CSS

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
      <div className="container mt-5">
        <div className="row">
          <div className="col-md-4">
            <div className="card mb-4 shadow-sm">
              <div className="card-body">
                <h3 className="card-title">Welcome, {firstName} {lastName}</h3>
                <p className="card-text">Select a student to view their information:</p>
                <div className="list-group">
                  {children.map((student) => (
                      <button
                          key={student.id}
                          className={`list-group-item list-group-item-action btn mb-2 ${selectedStudent && selectedStudent.id === student.id ? 'selected' : ''}`}
                          onClick={() => handleStudentClick(student)}
                      >
                        {student.firstName} {student.lastName}
                      </button>
                  ))}
                </div>
              </div>
            </div>
          </div>
          <div className="col-md-8">
            {selectedStudent && (
                <div className="card mb-4 shadow-sm">
                  <div className="card-header bg-primary text-white">
                    <h4 className="my-0">Student Information</h4>
                  </div>
                  <div className="card-body">
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
                </div>
            )}
          </div>
        </div>
      </div>
  );
};

export default ParentDashboard;