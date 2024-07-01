import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const UpdateStudent = () => {
    const { schoolId, classId, studentId } = useParams();
    const [studentData, setStudentData] = useState({
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
    });
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchStudentData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/${studentId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setStudentData({
                        firstName: data.firstName || '',
                        lastName: data.lastName || '',
                        dateOfBirth: data.dateOfBirth || '',
                        gender: data.gender || '',
                        phone: data.phone || '',
                        email: data.email || '',
                        username: data.username || '',
                        password: '', // Leave password empty
                    });
                } else {
                    console.error('Failed to fetch student data');
                }
            } catch (error) {
                console.error('Error fetching student data:', error);
            }
        };

        fetchStudentData();
    }, [schoolId, classId, studentId, authData]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setStudentData({
            ...studentData,
            [name]: value
        });
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        // Remove password field if it's empty
        const updatedStudentData = { ...studentData };
        if (!updatedStudentData.password) {
            delete updatedStudentData.password;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/${studentId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(updatedStudentData),
            });
            if (!response.ok) {
                console.error('Failed to update student');
            } else {
                navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/students`);
            }
        } catch (error) {
            console.error('Error updating student:', error);
        }
    };

    return (
        <Container className="mt-4">
            <Card>
                <Card.Header as="h2" className="text-center">Update Student</Card.Header>
                <Card.Body>
                    <Form onSubmit={handleFormSubmit}>
                        <Row>
                            <Col md={6}>
                                <Form.Group controlId="firstName" className="mb-3">
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="firstName"
                                        value={studentData.firstName}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={6}>
                                <Form.Group controlId="lastName" className="mb-3">
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="lastName"
                                        value={studentData.lastName}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col md={6}>
                                <Form.Group controlId="dateOfBirth" className="mb-3">
                                    <Form.Label>Date of Birth</Form.Label>
                                    <Form.Control
                                        type="date"
                                        name="dateOfBirth"
                                        value={studentData.dateOfBirth}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={6}>
                                <Form.Group controlId="gender" className="mb-3">
                                    <Form.Label>Gender</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="gender"
                                        value={studentData.gender}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col md={6}>
                                <Form.Group controlId="phone" className="mb-3">
                                    <Form.Label>Phone</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="phone"
                                        value={studentData.phone}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={6}>
                                <Form.Group controlId="email" className="mb-3">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control
                                        type="email"
                                        name="email"
                                        value={studentData.email}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col md={6}>
                                <Form.Group controlId="username" className="mb-3">
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="username"
                                        value={studentData.username}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={6}>
                                <Form.Group controlId="password" className="mb-3">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control
                                        type="password"
                                        name="password"
                                        value={studentData.password}
                                        onChange={handleInputChange}
                                        placeholder="Leave blank to keep current password"
                                    />
                                </Form.Group>
                            </Col>
                        </Row>
                        <div className="d-flex justify-content-between">
                            <Button variant="primary" type="submit">Submit</Button>
                            <Button variant="secondary" onClick={() => navigate(-1)}>Back</Button>
                        </div>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default UpdateStudent;