import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const StudentDetails = () => {
    const { schoolId, classId, studentId } = useParams();
    const [studentDetails, setStudentDetails] = useState(null);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchStudentDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/${studentId}/details`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched student details:', data); // Add logging here
                    setStudentDetails(data);
                } else {
                    console.error('Failed to fetch student details');
                }
            } catch (error) {
                console.error('Error fetching student details:', error);
            }
        };

        fetchStudentDetails();
    }, [schoolId, classId, studentId, authData]);

    if (!studentDetails) {
        return <p>Loading student details...</p>;
    }

    return (
        <div className="container mt-4">
            <h2>Student Details</h2>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{studentDetails.firstName} {studentDetails.lastName}</h5>
                    <p className="card-text"><strong>Date of Birth:</strong> {studentDetails.dateOfBirth}</p>
                    <p className="card-text"><strong>Gender:</strong> {studentDetails.gender}</p>
                    <p className="card-text"><strong>Phone:</strong> {studentDetails.phone}</p>
                    <p className="card-text"><strong>Email:</strong> {studentDetails.email}</p>
                    <p className="card-text"><strong>Username:</strong> {studentDetails.username}</p>
                    <p className="card-text"><strong>School:</strong> {studentDetails.schoolName}</p>
                    <p className="card-text"><strong>Class:</strong> {studentDetails.classDTO.name} ({studentDetails.classDTO.level})</p>
                    <h5>Parents</h5>
                    <ul className="list-group">
                        {studentDetails.parents && studentDetails.parents.length > 0 ? (
                            studentDetails.parents.map((parent) => (
                                <li key={parent.id} className="list-group-item">
                                    <p><strong>Name:</strong> {parent.firstName} {parent.lastName}</p>
                                    <p><strong>Phone:</strong> {parent.phone}</p>
                                    <p><strong>Email:</strong> {parent.email}</p>
                                    <p><strong>Username:</strong> {parent.username}</p>
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No parents available</li>
                        )}
                    </ul>
                    <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>Back</button>
                </div>
            </div>
        </div>
    );
};

export default StudentDetails;