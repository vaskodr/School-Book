// src/Class/ClassProgram.js
import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Button, Table } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const daysOfWeek = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY'];

const ClassProgram = () => {
    const { schoolId, classId } = useParams();
    const [program, setProgram] = useState(null);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const fetchProgram = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/program`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                setProgram(data);
            } else {
                console.error('Failed to fetch program');
            }
        } catch (error) {
            console.error('Error fetching program:', error);
        }
    };

    useEffect(() => {
        fetchProgram();
    }, [schoolId, classId, authData]);

    const handleEditSession = (sessionId) => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/program/${sessionId}/update`);
    };

    const handleDeleteSession = async (sessionId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/class/${classId}/program/${sessionId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setProgram({
                    ...program,
                    classSessions: program.classSessions.filter(session => session.id !== sessionId)
                });
            } else {
                console.error('Failed to delete session');
            }
        } catch (error) {
            console.error('Error deleting session:', error);
        }
    };

    const handleAddSession = () => {
        navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/program/create`);
    };

    const generateTimeSlots = () => {
        const timeSlots = new Set();
        program.classSessions.forEach(session => {
            timeSlots.add(`${session.startTime} - ${session.endTime}`);
        });
        return Array.from(timeSlots).sort();
    };

    const renderTableCells = (day, timeSlot) => {
        const session = program.classSessions.find(session => session.day === day && `${session.startTime} - ${session.endTime}` === timeSlot);
        return session ? (
            <div>
                Teacher ID: {session.teacherId}
                <br />
                Subject ID: {session.subjectId}
                <br />
                <Button variant="warning" size="sm" onClick={() => handleEditSession(session.id)} className="me-2">Edit</Button>
                <Button variant="danger" size="sm" onClick={() => handleDeleteSession(session.id)}>Delete</Button>
            </div>
        ) : <div>No Sessions</div>;
    };

    const timeSlots = program ? generateTimeSlots() : [];

    return (
        <div className="container mt-5">
            <h2 className="text-center">Class Program for Class {classId}</h2>
            <Button variant="primary" onClick={handleAddSession} className="mb-3">
                Add Session
            </Button>
            {program ? (
                <Table bordered>
                    <thead>
                    <tr>
                        <th>Time / Period</th>
                        {daysOfWeek.map(day => <th key={day}>{day}</th>)}
                    </tr>
                    </thead>
                    <tbody>
                    {timeSlots.map(timeSlot => (
                        <tr key={timeSlot}>
                            <td>{timeSlot}</td>
                            {daysOfWeek.map(day => (
                                <td key={day}>
                                    {renderTableCells(day, timeSlot)}
                                </td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </Table>
            ) : (
                <p className="text-center">Loading program...</p>
            )}
            <Button variant="secondary" onClick={() => navigate(-1)}>
                Back to Classes
            </Button>
        </div>
    );
};

export default ClassProgram;