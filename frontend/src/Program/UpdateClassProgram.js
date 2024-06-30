// src/Program/UpdateProgram.js
import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const UpdateProgram = () => {
    const { schoolId, classId, programId } = useParams();
    const [name, setName] = useState('');
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProgram = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/program/classes/${classId}/${programId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setName(data.name);
                } else {
                    console.error('Failed to fetch program details');
                }
            } catch (error) {
                console.error('Error fetching program details:', error);
            }
        };

        fetchProgram();
    }, [schoolId, classId, programId, authData]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/program/classes/${classId}/${programId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ name }),
            });
            if (response.ok) {
                navigate(-1); // Go back to the program list
            } else {
                console.error('Failed to update program');
            }
        } catch (error) {
            console.error('Error updating program:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Update Program</h2>
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
                <button type="submit" className="btn btn-primary">Update</button>
                <button type="button" className="btn btn-secondary ms-2" onClick={() => navigate(-1)}>Cancel</button>
            </form>
        </div>
    );
};

export default UpdateProgram;