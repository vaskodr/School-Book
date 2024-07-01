import React, { useState, useContext, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const RegisterStudent = () => {
    const { schoolId, classId } = useParams();
    const [studentData, setStudentData] = useState({
        id: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
    });
    const [parentData, setParentData] = useState({
        id: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
    });
    const [showParentForm, setShowParentForm] = useState(false);
    const [parentId, setParentId] = useState('');
    const [parentExists, setParentExists] = useState(false);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleInputChange = (e, setData, data) => {
        const { name, value } = e.target;
        setData({ ...data, [name]: value });
    };

    const handleParentIdChange = (e) => {
        setParentId(e.target.value);
    };

    const checkParentId = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/user/${parentId}`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                setParentData({
                    id: data.id || '',
                    firstName: data.firstName || '',
                    lastName: data.lastName || '',
                    dateOfBirth: data.dateOfBirth || '',
                    gender: data.gender || '',
                    phone: data.phone || '',
                    email: data.email || '',
                    username: data.username || '',
                    password: data.password || '', // Fetch existing password
                });
                setParentExists(true);
                setShowParentForm(true);
            } else {
                setParentExists(false);
                setShowParentForm(true);
            }
        } catch (error) {
            console.error('Error fetching parent data:', error);
            setParentExists(false);
            setShowParentForm(true);
        }
    };

    const handleStudentSubmit = (e) => {
        e.preventDefault();
        setShowParentForm(true);
    };

    const handleParentSubmit = async (e) => {
        e.preventDefault();

        // Remove password field if it's empty
        const updatedParentData = { ...parentData };
        if (!updatedParentData.password) {
            delete updatedParentData.password;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/add-student`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ ...studentData, registerDTO: updatedParentData }),
            });
            if (response.ok) {
                navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/students`);
            } else {
                console.error('Failed to register student');
            }
        } catch (error) {
            console.error('Error registering student:', error);
        }
    };

    return (
        <Container className="mt-4">
            <Card>
                <Card.Header as="h2" className="text-center">Register Student</Card.Header>
                <Card.Body>
                    {!showParentForm ? (
                        <>
                            <Form onSubmit={handleStudentSubmit}>
                                <Row>
                                    {['id', 'firstName', 'lastName', 'dateOfBirth', 'gender', 'phone', 'email', 'username', 'password'].map((field) => (
                                        <Col md={6} key={field}>
                                            <Form.Group controlId={field} className="mb-3">
                                                <Form.Label>{field.charAt(0).toUpperCase() + field.slice(1)}</Form.Label>
                                                <Form.Control
                                                    type={field === 'dateOfBirth' ? 'date' : 'text'}
                                                    name={field}
                                                    value={studentData[field]}
                                                    onChange={(e) => handleInputChange(e, setStudentData, studentData)}
                                                    required={field !== 'password'} // Password is not required
                                                />
                                            </Form.Group>
                                        </Col>
                                    ))}
                                </Row>
                                <Button variant="primary" type="submit" className="me-2">Next: Add Parent</Button>
                            </Form>
                            <div className="mt-4">
                                <Form.Group controlId="parentId" className="mb-3">
                                    <Form.Label>Parent ID (if already exists)</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="parentId"
                                        value={parentId}
                                        onChange={handleParentIdChange}
                                    />
                                </Form.Group>
                                <Button variant="secondary" onClick={checkParentId}>Check Parent ID</Button>
                            </div>
                        </>
                    ) : (
                        <Form onSubmit={handleParentSubmit}>
                            <h3>Add Parent Details</h3>
                            <Row>
                                {['id', 'firstName', 'lastName', 'dateOfBirth', 'gender', 'phone', 'email', 'username'].map((field) => (
                                    <Col md={6} key={field}>
                                        <Form.Group controlId={field} className="mb-3">
                                            <Form.Label>{field.charAt(0).toUpperCase() + field.slice(1)}</Form.Label>
                                            <Form.Control
                                                type={field === 'dateOfBirth' ? 'date' : 'text'}
                                                name={field}
                                                value={parentData[field]}
                                                onChange={(e) => handleInputChange(e, setParentData, parentData)}
                                                required
                                                disabled={parentExists && field !== 'password'} // Disable fields if parent exists, except password
                                            />
                                        </Form.Group>
                                    </Col>
                                ))}
                                <Col md={6}>
                                    <Form.Group controlId="password" className="mb-3">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control
                                            type="password"
                                            name="password"
                                            value={parentData.password}
                                            onChange={(e) => handleInputChange(e, setParentData, parentData)}
                                            placeholder="Leave blank to keep current password"
                                            required={!parentExists} // Make it required only if the parent does not exist
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Button variant="primary" type="submit" className="me-2">Submit</Button>
                        </Form>
                    )}
                    <Button variant="secondary" className="mt-3" onClick={() => navigate(-1)}>Back</Button>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default RegisterStudent;