import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const SchoolTeachers = () => {
    const { schoolId } = useParams();
    const [teachers, setTeachers] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTeachers = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setTeachers(data);
                } else {
                    console.error('Failed to fetch teachers');
                }
            } catch (error) {
                console.error('Error fetching teachers:', error);
            }
        };

        fetchTeachers();
    }, [schoolId, authData]);

    const handleAddTeacherClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/teacher/add`);
    };

    const handleEditTeacher = (teacherId) => {
        navigate(`/admin/dashboard/school/${schoolId}/teacher/${teacherId}/update`);
    };

    const handleDeleteTeacher = async (teacherId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setTeachers(teachers.filter(teacher => teacher.id !== teacherId));
            } else {
                console.error('Failed to delete teacher');
            }
        } catch (error) {
            console.error('Error deleting teacher:', error);
        }
    };

    const handleViewTeacherDetails = (teacherId) => {
        navigate(`/admin/dashboard/school/${schoolId}/teacher/${teacherId}/details`);
    };

    return (
        <div className="container mt-4">
            <h2>Teachers in School</h2>
            <button className="btn btn-secondary mb-3" onClick={() => navigate(-1)}>Back to School Details</button>
            <button className="btn btn-primary mb-3" onClick={handleAddTeacherClick}>Add Teacher</button>
            {teachers.length > 0 ? (
                <ul className="list-group">
                    {teachers.map((teacher) => (
                        <li key={teacher.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {teacher.firstName} {teacher.lastName}
                            <div>
                                <button className="btn btn-secondary btn-sm me-2" onClick={() => handleEditTeacher(teacher.id)}>Edit</button>
                                <button className="btn btn-danger btn-sm me-2" onClick={() => handleDeleteTeacher(teacher.id)}>Delete</button>
                                <button className="btn btn-info btn-sm" onClick={() => handleViewTeacherDetails(teacher.id)}>Details</button>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Loading teachers...</p>
            )}
        </div>
    );
};

export default SchoolTeachers;