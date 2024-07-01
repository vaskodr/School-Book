import React, { useEffect, useState, useContext, useCallback } from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Container, Table } from 'react-bootstrap';
import UpdateSessionOverlay from '../Sessions/UpdateSessionOverlay';
import AddSessionOverlay from "../Sessions/AddSessionOverlay"; // Custom CSS for overlay

const ClassProgram = () => {
    const { schoolId, classId } = useParams();
    const { authData } = useContext(AuthContext);
    const [program, setProgram] = useState(null);
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(true);
    const [showAddSessionOverlay, setShowAddSessionOverlay] = useState(false);
    const [showUpdateSessionOverlay, setShowUpdateSessionOverlay] = useState(false);
    const [currentSession, setCurrentSession] = useState(null);

    const fetchProgramData = useCallback(async () => {
        console.log('Fetching program data for classId:', classId);
        console.log('School id: ', schoolId);
        console.log('Class id: ', classId);

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/program/classes/${classId}`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                setProgram(data);
            } else {
                console.error('Failed to fetch program data, status:', response.status);
            }
        } catch (error) {
            console.error('Error fetching program data:', error);
        } finally {
            setIsLoading(false);
        }
    }, [schoolId, classId, authData]);

    useEffect(() => {
        fetchProgramData();
    }, [fetchProgramData]);

    const generateTimeSlots = () => {
        const slots = [];
        let startTime = new Date();
        startTime.setHours(7, 30, 0, 0); // 7:30 AM
        const endTime = new Date();
        endTime.setHours(13, 30, 0, 0); // 1:30 PM

        while (startTime < endTime) {
            const endSlot = new Date(startTime.getTime() + 40 * 60000); // 40 minutes later
            slots.push(`${startTime.toTimeString().substring(0, 5)} - ${endSlot.toTimeString().substring(0, 5)}`);
            startTime = new Date(endSlot.getTime() + 10 * 60000); // 10 minutes break
        }

        return slots;
    };

    const isTimeInRange = (session, timeSlot) => {
        const [slotStart, slotEnd] = timeSlot.split(' - ').map(time => parseTime(time));
        const sessionStart = parseTime(session.startTime);
        const sessionEnd = parseTime(session.endTime);
        return sessionStart >= slotStart && sessionEnd <= slotEnd;
    };

    const parseTime = (timeStr) => {
        const [hours, minutes] = timeStr.split(':').map(Number);
        return new Date(1970, 0, 1, hours, minutes);
    };

    const handleDeleteSession = async (sessionId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/class-sessions/${sessionId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                fetchProgramData(); // Re-fetch program data after deletion
            } else {
                console.error('Failed to delete session, status:', response.status);
            }
        } catch (error) {
            console.error('Error deleting session:', error);
        }
    };

    const handleUpdateSession = (session) => {
        setCurrentSession(session);
        setShowUpdateSessionOverlay(true);
    };

    const handleAddSession = () => {
        setShowAddSessionOverlay(true);
    };

    const handleSaveSession = () => {
        fetchProgramData();
        setShowAddSessionOverlay(false);
        setShowUpdateSessionOverlay(false);
    };

    const handleCreateProgram = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/program/classes/${classId}/create`, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                fetchProgramData();
            } else {
                console.error('Failed to create program, status:', response.status);
            }
        } catch (error) {
            console.error('Error creating program:', error);
        }
    };

    const renderProgramTable = (program) => {
        const daysOfWeek = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];

        if (!program || !program.weeklyProgram) return null;

        const timeSlots = generateTimeSlots();

        return (
            <Table striped bordered hover responsive className="mb-0">
                <thead className="thead-dark">
                <tr>
                    <th>Time / Period</th>
                    {daysOfWeek.map(day => (
                        <th key={day}>{day}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {timeSlots.map((timeSlot, timeIndex) => (
                    <tr key={timeIndex}>
                        <td>{timeSlot}</td>
                        {daysOfWeek.map(day => (
                            <td key={day}>
                                {program.weeklyProgram[day.toUpperCase()]?.map((session, index) => (
                                    isTimeInRange(session, timeSlot) && (
                                        <div key={index} className="mb-2">
                                            <p className="mb-1"><strong>Subject:</strong> {session.subject}</p>
                                            <p className="mb-1"><strong>Teacher:</strong> {session.teacher}</p>
                                            <p className="mb-1">
                                                <strong>Time:</strong> {session.startTime} - {session.endTime}</p>
                                            <Button variant="warning" size="sm" className="me-1"
                                                    onClick={() => handleUpdateSession(session)}>Edit</Button>
                                            <Button variant="danger" size="sm"
                                                    onClick={() => handleDeleteSession(session.id)}>Delete</Button>
                                        </div>
                                    )
                                ))}
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </Table>
        );
    };

    return (
        <Container className="mt-5">

            <div className="d-flex justify-content-between align-items-center mb-4">

                <h3 className="mb-0">Class Program</h3>

                {program ? (
                    <Button variant="primary" onClick={handleAddSession}>Add Session</Button>
                ) : (
                    <Button variant="primary" onClick={handleCreateProgram}>Create Program</Button>
                )}
                <Button variant="secondary" onClick={() => navigate(-1)}>Back</Button>
            </div>
            {isLoading ? (
                <p>Loading program...</p>
            ) : program ? (
                renderProgramTable(program)
            ) : (
                <p>No program available.</p>
            )}

            {showAddSessionOverlay && (
                <AddSessionOverlay
                    onSave={handleSaveSession}
                    programId={program.programId}
                    fetchProgramData={fetchProgramData}
                    handleClose={() => setShowAddSessionOverlay(false)}
                />
            )}

            {showUpdateSessionOverlay && (
                <UpdateSessionOverlay
                    show={showUpdateSessionOverlay}
                    handleClose={() => setShowUpdateSessionOverlay(false)}
                    onSave={handleSaveSession}
                    sessionId={currentSession.id}
                    currentSession={currentSession}
                    setCurrentSession={setCurrentSession}
                />
            )}
        </Container>
    );
};

export default ClassProgram;