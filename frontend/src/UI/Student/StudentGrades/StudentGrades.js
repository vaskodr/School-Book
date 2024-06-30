import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

class StudentGrades extends Component {
    constructor(props) {
        super(props);
        this.state = {
            grades: null,
            showGrades: false,
        };
    }

    fetchGradesData = async () => {
        const { studentId, authData } = this.props;
        console.log('Fetching grades data for studentId:', studentId); // Debugging log

        try {
            const response = await fetch(`http://localhost:8080/api/grades/student/${studentId}`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                console.log('Fetched grades data:', data); // Debugging log
                this.setState({ grades: data, showGrades: true });
            } else {
                console.error('Failed to fetch grades data');
            }
        } catch (error) {
            console.error('Error fetching grades data:', error);
        }
    };

    toggleGradesVisibility = () => {
        this.setState(prevState => ({ showGrades: !prevState.showGrades }));
    };

    renderGradesTable = (grades) => {
        if (!grades) return null;

        return (
            <table className="table table-striped table-bordered mt-3">
                <thead className="thead-dark">
                <tr>
                    <th>Grade</th>
                    <th>Date</th>
                    <th>Subject</th>
                </tr>
                </thead>
                <tbody>
                {grades.map(grade => (
                    <tr key={grade.id}>
                        <td>{grade.grade}</td>
                        <td>{new Date(grade.date).toLocaleDateString()}</td>
                        <td>{grade.subjectName}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        );
    };

    render() {
        const { grades, showGrades } = this.state;

        return (
            <div className="container mt-5">
                {!showGrades ? (
                    <button className="btn btn-primary mb-3" onClick={this.fetchGradesData}>
                        Load Student Grades
                    </button>
                ) : (
                    <>
                        {this.renderGradesTable(grades)}
                        <button className="btn btn-secondary mt-3" onClick={this.toggleGradesVisibility}>
                            Hide Grades
                        </button>
                    </>
                )}
            </div>
        );
    }
}

export default StudentGrades;