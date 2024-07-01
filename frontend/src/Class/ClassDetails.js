import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Container, Row, Col, Card, Spinner } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const ClassDetails = () => {
    const { schoolId, classId } = useParams();
    const [classDetails, setClassDetails] = useState({});
    const [mentorDetails, setMentorDetails] = useState(null);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClassDetails = async () => {
            try {
                console.log('Fetching class details');
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Class details fetched:', data);
                    setClassDetails(data);
                    if (data.mentorId) {
                        fetchMentorDetails(data.mentorId);
                    }
                } else {
                    console.error('Failed to fetch class details');
                }
            } catch (error) {
                console.error('Error fetching class details:', error);
            }
        };

        const fetchMentorDetails = async (mentorId) => {
            try {
                console.log('Fetching mentor details for mentorId:', mentorId);
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${mentorId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Mentor details fetched:', data);
                    setMentorDetails(data);
                } else {
                    console.error('Failed to fetch mentor details');
                }
            } catch (error) {
                console.error('Error fetching mentor details:', error);
            }
        };

        fetchClassDetails();
    }, [schoolId, classId, authData]);

    const handleViewStudentsClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/students`);
    };

    return (
        <Container className="mt-4">
            <Card>
                <Card.Header as="h2" className="bg-primary text-white">Class Details</Card.Header>
                <Card.Body>
                    <Row className="mb-3">
                        <Col md={3}><strong>Name:</strong></Col>
                        <Col md={9}>{classDetails.name || <Spinner animation="border" size="sm" />}</Col>
                    </Row>
                    <Row className="mb-3">
                        <Col md={3}><strong>Level:</strong></Col>
                        <Col md={9}>{classDetails.level || <Spinner animation="border" size="sm" />}</Col>
                    </Row>
                    <Row className="mb-3">
                        <Col md={3}><strong>School:</strong></Col>
                        <Col md={9}>{classDetails.schoolName || <Spinner animation="border" size="sm" />}</Col>
                    </Row>
                    {mentorDetails ? (
                        <Row className="mb-3">
                            <Col md={3}><strong>Mentor:</strong></Col>
                            <Col md={9}>{mentorDetails.firstName} {mentorDetails.lastName}</Col>
                        </Row>
                    ) : (
                        <Row className="mb-3">
                            <Col md={3}><strong>Mentor:</strong></Col>
                            <Col md={9}>{classDetails.mentorId ? <Spinner animation="border" size="sm" /> : 'No mentor assigned'}</Col>
                        </Row>
                    )}
                    <Row className="mt-4">
                        <Col>
                            <Button variant="primary" className="w-100" onClick={handleViewStudentsClick}>
                                View All Students
                            </Button>
                        </Col>
                        <Col>
                            <Button variant="secondary" className="w-100" onClick={() => navigate(-1)}>
                                Back to Classes
                            </Button>
                        </Col>
                    </Row>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default ClassDetails;