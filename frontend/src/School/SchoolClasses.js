// src/School/SchoolClasses.js
import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate, Outlet } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const SchoolClasses = () => {
    const { schoolId } = useParams();
    const [classes, setClasses] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const fetchClasses = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/all`, {
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

    useEffect(() => {
        fetchClasses();
    }, [schoolId, authData]);

    const handleClassClick = (classId) => {
        navigate(`/admin/dashboard/school/${schoolId}/classes/${classId}`);
    };

    const handleAddClassClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/classes/create`);
    };

    const handleViewTeachersClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/teachers`);
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center">Classes for School {schoolId}</h2>
            <div className="d-flex justify-content-between mb-3">
                <Button variant="primary" onClick={handleAddClassClick}>
                    Add Class
                </Button>
                <Button variant="secondary" onClick={handleViewTeachersClick}>
                    View All Teachers
                </Button>
            </div>
            <Outlet />
            {classes.length > 0 ? (
                <div className="list-group">
                    {classes.map((classItem) => (
                        <div key={classItem.id} className="list-group-item d-flex justify-content-between align-items-center">
                            <span className="font-weight-bold">{classItem.name} - {classItem.level}</span>
                            <div>
                                <Button variant="info" size="sm" onClick={() => handleClassClick(classItem.id)}>Details</Button>
                                <Button variant="warning" size="sm" className="mx-1">Edit</Button>
                                <Button variant="danger" size="sm">Delete</Button>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <p className="text-center">Loading classes...</p>
            )}
        </div>
    );
};

export default SchoolClasses;