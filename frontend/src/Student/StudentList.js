import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const StudentsList = () => {
    const { schoolId, classId } = useParams();
    const [students, setStudents] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setStudents(data);
                } else {
                    console.error('Failed to fetch students');
                }
            } catch (error) {
                console.error('Error fetching students:', error);
            }
        };

        fetchStudents();
    }, [schoolId, classId, authData]);

    const handleAddStudentClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/student/add`);
    };

    const handleEditStudent = (studentId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/student/${studentId}/update`);
    };

    const handleDeleteStudent = async (studentId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/${studentId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setStudents(students.filter(student => student.id !== studentId));
            } else {
                console.error('Failed to delete student');
            }
        } catch (error) {
            console.error('Error deleting student:', error);
        }
    };

    const handleStudentDetails = (studentId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/student/${studentId}/details`);
    };

    return (
        <div className="container mt-4">
            <h2>Students in Class</h2>
            <button className="btn btn-secondary mb-3" onClick={() => navigate(-1)}>Back to Class Details</button>
            <button className="btn btn-primary mb-3" onClick={handleAddStudentClick}>Add Student</button>
            {students.length > 0 ? (
                <ul className="list-group">
                    {students.map((student) => (
                        <li key={student.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {student.firstName} {student.lastName}
                            <div>
                                <button className="btn btn-secondary btn-sm me-2" onClick={() => handleEditStudent(student.id)}>Edit</button>
                                <button className="btn btn-danger btn-sm me-2" onClick={() => handleDeleteStudent(student.id)}>Delete</button>
                                <button className="btn btn-info btn-sm" onClick={() => handleStudentDetails(student.id)}>Details</button>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Loading students...</p>
            )}
        </div>
    );
};

export default StudentsList;