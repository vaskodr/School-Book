import React, { useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const CreateClass = () => {
    const { schoolId } = useParams();
    const [name, setName] = useState('');
    const [level, setLevel] = useState('');
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const newClass = {
            name,
            level,
            schoolId
        };

        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/add-class`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(newClass),
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

    return (
        <div>
            <h2>Create Class</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div>
                    <label>Level</label>
                    <input type="text" value={level} onChange={(e) => setLevel(e.target.value)} required />
                </div>
                <button type="submit">Create Class</button>
            </form>
        </div>
    );
};

export default CreateClass;