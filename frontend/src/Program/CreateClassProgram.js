import React, { useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const CreateClassProgram = () => {
    const { schoolId, classId } = useParams();
    const [name, setName] = useState('');
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/program/classes/${classId}/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ name }),
            });
            if (response.ok) {
                navigate(-1); // Go back to the program list
            } else {
                console.error('Failed to create program');
            }
        } catch (error) {
            console.error('Error creating program:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Create Program</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
                <button type="submit" className="btn btn-primary">Create</button>
                <button type="button" className="btn btn-secondary ms-2" onClick={() => navigate(-1)}>Cancel</button>
            </form>
        </div>
    );
};

export default CreateClassProgram;