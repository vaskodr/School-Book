// src/School/SchoolTeachers.js
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

    return (
        <div>
            <h2>Teachers for School {schoolId}</h2>
            <button onClick={() => navigate(-1)}>Back</button>
            {teachers.length > 0 ? (
                <ul>
                    {teachers.map((teacher) => (
                        <li key={teacher.id}>
                            {teacher.firstName} {teacher.lastName}
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