import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const UpdateTeacher = () => {
    const { schoolId, teacherId } = useParams();
    const [teacherData, setTeacherData] = useState({
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
        subjectIds: []
    });
    const [subjects, setSubjects] = useState([]);
    const [subjectNames, setSubjectNames] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTeacherData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setTeacherData({
                        firstName: data.firstName || '',
                        lastName: data.lastName || '',
                        dateOfBirth: data.dateOfBirth || '',
                        gender: data.gender || '',
                        phone: data.phone || '',
                        email: data.email || '',
                        username: data.username || '',
                        password: '', // Leave password empty
                        subjectIds: [] // We will set this after fetching all subjects
                    });
                    setSubjectNames(data.subjectNames || []);
                } else {
                    console.error('Failed to fetch teacher data');
                }
            } catch (error) {
                console.error('Error fetching teacher data:', error);
            }
        };

        const fetchSubjects = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/subject/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setSubjects(data);
                } else {
                    console.error('Failed to fetch subjects');
                }
            } catch (error) {
                console.error('Error fetching subjects:', error);
            }
        };

        fetchTeacherData();
        fetchSubjects();
    }, [schoolId, teacherId, authData]);

    useEffect(() => {
        if (subjects.length > 0 && subjectNames.length > 0) {
            const subjectIds = subjects
                .filter(subject => subjectNames.includes(subject.name))
                .map(subject => subject.id);
            setTeacherData(prevData => ({
                ...prevData,
                subjectIds
            }));
        }
    }, [subjects, subjectNames]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setTeacherData({
            ...teacherData,
            [name]: value
        });
    };

    const handleCheckboxChange = (subjectId) => {
        setTeacherData((prevState) => {
            const { subjectIds } = prevState;
            if (subjectIds.includes(subjectId)) {
                return {
                    ...prevState,
                    subjectIds: subjectIds.filter((id) => id !== subjectId)
                };
            } else {
                return {
                    ...prevState,
                    subjectIds: [...subjectIds, subjectId]
                };
            }
        });
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        // Remove password field if it's empty
        const updatedTeacherData = { ...teacherData };
        if (!updatedTeacherData.password) {
            delete updatedTeacherData.password;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(updatedTeacherData),
            });
            if (!response.ok) {
                console.error('Failed to update teacher');
            } else {
                navigate(`/admin/dashboard/school/${schoolId}/teachers`);
            }
        } catch (error) {
            console.error('Error updating teacher:', error);
        }
    };

    return (
        <Container className="mt-4">
            <Card>
                <Card.Header as="h2" className="text-center">Update Teacher</Card.Header>
                <Card.Body>
                    <Form onSubmit={handleFormSubmit}>
                        <Row>
                            <Col md={6}>
                                <Form.Group controlId="firstName" className="mb-3">
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="firstName"
                                        value={teacherData.firstName}
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
                                        value={teacherData.lastName}
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
                                        value={teacherData.dateOfBirth}
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
                                        value={teacherData.gender}
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
                                        value={teacherData.phone}
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
                                        value={teacherData.email}
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
                                        value={teacherData.username}
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
                                        value={teacherData.password}
                                        onChange={handleInputChange}
                                        placeholder="Leave blank to keep current password"
                                    />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Form.Group controlId="subjects" className="mb-3">
                            <Form.Label>Subjects</Form.Label>
                            <div className="d-flex flex-wrap">
                                {subjects.map((subject) => (
                                    <Form.Check
                                        key={subject.id}
                                        type="checkbox"
                                        label={subject.name}
                                        id={`subject-${subject.id}`}
                                        checked={teacherData.subjectIds.includes(subject.id)}
                                        onChange={() => handleCheckboxChange(subject.id)}
                                        className="me-3 mb-2"
                                    />
                                ))}
                            </div>
                        </Form.Group>
                        <Button variant="primary" type="submit" className="me-2">Submit</Button>
                        <Button variant="secondary" onClick={() => navigate(-1)}>Back</Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default UpdateTeacher;