import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Container, ListGroup, Card, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const SchoolTeachers = () => {
    const { schoolId } = useParams();
    const [teachers, setTeachers] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTeachers = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setTeachers(data);
                } else {
                    console.error('Failed to fetch teachers');
                }
            } catch (error) {
                console.error('Error fetching teachers:', error);
            }
        };

        fetchTeachers();
    }, [schoolId, authData]);

    const handleAddTeacherClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/teacher/add`);
    };

    const handleEditTeacher = (teacherId) => {
        navigate(`/admin/dashboard/school/${schoolId}/teacher/${teacherId}/update`);
    };

    const handleDeleteTeacher = async (teacherId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setTeachers(teachers.filter(teacher => teacher.id !== teacherId));
            } else {
                console.error('Failed to delete teacher');
            }
        } catch (error) {
            console.error('Error deleting teacher:', error);
        }
    };

    const handleViewTeacherDetails = (teacherId) => {
        navigate(`/admin/dashboard/school/${schoolId}/teacher/${teacherId}/details`);
    };

    return (
        <Container className="mt-4">
            <h2>Teachers in School</h2>
            <Button variant="secondary" className="mb-3" onClick={() => navigate(-1)}>Back to School Details</Button>
            <Button variant="primary" className="mb-3" onClick={handleAddTeacherClick}>Add Teacher</Button>
            {teachers.length > 0 ? (
                <ListGroup>
                    {teachers.map((teacher) => (
                        <ListGroup.Item key={teacher.id} className="mb-3">
                            <Row>
                                <Col md={8}>
                                    <Card>
                                        <Card.Body>
                                            <Card.Title>{teacher.firstName} {teacher.lastName}</Card.Title>
                                            <Card.Text>
                                                <strong>Subjects:</strong> {teacher.subjectNames.join(', ')}<br />
                                                {teacher.classDTO ? (
                                                    <>
                                                        <strong>Mentored Class:</strong> {teacher.classDTO.name} - Level: {teacher.classDTO.level}
                                                    </>
                                                ) : (
                                                    <strong>No mentored class</strong>
                                                )}
                                            </Card.Text>
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col md={4} className="d-flex align-items-center justify-content-end">
                                    <Button variant="secondary" className="me-2" onClick={() => handleEditTeacher(teacher.id)}>Edit</Button>
                                    <Button variant="danger" className="me-2" onClick={() => handleDeleteTeacher(teacher.id)}>Delete</Button>
                                    <Button variant="info" onClick={() => handleViewTeacherDetails(teacher.id)}>Details</Button>
                                </Col>
                            </Row>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            ) : (
                <p>Loading teachers...</p>
            )}
        </Container>
    );
};

export default SchoolTeachers;