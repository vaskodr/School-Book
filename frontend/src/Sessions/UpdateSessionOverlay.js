import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button } from 'react-bootstrap';
import '../Sessions/SessionStyle.css';

const UpdateSessionOverlay = ({ show, onHide, onSave, currentSession, setCurrentSession, sessionId }) => {
    const { authData } = useContext(AuthContext);
    const [subjects, setSubjects] = useState([]);
    const [teachers, setTeachers] = useState([]);

    useEffect(() => {
        if (sessionId) {
            fetchSessionData(sessionId);
        }
    }, [sessionId]);

    useEffect(() => {
        fetchSubjects();
    }, []);

    useEffect(() => {
        if (currentSession?.subjectId) {
            fetchTeachers(currentSession.subjectId);
        }
    }, [currentSession?.subjectId]);

    const fetchSessionData = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/class-sessions/${id}`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                setCurrentSession(data);
            } else {
                console.error('Failed to fetch session data');
            }
        } catch (error) {
            console.error('Error fetching session data:', error);
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
        if (!sessionId) {
            console.error('No sessionId provided');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/class-sessions/${sessionId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({
                    day: currentSession.day,
                    startTime: `${currentSession.startTime}:00`, // Add seconds
                    endTime: `${currentSession.endTime}:00`, // Add seconds
                    teacherId: parseInt(currentSession.teacherId),
                    subjectId: parseInt(currentSession.subjectId)
                }),
            });
            if (response.ok) {
                const data = await response.json();
                onSave(data);
                onHide();
            } else {
                console.error('Failed to update session');
            }
        } catch (error) {
            console.error('Error updating session:', error);
        }
    };

    return (
        <div className={`overlay ${show ? 'show' : ''}`}>
            <div className="overlay-content">
                <h3>Update Session</h3>
                <Form>
                    <Form.Group controlId="day">
                        <Form.Label>Day</Form.Label>
                        <Form.Control
                            as="select"
                            value={currentSession?.day || ''}
                            onChange={(e) => setCurrentSession({ ...currentSession, day: e.target.value })}
                        >
                            <option value="">Select Day</option>
                            <option value="MONDAY">Monday</option>
                            <option value="TUESDAY">Tuesday</option>
                            <option value="WEDNESDAY">Wednesday</option>
                            <option value="THURSDAY">Thursday</option>
                            <option value="FRIDAY">Friday</option>
                        </Form.Control>
                    </Form.Group>

                    <Form.Group controlId="startTime">
                        <Form.Label>Start Time</Form.Label>
                        <Form.Control
                            type="time"
                            value={currentSession?.startTime || ''}
                            onChange={(e) => setCurrentSession({ ...currentSession, startTime: e.target.value })}
                        />
                    </Form.Group>

                    <Form.Group controlId="endTime">
                        <Form.Label>End Time</Form.Label>
                        <Form.Control
                            type="time"
                            value={currentSession?.endTime || ''}
                            onChange={(e) => setCurrentSession({ ...currentSession, endTime: e.target.value })}
                        />
                    </Form.Group>

                    <Form.Group controlId="subjectId">
                        <Form.Label>Subject</Form.Label>
                        <Form.Control
                            as="select"
                            value={currentSession?.subjectId || ''}
                            onChange={(e) => setCurrentSession({ ...currentSession, subjectId: e.target.value })}
                        >
                            <option value="">Select Subject</option>
                            {subjects.map((subject) => (
                                <option key={subject.id} value={subject.id}>
                                    {subject.name}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>

                    <Form.Group controlId="teacherId">
                        <Form.Label>Teacher</Form.Label>
                        <Form.Control
                            as="select"
                            value={currentSession?.teacherId || ''}
                            onChange={(e) => setCurrentSession({ ...currentSession, teacherId: e.target.value })}
                            disabled={!currentSession?.subjectId}
                        >
                            <option value="">Select Teacher</option>
                            {teachers.map((teacher) => (
                                <option key={teacher.id} value={teacher.id}>
                                    {teacher.firstName} {teacher.lastName}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>

                    <div className="d-flex justify-content-end mt-3">
                        <Button variant="secondary" onClick={onHide} className="me-2">
                            Cancel
                        </Button>
                        <Button variant="primary" onClick={handleSave}>
                            Save
                        </Button>
                    </div>
                </Form>
            </div>
        </div>
    );
};

export default UpdateSessionOverlay;