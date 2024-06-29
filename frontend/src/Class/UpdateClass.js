import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const UpdateClass = () => {
    const { schoolId, classId } = useParams();
    const [name, setName] = useState('');
    const [level, setLevel] = useState('');
    const [mentorId, setMentorId] = useState('');
    const [studentIds, setStudentIds] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClassDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/class/${classId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setName(data.name);
                    setLevel(data.level);
                    setMentorId(data.mentorId);
                    setStudentIds(data.studentIds);
                } else {
                    console.error('Failed to fetch class details');
                }
            } catch (error) {
                console.error('Error fetching class details:', error);
            }
        };

        fetchClassDetails();
    }, [schoolId, classId, authData]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const updatedClass = {
            name,
            level,
            mentorId,
            studentIds,
            schoolId
        };

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/class/${classId}`, {
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
        <div>
            <h2>Update Class</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div>
                    <label>Level</label>
                    <input type="text" value={level} onChange={(e) => setLevel(e.target.value)} required />
                </div>
                <div>
                    <label>Mentor ID</label>
                    <input type="text" value={mentorId} onChange={(e) => setMentorId(e.target.value)} required />
                </div>
                <div>
                    <label>Student IDs (comma separated)</label>
                    <input type="text" value={studentIds} onChange={(e) => setStudentIds(e.target.value.split(','))} />
                </div>
                <button type="submit">Update Class</button>
            </form>
        </div>
    );
};

export default UpdateClass;