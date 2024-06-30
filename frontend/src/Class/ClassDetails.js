import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const ClassDetails = () => {
    const { schoolId, classId } = useParams();
    const [classDetails, setClassDetails] = useState({});
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClassDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setClassDetails(data);
                } else {
                    console.error('Failed to fetch class details');
                }
            } catch (error) {
                console.error('Error fetching class details:', error);
            }
        };

        fetchClassDetails();
    }, [schoolId, classId, authData]);

    const handleViewStudentsClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/students`);
    };

    return (
        <div className="container mt-4">
            <h2>Class Details for {classDetails.name}</h2>
            <p><strong>Name:</strong> {classDetails.name}</p>
            <p><strong>Level:</strong> {classDetails.level}</p>
            <p><strong>School:</strong> {classDetails.schoolName}</p>
            <button className="btn btn-primary me-2" onClick={handleViewStudentsClick}>View All Students</button>
            <button className="btn btn-secondary" onClick={() => navigate(-1)}>Back to Classes</button>
        </div>
    );
};

export default ClassDetails;