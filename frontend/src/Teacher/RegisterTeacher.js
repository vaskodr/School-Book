import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const RegisterTeacher = () => {
    const { schoolId } = useParams();
    const [teacherData, setTeacherData] = useState({
        id: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gender: '',
        phone: '',
        email: '',
        username: '',
        password: '',
        subjectIds: []
    });
    const [subjects, setSubjects] = useState([]);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSubjects = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/subject/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log('Fetched subjects:', data); // Debugging line
                    setSubjects(data);
                } else {
                    console.error('Failed to fetch subjects');
                }
            } catch (error) {
                console.error('Error fetching subjects:', error);
            }
        };

        fetchSubjects();
    }, [authData]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setTeacherData({
            ...teacherData,
            [name]: value
        });
    };

    const handleCheckboxChange = (subjectId) => {
        setTeacherData((prevState) => {
            const { subjectIds } = prevState;
            if (subjectIds.includes(subjectId)) {
                return {
                    ...prevState,
                    subjectIds: subjectIds.filter((id) => id !== subjectId)
                };
            } else {
                return {
                    ...prevState,
                    subjectIds: [...subjectIds, subjectId]
                };
            }
        });
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/add-teacher`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(teacherData),
            });
            if (!response.ok) {
                console.error('Failed to register teacher');
            } else {
                navigate(`/admin/dashboard/school/${schoolId}/teachers`);
            }
        } catch (error) {
            console.error('Error registering teacher:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Register Teacher</h2>
            <form onSubmit={handleFormSubmit}>
                {['id', 'firstName', 'lastName', 'dateOfBirth', 'gender', 'phone', 'email', 'username', 'password'].map((field) => (
                    <div className="mb-3" key={field}>
                        <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}</label>
                        <input
                            type="text"
                            className="form-control"
                            name={field}
                            value={teacherData[field]}
                            onChange={handleInputChange}
                        />
                    </div>
                ))}
                <div className="mb-3">
                    <label className="form-label">Subjects</label>
                    {subjects.length === 0 ? (
                        <p>No subjects available.</p>
                    ) : (
                        subjects.map((subject) => (
                            <div className="form-check" key={subject.id}>
                                <input
                                    type="checkbox"
                                    className="form-check-input"
                                    id={`subject-${subject.id}`}
                                    checked={teacherData.subjectIds.includes(subject.id)}
                                    onChange={() => handleCheckboxChange(subject.id)}
                                />
                                <label className="form-check-label" htmlFor={`subject-${subject.id}`}>
                                    {subject.name}
                                </label>
                            </div>
                        ))
                    )}
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
            <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
};

export default RegisterTeacher;