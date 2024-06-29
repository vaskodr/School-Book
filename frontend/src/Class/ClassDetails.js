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
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/class/${classId}`, {
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

    return (
        <div>
            <h2>Class Details for {classDetails.name}</h2>
            <p>ID: {classDetails.id}</p>
            <p>Name: {classDetails.name}</p>
            <p>Level: {classDetails.level}</p>
            {/* Add more details as needed */}
            <button onClick={() => navigate(-1)}>Back to Classes</button>
        </div>
    );
};

export default ClassDetails;