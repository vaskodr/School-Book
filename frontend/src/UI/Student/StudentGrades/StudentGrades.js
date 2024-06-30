import React, { Component } from 'react';

class StudentGrades extends Component {
  constructor(props) {
    super(props);
    this.state = {
      grades: null,
    };
  }

  fetchGradesData = async () => {
    const { studentId, authData, clearContent, setContent } = this.props;
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
        clearContent(); // Clear the content before rendering the new data
        setContent(this.renderGradesTable(data)); // Render the table in the cleared div
      } else {
        console.error('Failed to fetch grades data');
      }
    } catch (error) {
      console.error('Error fetching grades data:', error);
    }
  };

  renderGradesTable = (grades) => {
    if (!grades) return null;

    return (
      <table>
        <thead>
          <tr>
            <th>Grade</th>
            <th>Date</th>
            <th>Subject ID</th>
          </tr>
        </thead>
        <tbody>
          {grades.map(grade => (
            <tr key={grade.id}>
              <td>{grade.grade}</td>
              <td>{grade.date}</td>
              <td>{grade.subjectId}</td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  };

  render() {
    return (
      <button onClick={this.fetchGradesData}>
        Load Student Grades
      </button>
    );
  }
}

export default StudentGrades;
