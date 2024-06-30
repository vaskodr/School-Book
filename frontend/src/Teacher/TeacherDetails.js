// src/Teacher/TeacherDetails.js
import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const TeacherDetails = () => {
    const { schoolId, teacherId } = useParams();
    const [teacherDetails, setTeacherDetails] = useState(null);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTeacherDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched teacher details:', data); // Add logging here
                    setTeacherDetails(data);
                } else {
                    console.error('Failed to fetch teacher details');
                }
            } catch (error) {
                console.error('Error fetching teacher details:', error);
            }
        };

        fetchTeacherDetails();
    }, [schoolId, teacherId, authData]);

    if (!teacherDetails) {
        return <p>Loading teacher details...</p>;
    }

    return (
        <div className="container mt-4">
            <h2>Teacher Details</h2>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{teacherDetails.firstName} {teacherDetails.lastName}</h5>
                    <p className="card-text"><strong>Date of Birth:</strong> {teacherDetails.dateOfBirth}</p>
                    <p className="card-text"><strong>Gender:</strong> {teacherDetails.gender}</p>
                    <p className="card-text"><strong>Phone:</strong> {teacherDetails.phone}</p>
                    <p className="card-text"><strong>Email:</strong> {teacherDetails.email}</p>
                    <p className="card-text"><strong>Username:</strong> {teacherDetails.username}</p>
                    <p className="card-text"><strong>School:</strong> {teacherDetails.schoolName}</p>
                    <h5>Subjects</h5>
                    <ul className="list-group">
                        {teacherDetails.subjectNames && teacherDetails.subjectNames.length > 0 ? (
                            teacherDetails.subjectNames.map((subject, index) => (
                                <li key={index} className="list-group-item">
                                    {subject}
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No subjects available</li>
                        )}
                    </ul>
                    <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>Back</button>
                </div>
            </div>
        </div>
    );
};

export default TeacherDetails;