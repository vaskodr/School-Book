import React, { useEffect, useState, useContext, useCallback } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import { useNavigate, Outlet, useLocation } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap is imported

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
        <div className="container mt-5">
            <div className="text-center">
                <h2>Admin Dashboard</h2>
                <button className="btn btn-primary my-3" onClick={handleCreateSchool}>Create New School</button>
            </div>
            <h4>Select a school:</h4>
            {schools.length > 0 ? (
                <div className="list-group">
                    {schools.map((school) => (
                        <div key={school.id} className="list-group-item d-flex justify-content-between align-items-center">
                            <span className="font-weight-bold" onClick={() => handleSchoolDetails(school.id)}>{school.name}</span>
                            <div>
                                <button className="btn btn-info btn-sm mx-1" onClick={() => handleSchoolDetails(school.id)}>Details</button>
                                <button className="btn btn-warning btn-sm mx-1" onClick={() => handleEditSchool(school.id)}>Edit</button>
                                <button className="btn btn-danger btn-sm mx-1" onClick={() => handleDeleteSchool(school.id)}>Delete</button>
                            </div>
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