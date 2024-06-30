import React, { useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const CreateClass = () => {
    const { schoolId } = useParams();
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();
    const [className, setClassName] = useState('');
    const [classLevel, setClassLevel] = useState('FIRST');

    const handleFormSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/add-class`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ name: className, level: classLevel }),
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

    const handleBackClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/classes`);
    };

    return (
        <div className="container mt-4">
            <h2>Create New Class</h2>
            <form onSubmit={handleFormSubmit}>
                <div className="mb-3">
                    <label htmlFor="className" className="form-label">Class Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="className"
                        value={className}
                        onChange={(e) => setClassName(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="classLevel" className="form-label">Class Level</label>
                    <select
                        className="form-select"
                        id="classLevel"
                        value={classLevel}
                        onChange={(e) => setClassLevel(e.target.value)}
                        required
                    >
                        <option value="FIRST">FIRST</option>
                        <option value="SECOND">SECOND</option>
                        <option value="THIRD">THIRD</option>
                        <option value="FOURTH">FOURTH</option>
                        <option value="FIFTH">FIFTH</option>
                        <option value="SIXTH">SIXTH</option>
                        <option value="SEVENTH">SEVENTH</option>
                        <option value="EIGHTH">EIGHTH</option>
                        <option value="NINTH">NINTH</option>
                        <option value="TENTH">TENTH</option>
                        <option value="ELEVENTH">ELEVENTH</option>
                        <option value="TWELFTH">TWELFTH</option>
                    </select>
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
                <button type="button" className="btn btn-secondary ms-2" onClick={handleBackClick}>Back</button>
            </form>
        </div>
    );
};

export default CreateClass;