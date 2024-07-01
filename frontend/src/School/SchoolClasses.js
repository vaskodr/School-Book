import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate, Outlet } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Container, Row, Col, Card, ListGroup } from 'react-bootstrap';
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
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}`, {
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

    // Group classes by level
    const groupedClasses = classes.reduce((acc, classItem) => {
        const level = classItem.level;
        if (!acc[level]) {
            acc[level] = [];
        }
        acc[level].push(classItem);
        return acc;
    }, {});

    // Levels in order
    const levels = ['FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH', 'ELEVENTH', 'TWELFTH'];

    return (
        <Container className="mt-5">
            <h2 className="text-center mb-4">Classes for School {schoolId}</h2>
            {schoolDetails.directorId && directorDetails ? (
                <Card className="mb-4">
                    <Card.Body>
                        <Card.Title>Director: {directorDetails.firstName} {directorDetails.lastName}</Card.Title>
                        <div className="d-flex justify-content-end">
                            <Button variant="secondary" size="sm" onClick={() => handleDirectorDetailsClick(schoolDetails.directorId)} className="mx-1">Details</Button>
                            <Button variant="warning" size="sm" onClick={() => handleEditDirectorClick(schoolDetails.directorId)} className="mx-1">Edit</Button>
                            <Button variant="danger" size="sm" onClick={() => handleDeleteDirectorClick(schoolDetails.directorId)} className="mx-1">Delete</Button>
                            <Button variant="danger" size="sm" onClick={() => handleUnassignDirector(schoolDetails.directorId)} className="mx-1">Unassign Director</Button>
                        </div>
                    </Card.Body>
                </Card>
            ) : (
                <Button variant="info" className="mb-4" onClick={handleAddDirectorClick}>
                    Add Director
                </Button>
            )}
            <Row className="mb-4">
                <Col>
                    <Button variant="primary" className="w-100" onClick={handleAddClassClick}>
                        Add Class
                    </Button>
                </Col>
                <Col>
                    <Button variant="secondary" className="w-100" onClick={handleViewTeachersClick}>
                        View All Teachers
                    </Button>
                </Col>
            </Row>
            <Outlet />
            {classes.length > 0 ? (
                levels.map(level => (
                    groupedClasses[level] && (
                        <div key={level} className="mb-4">
                            <h4>{level}</h4>
                            <Row>
                                {groupedClasses[level].map(classItem => (
                                    <Col md={6} key={classItem.id} className="mb-3">
                                        <Card>
                                            <Card.Body>
                                                <Card.Title>{classItem.name}</Card.Title>
                                                <div className="d-flex justify-content-end">
                                                    <Button variant="info" size="sm" onClick={() => handleClassClick(classItem.id)}>Details</Button>
                                                    <Button variant="info" size="sm" onClick={() => handleViewProgramsClick(classItem.id)} className="mx-1">Programs</Button>
                                                    <Button variant="warning" size="sm" onClick={() => handleEditClass(classItem.id)} className="mx-1">Edit</Button>
                                                    <Button variant="danger" size="sm" onClick={() => handleDeleteClass(classItem.id)}>Delete</Button>
                                                </div>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    )
                ))
            ) : (
                <p className="text-center">Loading classes...</p>
            )}
        </Container>
    );
};

export default SchoolClasses;