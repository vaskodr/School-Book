import React, { useState, useContext, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const CreateClass = () => {
    const { schoolId } = useParams();
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();
    const [className, setClassName] = useState('');
    const [classLevel, setClassLevel] = useState('FIRST');
    const [mentors, setMentors] = useState([]);
    const [selectedMentor, setSelectedMentor] = useState('');

    useEffect(() => {
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

        fetchMentors();
    }, [schoolId, authData]);

    const handleFormSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/add-class`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ name: className, level: classLevel, mentorId: selectedMentor }),
            });
            if (response.ok) {
                navigate(`/admin/dashboard/school/${schoolId}/classes`);
            } else {
                console.error('Failed to create class');
            }
        } catch (error) {
            console.error('Error creating class:', error);
        }
    };

    const handleBackClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/classes`);
    };

    return (
        <div className="container mt-4">
            <h2>Create New Class</h2>
            <Form onSubmit={handleFormSubmit}>
                <Form.Group controlId="className" className="mb-3">
                    <Form.Label>Class Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={className}
                        onChange={(e) => setClassName(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="classLevel" className="mb-3">
                    <Form.Label>Class Level</Form.Label>
                    <Form.Control
                        as="select"
                        value={classLevel}
                        onChange={(e) => setClassLevel(e.target.value)}
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
                <Form.Group controlId="mentor" className="mb-3">
                    <Form.Label>Mentor</Form.Label>
                    {mentors.map((mentor) => (
                        <Form.Check
                            type="radio"
                            key={mentor.id}
                            name="mentor"
                            id={`mentor-${mentor.id}`}
                            label={`${mentor.firstName} ${mentor.lastName} - Subjects: ${mentor.subjectNames.join(', ')}`}
                            value={mentor.id}
                            onChange={() => setSelectedMentor(mentor.id)}
                            checked={selectedMentor === mentor.id}
                        />
                    ))}
                </Form.Group>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
                <Button variant="secondary" className="ms-2" onClick={handleBackClick}>
                    Back
                </Button>
            </Form>
        </div>
    );
};

export default CreateClass;