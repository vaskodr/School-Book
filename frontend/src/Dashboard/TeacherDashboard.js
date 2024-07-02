import React, { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import TeacherButtons from '../UI/Teacher/TeacherButtons';
import TeacherInfo from '../UI/Teacher/TeacherInfo';
import TeacherProgram from '../UI/Teacher/TeacherProgram';

const TeacherDashboard = () => {
    const [teacherData, setTeacherData] = useState(null);
    const [subjects, setSubjects] = useState([]);
    const { authData } = useContext(AuthContext);

    useEffect(() => {
        console.log('Auth data in useEffect:', authData); // Debugging log

        if (!authData || !authData.userDetailsDTO) {
            console.error('Auth data or userDetailsDTO is not available');
            return;
        }

        const fetchTeacherData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/teachers/${authData.userDetailsDTO.id}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched teacher data:', data); // Debugging log
                    setTeacherData(data);

                    // Fetch subjects after fetching teacher data
                    const subjectsResponse = await fetch(`http://localhost:8080/api/school/${data.schoolId}/teacher/${data.id}/subjects`, {
                        headers: {
                            Authorization: `Bearer ${authData.accessToken}`,
                        },
                    });
                    if (subjectsResponse.ok) {
                        const subjectsData = await subjectsResponse.json();
                        console.log('Fetched subjects:', subjectsData); // Debugging log
                        setSubjects(subjectsData);
                    } else {
                        console.error('Failed to fetch subjects');
                    }
                } else {
                    console.error('Failed to fetch teacher data');
                }
            } catch (error) {
                console.error('Error fetching teacher data:', error);
            }
        };

        fetchTeacherData();
    }, [authData]);

    if (!authData || !authData.userDetailsDTO) {
        return <div>Loading auth data...</div>;
    }

    const { firstName, lastName } = authData.userDetailsDTO;

    return (
        <div>
            <TeacherInfo
                firstName={firstName}
                lastName={lastName}
                schoolName={teacherData ? teacherData.schoolName : ''}
            />
            {subjects && subjects.length > 0 ? (
                <TeacherButtons
                    schoolId={teacherData ? teacherData.schoolId : ''}
                    subjects={subjects}
                    authData={authData}
                    teacherId={teacherData ? teacherData.id : ''}
                />
            ) : (
                <p>Loading teacher data...</p>
            )}
            {teacherData && (
                <TeacherProgram
                    schoolId={teacherData.schoolId}
                    teacherId={teacherData.id}
                    authData={authData}
                />
            )}
        </div>
    );
};

export default TeacherDashboard;