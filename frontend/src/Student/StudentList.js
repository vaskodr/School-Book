import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Container, Row, Col, ListGroup, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const StudentsList = () => {
    const { schoolId, classId } = useParams();
    const [students, setStudents] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setStudents(data);
                } else {
                    console.error('Failed to fetch students');
                }
            } catch (error) {
                console.error('Error fetching students:', error);
            }
        };

        fetchStudents();
    }, [schoolId, classId, authData]);

    const handleAddStudentClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/student/add`);
    };

    const handleEditStudent = (studentId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/student/${studentId}/update`);
    };

    const handleDeleteStudent = async (studentId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/${studentId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setStudents(students.filter(student => student.id !== studentId));
            } else {
                console.error('Failed to delete student');
            }
        } catch (error) {
            console.error('Error deleting student:', error);
        }
    };

    const handleStudentDetails = (studentId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/student/${studentId}/details`);
    };

    return (
        <Container className="mt-4">
            <Card className="mb-4">
                <Card.Body>
                    <Card.Title className="text-center">Students in Class</Card.Title>
                    <Row className="mb-3">
                        <Col>
                            <Button variant="secondary" onClick={() => navigate(-1)} className="w-100">Back to Class Details</Button>
                        </Col>
                        <Col>
                            <Button variant="primary" onClick={handleAddStudentClick} className="w-100">Add Student</Button>
                        </Col>
                    </Row>
                    {students.length > 0 ? (
                        <ListGroup>
                            {students.map((student) => (
                                <ListGroup.Item key={student.id} className="d-flex justify-content-between align-items-center">
                                    <span>{student.firstName} {student.lastName}</span>
                                    <div>
                                        <Button variant="info" size="sm" onClick={() => handleStudentDetails(student.id)} className="me-2">Details</Button>
                                        <Button variant="warning" size="sm" onClick={() => handleEditStudent(student.id)} className="me-2">Edit</Button>
                                        <Button variant="danger" size="sm" onClick={() => handleDeleteStudent(student.id)}>Delete</Button>
                                    </div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    ) : (
                        <p className="text-center">Loading students...</p>
                    )}
                </Card.Body>
            </Card>
        </Container>
    );
};

export default StudentsList;