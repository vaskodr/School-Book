import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Container, Row, Col, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const CreateClassProgram = () => {
    const { schoolId, classId } = useParams();
    const [teachers, setTeachers] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [classSessions, setClassSessions] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTeachers = async () => {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teachers`, {
                headers: { Authorization: `Bearer ${authData.accessToken}` },
            });
            const data = await response.json();
            setTeachers(data);
        };

        const fetchSubjects = async () => {
            const response = await fetch(`http://localhost:8080/api/subjects`, {
                headers: { Authorization: `Bearer ${authData.accessToken}` },
            });
            const data = await response.json();
            setSubjects(data);
        };

        fetchTeachers();
        fetchSubjects();
    }, [schoolId, authData]);

    const handleAddSession = () => {
        setClassSessions([...classSessions, { day: '', startTime: '', endTime: '', teacherId: '', subjectId: '' }]);
    };

    const handleInputChange = (index, event) => {
        const values = [...classSessions];
        values[index][event.target.name] = event.target.value;
        setClassSessions(values);
    };

    const handleFormSubmit = async (event) => {
        event.preventDefault();
        const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/program`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${authData.accessToken}`,
            },
            body: JSON.stringify({ classSessions }),
        });

        if (response.ok) {
            navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/program`);
        } else {
            console.error('Failed to create program');
        }
    };

    return (
        <Container className="mt-4">
            <h2>Create Program</h2>
            <Form onSubmit={handleFormSubmit}>
                {classSessions.map((session, index) => (
                    <div key={index}>
                        <Row className="mb-3">
                            <Col md={2}>
                                <Form.Group controlId={`day-${index}`}>
                                    <Form.Label>Day</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="day"
                                        value={session.day}
                                        onChange={(event) => handleInputChange(index, event)}
                                        required
                                    >
                                        <option value="">Select day</option>
                                        <option value="MONDAY">Monday</option>
                                        <option value="TUESDAY">Tuesday</option>
                                        <option value="WEDNESDAY">Wednesday</option>
                                        <option value="THURSDAY">Thursday</option>
                                        <option value="FRIDAY">Friday</option>
                                    </Form.Control>
                                </Form.Group>
                            </Col>
                            <Col md={2}>
                                <Form.Group controlId={`startTime-${index}`}>
                                    <Form.Label>Start Time</Form.Label>
                                    <Form.Control
                                        type="time"
                                        name="startTime"
                                        value={session.startTime}
                                        onChange={(event) => handleInputChange(index, event)}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={2}>
                                <Form.Group controlId={`endTime-${index}`}>
                                    <Form.Label>End Time</Form.Label>
                                    <Form.Control
                                        type="time"
                                        name="endTime"
                                        value={session.endTime}
                                        onChange={(event) => handleInputChange(index, event)}
                                        required
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={3}>
                                <Form.Group controlId={`teacherId-${index}`}>
                                    <Form.Label>Teacher</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="teacherId"
                                        value={session.teacherId}
                                        onChange={(event) => handleInputChange(index, event)}
                                        required
                                    >
                                        <option value="">Select teacher</option>
                                        {teachers.map((teacher) => (
                                            <option key={teacher.id} value={teacher.id}>
                                                {teacher.firstName} {teacher.lastName}
                                            </option>
                                        ))}
                                    </Form.Control>
                                </Form.Group>
                            </Col>
                            <Col md={3}>
                                <Form.Group controlId={`subjectId-${index}`}>
                                    <Form.Label>Subject</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="subjectId"
                                        value={session.subjectId}
                                        onChange={(event) => handleInputChange(index, event)}
                                        required
                                    >
                                        <option value="">Select subject</option>
                                        {subjects.map((subject) => (
                                            <option key={subject.id} value={subject.id}>
                                                {subject.name}
                                            </option>
                                        ))}
                                    </Form.Control>
                                </Form.Group>
                            </Col>
                        </Row>
                    </div>
                ))}
                <Button variant="success" onClick={handleAddSession}>Add Session</Button>
                <Button variant="primary" type="submit" className="ms-2">Submit</Button>
                <Button variant="secondary" className="ms-2" onClick={() => navigate(-1)}>Back</Button>
            </Form>
        </Container>
    );
};

export default CreateClassProgram;