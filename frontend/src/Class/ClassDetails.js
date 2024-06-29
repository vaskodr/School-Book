import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const ClassDetails = () => {
    const { schoolId, classId } = useParams();
    const [classDetails, setClassDetails] = useState(null);
    const { authData } = useContext(AuthContext);

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

    return (
        <div>
            <h2>Class Details</h2>
            {classDetails ? (
                <div>
                    <h3>{classDetails.name}</h3>
                    <p>Level: {classDetails.level}</p>
                    {/* Add more class details here */}
                </div>
            ) : (
                <p>Loading class details...</p>
            )}
        </div>
    );
};

export default ClassDetails;