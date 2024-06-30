// src/School/SchoolClasses.js
import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate, Outlet } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const SchoolClasses = () => {
    const { schoolId } = useParams();
    const [classes, setClasses] = useState([]);
    const [schoolDetails, setSchoolDetails] = useState({});
    const [directorDetails, setDirectorDetails] = useState(null);
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

    const fetchSchoolDetails = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                console.log('Fetched school details:', data); // Debug log
                setSchoolDetails(data);
                if (data.directorId) {
                    fetchDirectorDetails(data.directorId);
                }
            } else {
                console.error('Failed to fetch school details');
            }
        } catch (error) {
            console.error('Error fetching school details:', error);
        }
    };

    const fetchDirectorDetails = async (directorId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/director/${directorId}`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                console.log('Fetched director details:', data); // Debug log
                setDirectorDetails(data);
            } else {
                console.error('Failed to fetch director details');
            }
        } catch (error) {
            console.error('Error fetching director details:', error);
        }
    };

    useEffect(() => {
        fetchClasses();
        fetchSchoolDetails();
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

    const handleViewProgramsClick = (classId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/programs`);
    };

    const handleEditClass = (classId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/update`);
    };

    const handleDeleteClass = async (classId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/class/${classId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setClasses(classes.filter(classItem => classItem.id !== classId));
            } else {
                console.error('Failed to delete class');
            }
        } catch (error) {
            console.error('Error deleting class:', error);
        }
    };

    const handleAddDirectorClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/director/add`);
    };

    const handleEditDirectorClick = (directorId) => {
        navigate(`/admin/dashboard/school/${schoolId}/director/${directorId}/update`);
    };

    const handleDeleteDirectorClick = async (directorId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/director/${directorId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                fetchSchoolDetails(); // Re-fetch school details after deletion
                setDirectorDetails(null); // Reset director details
            } else {
                console.error('Failed to delete director');
            }
        } catch (error) {
            console.error('Error deleting director:', error);
        }
    };

    const handleUnassignDirector = async (directorId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/director/${directorId}/unassign-director`, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                fetchSchoolDetails(); // Re-fetch school details after unassigning
                setDirectorDetails(null); // Reset director details
            } else {
                console.error('Failed to unassign director');
            }
        } catch (error) {
            console.error('Error unassigning director:', error);
        }
    };

    const handleDirectorDetailsClick = (directorId) => {
        navigate(`/admin/dashboard/school/${schoolId}/director/${directorId}`);
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center">Classes for School {schoolId}</h2>
            {schoolDetails.directorId && directorDetails ? (
                <div className="mb-3">
                    <h5>Director: {directorDetails.firstName} {directorDetails.lastName}</h5>
                    <div className="d-flex">
                        <Button variant="secondary" size="sm" onClick={() => handleDirectorDetailsClick(schoolDetails.directorId)} className="mx-1">Details</Button>
                        <Button variant="warning" size="sm" onClick={() => handleEditDirectorClick(schoolDetails.directorId)} className="mx-1">Edit</Button>
                        <Button variant="danger" size="sm" onClick={() => handleDeleteDirectorClick(schoolDetails.directorId)} className="mx-1">Delete</Button>
                        <Button variant="danger" size="sm" onClick={() => handleUnassignDirector(schoolDetails.directorId)} className="mx-1">Unassign Director</Button>
                    </div>
                </div>
            ) : (
                <Button variant="info" onClick={handleAddDirectorClick}>
                    Add Director
                </Button>
            )}
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
                                <Button variant="info" size="sm" onClick={() => handleViewProgramsClick(classItem.id)} className="mx-1">Programs</Button>
                                <Button variant="warning" size="sm" onClick={() => handleEditClass(classItem.id)} className="mx-1">Edit</Button>
                                <Button variant="danger" size="sm" onClick={() => handleDeleteClass(classItem.id)}>Delete</Button>
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