import React, { useEffect, useState, useContext, useCallback } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import { useNavigate, Outlet, useLocation } from 'react-router-dom';

const AdminDashboard = () => {
    const [schools, setSchools] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();
    const location = useLocation();

    const fetchSchools = useCallback(async () => {
        try {
            const response = await fetch('http://localhost:8080/api/school/all', {
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
    }, [authData.accessToken]);

    useEffect(() => {
        fetchSchools();
    }, [fetchSchools, location.pathname]);

    const handleSchoolClick = (schoolId) => {
        navigate(`/admin/dashboard/school/${schoolId}/classes`);
    };

    const handleEditSchool = (schoolId) => {
        navigate(`/admin/dashboard/school/${schoolId}/update`);
    };

    const handleDeleteSchool = async (schoolId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                fetchSchools(); // Re-fetch schools after deletion
            } else {
                console.error('Failed to delete school');
            }
        } catch (error) {
            console.error('Error deleting school:', error);
        }
    };

    const handleCreateSchool = () => {
        navigate('/admin/dashboard/school/create');
    };

    const handleSchoolDetails = (schoolId) => {
        navigate(`/admin/dashboard/school/${schoolId}/classes`);
    };

    return (
        <div>
            <h2>Admin Dashboard</h2>
            <button onClick={handleCreateSchool}>Create New School</button>
            <p> Select a school: </p>
            {schools.length > 0 ? (
                <div>
                    {schools.map((school) => (
                        <div key={school.id}>
                            <span onClick={() => handleSchoolClick(school.id)}>{school.name}</span>
                            <button onClick={() => handleEditSchool(school.id)}>Edit</button>
                            <button onClick={() => handleDeleteSchool(school.id)}>Delete</button>
                            <button onClick={() => handleSchoolDetails(school.id)}>Details</button>
                        </div>
                    ))}
                </div>
            ) : (
                <p>Loading schools...</p>
            )}
            <Outlet />
        </div>
    );
};

export default AdminDashboard;