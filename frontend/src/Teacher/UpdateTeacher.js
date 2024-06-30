import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import 'bootstrap/dist/css/bootstrap.min.css';

const UpdateTeacher = () => {
    const { schoolId, teacherId } = useParams();
    const [teacherData, setTeacherData] = useState({
        firstName: '',
        lastName: '',
        subjectIds: []
    });
    const [subjects, setSubjects] = useState([]);
    const [subjectNames, setSubjectNames] = useState([]); // Added to store subject names
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTeacherData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setTeacherData({
                        firstName: data.firstName || '',
                        lastName: data.lastName || '',
                        subjectIds: [] // We will set this after fetching all subjects
                    });
                    setSubjectNames(data.subjectNames || []); // Store the subject names
                } else {
                    console.error('Failed to fetch teacher data');
                }
            } catch (error) {
                console.error('Error fetching teacher data:', error);
            }
        };

        const fetchSubjects = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/subject/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setSubjects(data);
                } else {
                    console.error('Failed to fetch subjects');
                }
            } catch (error) {
                console.error('Error fetching subjects:', error);
            }
        };

        fetchTeacherData();
        fetchSubjects();
    }, [schoolId, teacherId, authData]);

    useEffect(() => {
        if (subjects.length > 0 && subjectNames.length > 0) {
            // Map subject names to IDs
            const subjectIds = subjects
                .filter(subject => subjectNames.includes(subject.name))
                .map(subject => subject.id);
            setTeacherData(prevData => ({
                ...prevData,
                subjectIds
            }));
        }
    }, [subjects, subjectNames]);

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
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/${teacherId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(teacherData),
            });
            if (!response.ok) {
                console.error('Failed to update teacher');
            } else {
                navigate(`/admin/dashboard/school/${schoolId}/teachers`);
            }
        } catch (error) {
            console.error('Error updating teacher:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Update Teacher</h2>
            <form onSubmit={handleFormSubmit}>
                {['firstName', 'lastName'].map((field) => (
                    <div className="mb-3" key={field}>
                        <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}</label>
                        <input
                            className="form-control"
                            name={field}
                            value={teacherData[field]}
                            onChange={handleInputChange}
                        />
                    </div>
                ))}
                <div className="mb-3">
                    <label className="form-label">Subjects</label>
                    {subjects.map((subject) => (
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
                    ))}
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
            <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
};

export default UpdateTeacher;