import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './StudentInfo.css'; // Import custom styles

class StudentInfo extends React.Component {
    render() {
        const { firstName, lastName, classDTO } = this.props;

        return (
            <div className="container mt-5">
                <div className="custom-card card shadow-lg">
                    <div className="card-header custom-card-header">
                        <h2 className="card-title">
                            Information for {firstName} {lastName}
                        </h2>
                    </div>
                    <div className="card-body">
                        {classDTO ? (
                            <>
                                <h4>School: {classDTO.schoolName}</h4>
                                <p>Class: {classDTO.name} - Level: {classDTO.level}</p>
                            </>
                        ) : (
                            <div className="spinner-border text-primary" role="status">
                                <span className="sr-only">Loading school name...</span>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        );
    }
}

export default StudentInfo;