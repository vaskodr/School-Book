import React, { useState, useEffect, useContext } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { AuthContext } from '../Auth/AuthContext';

const AddSessionOverlay = ({ onSave, programId, fetchProgramData, handleClose }) => {
    const { authData } = useContext(AuthContext);
    const [subjects, setSubjects] = useState([]);
    const [teachers, setTeachers] = useState([]);
    const [currentSession, setCurrentSession] = useState({
        day: '',
        startTime: '',
        endTime: '',
        teacherId: '',
        subjectId: ''
    });
    const [errors, setErrors] = useState({});
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        fetchSubjects().catch(console.error);
    }, []);

    useEffect(() => {
        if (currentSession.subjectId) {
            fetchTeachers(currentSession.subjectId).catch(console.error);
        }
    }, [currentSession.subjectId]);

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

    const fetchTeachers = async (subjectId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/subject/${subjectId}/teachers`, {
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

    const handleSave = async () => {
        if (validateForm()) {
            setSubmitting(true);
            try {
                const response = await fetch(`http://localhost:8080/api/class-sessions/program/${programId}/create`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                    body: JSON.stringify({
                        day: currentSession.day,
                        startTime: currentSession.startTime.length === 5 ? `${currentSession.startTime}:00` : currentSession.startTime, // Ensure seconds are included
                        endTime: currentSession.endTime.length === 5 ? `${currentSession.endTime}:00` : currentSession.endTime, // Ensure seconds are included
                        teacherId: parseInt(currentSession.teacherId), // Ensure it's sent as a number
                        subjectId: parseInt(currentSession.subjectId) // Ensure it's sent as a number
                    }),
                });

                if (response.ok) {
                    const contentType = response.headers.get("content-type");
                    if (contentType && contentType.includes("application/json")) {
                        await response.json();
                    } else {
                        await response.text(); // Handle non-JSON response
                    }
                    onSave(); // Callback to update parent state after saving
                    handleClose(); // Close the overlay
                    setCurrentSession({
                        day: '',
                        startTime: '',
                        endTime: '',
                        teacherId: '',
                        subjectId: ''
                    });
                } else {
                    console.error('Failed to create session');
                }
            } catch (error) {
                console.error('Error creating session:', error);
            } finally {
                setSubmitting(false);
            }
        }
    };

    const validateForm = () => {
        const newErrors = {};
        if (!currentSession.day) newErrors.day = 'Day is required';
        if (!currentSession.startTime) newErrors.startTime = 'Start time is required';
        if (!currentSession.endTime) newErrors.endTime = 'End time is required';
        if (!currentSession.subjectId) newErrors.subjectId = 'Subject is required';
        if (!currentSession.teacherId) newErrors.teacherId = 'Teacher is required';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    return (
        <div className="overlay show">
            <div className="overlay-content">
                <h2>Add Session</h2>
                <Form>
                    <Form.Group controlId="day">
                        <Form.Label>Day</Form.Label>
                        <Form.Control
                            as="select"
                            value={currentSession.day}
                            onChange={(e) => setCurrentSession({ ...currentSession, day: e.target.value })}
                            isInvalid={!!errors.day}
                        >
                            <option value="">Select a day</option>
                            <option value="MONDAY">Monday</option>
                            <option value="TUESDAY">Tuesday</option>
                            <option value="WEDNESDAY">Wednesday</option>
                            <option value="THURSDAY">Thursday</option>
                            <option value="FRIDAY">Friday</option>
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">{errors.day}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="startTime">
                        <Form.Label>Start Time</Form.Label>
                        <Form.Control
                            type="time"
                            value={currentSession.startTime}
                            onChange={(e) => setCurrentSession({ ...currentSession, startTime: e.target.value })}
                            isInvalid={!!errors.startTime}
                        />
                        <Form.Control.Feedback type="invalid">{errors.startTime}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="endTime">
                        <Form.Label>End Time</Form.Label>
                        <Form.Control
                            type="time"
                            value={currentSession.endTime}
                            onChange={(e) => setCurrentSession({ ...currentSession, endTime: e.target.value })}
                            isInvalid={!!errors.endTime}
                        />
                        <Form.Control.Feedback type="invalid">{errors.endTime}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="subjectId">
                        <Form.Label>Subject</Form.Label>
                        <Form.Control
                            as="select"
                            value={currentSession.subjectId}
                            onChange={(e) => setCurrentSession({ ...currentSession, subjectId: e.target.value })}
                            isInvalid={!!errors.subjectId}
                        >
                            <option value="">Select a subject</option>
                            {subjects.map((subject) => (
                                <option key={subject.id} value={subject.id}>{subject.name}</option>
                            ))}
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">{errors.subjectId}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="teacherId">
                        <Form.Label>Teacher</Form.Label>
                        <Form.Control
                            as="select"
                            value={currentSession.teacherId}
                            onChange={(e) => setCurrentSession({ ...currentSession, teacherId: e.target.value })}
                            isInvalid={!!errors.teacherId}
                        >
                            <option value="">Select a teacher</option>
                            {teachers.map((teacher) => (
                                <option key={teacher.id} value={teacher.id}>{teacher.firstName} {teacher.lastName}</option>
                            ))}
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">{errors.teacherId}</Form.Control.Feedback>
                    </Form.Group>
                </Form>
                {submitting && <Alert variant="info">Submitting...</Alert>}
                <div className="overlay-footer">
                    <Button variant="secondary" onClick={handleClose} disabled={submitting}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleSave} disabled={submitting}>
                        Save
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default AddSessionOverlay;
