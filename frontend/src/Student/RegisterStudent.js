import React, { useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const RegisterStudent = () => {
    const { schoolId, classId } = useParams();
    const [studentData, setStudentData] = useState({
        id: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
    });
    const [parentData, setParentData] = useState({
        id: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
    });
    const [showParentForm, setShowParentForm] = useState(false);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleInputChange = (e, setData, data) => {
        const { name, value } = e.target;
        setData({ ...data, [name]: value });
    };

    const handleStudentSubmit = (e) => {
        e.preventDefault();
        setShowParentForm(true);
    };

    const handleParentSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/add-student`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ ...studentData, registerDTO: parentData }),
            });
            if (response.ok) {
                navigate(`/admin/dashboard/school/${schoolId}/class/${classId}/students`);
            } else {
                console.error('Failed to register student');
            }
        } catch (error) {
            console.error('Error registering student:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Register Student</h2>
            {!showParentForm ? (
                <form onSubmit={handleStudentSubmit}>
                    {['id', 'firstName', 'lastName', 'dateOfBirth', 'gender', 'phone', 'email', 'username', 'password'].map((field) => (
                        <div className="mb-3" key={field}>
                            <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}</label>
                            <input
                                type="text"
                                className="form-control"
                                name={field}
                                value={studentData[field]}
                                onChange={(e) => handleInputChange(e, setStudentData, studentData)}
                            />
                        </div>
                    ))}
                    <button type="submit" className="btn btn-primary">Next: Add Parent</button>
                </form>
            ) : (
                <form onSubmit={handleParentSubmit}>
                    <h3>Add Parent Details</h3>
                    {['id', 'firstName', 'lastName', 'dateOfBirth', 'gender', 'phone', 'email', 'username', 'password'].map((field) => (
                        <div className="mb-3" key={field}>
                            <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}</label>
                            <input
                                type="text"
                                className="form-control"
                                name={field}
                                value={parentData[field]}
                                onChange={(e) => handleInputChange(e, setParentData, parentData)}
                            />
                        </div>
                    ))}
                    <button type="submit" className="btn btn-primary">Submit</button>
                </form>
            )}
            <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
};

export default RegisterStudent;