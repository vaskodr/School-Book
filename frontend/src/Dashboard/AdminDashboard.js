import React, { useEffect, useState, useContext, useCallback } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import { useNavigate, Outlet, useLocation } from 'react-router-dom';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

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

    const handleManageSubjects = () => {
        navigate('/admin/dashboard/subjects');
    };

    return (
        <Container className="mt-5">
            <div className="text-center mb-4">
                <h2>Admin Dashboard</h2>
            </div>
            <Row className="justify-content-center mb-4">
                <Col md={4}>
                    <Button variant="primary" className="w-100 mb-3" onClick={handleCreateSchool}>Create New School</Button>
                </Col>
                <Col md={4}>
                    <Button variant="secondary" className="w-100 mb-3" onClick={handleManageSubjects}>Manage Subjects</Button>
                </Col>
            </Row>
            <h4 className="mb-4">Select a school:</h4>
            {schools.length > 0 ? (
                <Row>
                    {schools.map((school) => (
                        <Col md={6} key={school.id} className="mb-3">
                            <Card>
                                <Card.Body>
                                    <Card.Title>{school.name}</Card.Title>
                                    <Card.Text>
                                        <Button variant="info" className="me-2" onClick={() => handleSchoolDetails(school.id)}>Details</Button>
                                        <Button variant="warning" className="me-2" onClick={() => handleEditSchool(school.id)}>Edit</Button>
                                        <Button variant="danger" onClick={() => handleDeleteSchool(school.id)}>Delete</Button>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            ) : (
                <p>Loading schools...</p>
            )}
            <Outlet />
        </Container>
    );
};

export default AdminDashboard;