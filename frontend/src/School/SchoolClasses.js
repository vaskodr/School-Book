import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const SchoolClasses = () => {
    const { schoolId } = useParams();
    const [classes, setClasses] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setClasses(data);
                } else {
                    console.error('Failed to fetch classes');
                }
            } catch (error) {
                console.error('Error fetching classes:', error);
            }
        };

        fetchClasses();
    }, [schoolId, authData]);

    const handleClassClick = (classId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}`);
    };

    return (
        <div>
            <h2>Classes for School {schoolId}</h2>
            {classes.length > 0 ? (
                <div>
                    {classes.map((classItem) => (
                        <button key={classItem.id} onClick={() => handleClassClick(classItem.id)}>
                            {classItem.name}
                        </button>
                    ))}
                </div>
            ) : (
                <p>Loading classes...</p>
            )}
        </div>
    );
};

export default SchoolClasses;