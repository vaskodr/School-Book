import React, { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import { useNavigate } from 'react-router-dom';

const AdminDashboard = () => {
    const [schools, setSchools] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSchools = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/school', {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setSchools(data);
                } else {
                    console.error('Failed to fetch schools');
                }
            } catch (error) {
                console.error('Error fetching schools:', error);
            }
        };

        fetchSchools();
    }, [authData]);

    const handleSchoolClick = (schoolId) => {
        navigate(`/admin/dashboard/school/${schoolId}/classes`);
    };

    return (
        <div>
            <h2>Admin Dashboard</h2>
            {schools.length > 0 ? (
                <div>
                    {schools.map((school) => (
                        <button key={school.id} onClick={() => handleSchoolClick(school.id)}>
                            {school.name}
                        </button>
                    ))}
                </div>
            ) : (
                <p>Loading schools...</p>
            )}
        </div>
    );
};

export default AdminDashboard;