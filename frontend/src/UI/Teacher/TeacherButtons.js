import React from 'react';

const TeacherButtons = ({ schoolId, subjects, authData, teacherId }) => (
    <div>
        {subjects && subjects.length > 0 ? (
            subjects.map(subject => (
                <button key={subject.id} onClick={() => handleSubjectClick(schoolId, subject.id, authData, teacherId)}>
                    {subject.name}
                </button>
            ))
        ) : (
            <p>No subjects available</p>
        )}
    </div>
);

const handleSubjectClick = (schoolId, subjectId, authData, teacherId) => {
    console.log(`Fetch students for schoolId: ${schoolId}, subjectId: ${subjectId}, teacherId: ${teacherId}`);
    // Implement the fetch logic here...
};

export default TeacherButtons;