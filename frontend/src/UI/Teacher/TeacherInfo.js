import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './TeacherInfo.css'; // Import custom styles

class TeacherInfo extends React.Component {
    render() {
        const { firstName, lastName, schoolName } = this.props;

        return (
            <div className="container mt-5">
                <div className="custom-card card shadow-lg">
                    <div className="card-header custom-card-header">
                        <h2 className="card-title">
                            Information for {firstName} {lastName}
                        </h2>
                    </div>
                    <div className="card-body">
                        {schoolName ? (
                            <>
                                <h4>School: {schoolName}</h4>
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

export default TeacherInfo;