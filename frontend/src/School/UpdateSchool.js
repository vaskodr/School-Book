import React, { useState, useContext, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const UpdateSchool = () => {
    const { schoolId } = useParams();
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();
    const [school, setSchool] = useState({
        name: '',
        address: '',
    });

    useEffect(() => {
        const fetchSchool = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setSchool(data);
                } else {
                    console.error('Failed to fetch school');
                }
            } catch (error) {
                console.error('Error fetching school:', error);
            }
        };

        fetchSchool();
    }, [schoolId, authData.accessToken]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSchool((prevSchool) => ({
            ...prevSchool,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(school),
            });
            if (response.ok) {
                navigate('/admin/dashboard');
            } else {
                console.error('Failed to update school');
            }
        } catch (error) {
            console.error('Error updating school:', error);
        }
    };

    return (
        <div>
            <h2>Update School</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name:</label>
                    <input type="text" name="name" value={school.name} onChange={handleChange} />
                </div>
                <div>
                    <label>Address:</label>
                    <input type="text" name="address" value={school.address} onChange={handleChange} />
                </div>
                <button type="submit">Save</button>
            </form>
        </div>
    );
};

export default UpdateSchool;