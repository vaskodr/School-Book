import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const StudentDashboard = () => {
    const [schoolName, setSchoolName] = useState('');
    const [studentData, setStudentData] = useState(null);
    const [program, setProgram] = useState(null); // State to store program data
    const { schoolId } = useParams();
    const { authData } = useContext(AuthContext);

    useEffect(() => {
        console.log('Auth data in useEffect:', authData); // Debugging log

        if (!authData || !authData.userDetailsDTO) {
            console.error('Auth data or userDetailsDTO is not available');
            return;
        }

        const fetchSchoolName = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched school data:', data); // Debugging log
                    setSchoolName(data.name);
                } else {
                    console.error('Failed to fetch school name');
                }
            } catch (error) {
                console.error('Error fetching school name:', error);
            }
        };

        const fetchStudentData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/student/${authData.userDetailsDTO.id}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched student data:', data); // Debugging log
                    setStudentData(data);

                    // Fetch program data based on the classId from student data
                    if (data.classDTO && data.classDTO.id) {
                        fetchProgramData(data.classDTO.id);
                    }
                } else {
                    console.error('Failed to fetch student data');
                }
            } catch (error) {
                console.error('Error fetching student data:', error);
            }
        };

        const fetchProgramData = async (classId) => {
            console.log('Fetching program data for classId:', classId); // Debugging log
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/program`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched program data:', data); // Debugging log
                    setProgram(data);
                } else {
                    console.error('Failed to fetch program data');
                }
            } catch (error) {
                console.error('Error fetching program data:', error);
            }
        };

        fetchSchoolName();
        fetchStudentData();
    }, [schoolId, authData]);

    if (!authData || !authData.userDetailsDTO) {
        return <div>Loading auth data...</div>;
    }

    const { firstName, lastName } = authData.userDetailsDTO;

    const daysOfWeek = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];

    const renderProgramTable = () => {
        if (!program || !program.classSessions) return null;

        const sessionsByDay = daysOfWeek.reduce((acc, day) => {
            acc[day] = program.classSessions.filter(session => session.day === day);
            return acc;
        }, {});

        return (
            <table>
                <thead>
                <tr>
                    {daysOfWeek.map(day => (
                        <th key={day}>{day}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                <tr>
                    {daysOfWeek.map(day => (
                        <td key={day}>
                            {sessionsByDay[day].map(session => (
                                <div key={session.id}>
                                    <p>{session.startTime} - {session.endTime}</p>
                                    <p>Subject ID: {session.subjectId}</p>
                                    <p>Teacher ID: {session.teacherId}</p>
                                </div>
                            ))}
                        </td>
                    ))}
                </tr>
                </tbody>
            </table>
        );
    };

    return (
        <div>
            <h2>
                Welcome {firstName} {lastName}
            </h2>
            {schoolName ? <h2>School: {schoolName}</h2> : <p>Loading school name...</p>}
            {studentData ? (
                <p>Class: {studentData.classDTO?.name} - Level: {studentData.classDTO?.level}</p>
            ) : (
                <p>Loading student class info...</p>
            )}
            {program ? (
                <div>
                    <h3>Program:</h3>
                    {renderProgramTable()}
                </div>
            ) : (
                <p>Loading program data...</p>
            )}
        </div>
    );
};

export default StudentDashboard;