import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button, Container } from 'react-bootstrap';

const UpdateClass = () => {
    const { schoolId, classId } = useParams();
    const [name, setName] = useState('');
    const [level, setLevel] = useState('FIRST');
    const [mentorId, setMentorId] = useState('');
    const [mentors, setMentors] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClassDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setName(data.name);
                    setLevel(data.level);
                    setMentorId(data.mentorId);
                } else {
                    console.error('Failed to fetch class details');
                }
            } catch (error) {
                console.error('Error fetching class details:', error);
            }
        };

        const fetchMentors = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/available-mentors`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setMentors(data);
                } else {
                    console.error('Failed to fetch mentors');
                }
            } catch (error) {
                console.error('Error fetching mentors:', error);
            }
        };

        fetchClassDetails();
        fetchMentors();
    }, [schoolId, classId, authData]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const updatedClass = {
            name,
            level,
            mentorId: mentorId || null, // If mentorId is empty, set it to null
        };

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(updatedClass),
            });

            if (response.ok) {
                navigate(`/admin/dashboard/school/${schoolId}/classes`);
            } else {
                console.error('Failed to update class');
            }
        } catch (error) {
            console.error('Error updating class:', error);
        }
    };

    return (
        <Container className="mt-4">
            <h2>Update Class</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="name" className="mb-3">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="level" className="mb-3">
                    <Form.Label>Level</Form.Label>
                    <Form.Control
                        as="select"
                        value={level}
                        onChange={(e) => setLevel(e.target.value)}
                        required
                    >
                        <option value="FIRST">FIRST</option>
                        <option value="SECOND">SECOND</option>
                        <option value="THIRD">THIRD</option>
                        <option value="FOURTH">FOURTH</option>
                        <option value="FIFTH">FIFTH</option>
                        <option value="SIXTH">SIXTH</option>
                        <option value="SEVENTH">SEVENTH</option>
                        <option value="EIGHTH">EIGHTH</option>
                        <option value="NINTH">NINTH</option>
                        <option value="TENTH">TENTH</option>
                        <option value="ELEVENTH">ELEVENTH</option>
                        <option value="TWELFTH">TWELFTH</option>
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId="mentorId" className="mb-3">
                    <Form.Label>Mentor</Form.Label>
                    <Form.Control
                        as="select"
                        value={mentorId}
                        onChange={(e) => setMentorId(e.target.value)}
                    >
                        <option value="">Select a mentor</option>
                        {mentors.map(mentor => (
                            <option key={mentor.id} value={mentor.id}>
                                {mentor.firstName} {mentor.lastName} - {mentor.subjectNames.join(', ')}
                            </option>
                        ))}
                    </Form.Control>
                </Form.Group>
                <Button variant="primary" type="submit">Update Class</Button>
                <Button variant="secondary" className="ms-2" onClick={() => navigate(-1)}>Back</Button>
            </Form>
        </Container>
    );
};

export default UpdateClass;