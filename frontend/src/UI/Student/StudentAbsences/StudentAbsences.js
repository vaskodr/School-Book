import React, { Component } from 'react';

class StudentAbsences extends Component {
  constructor(props) {
    super(props);
    this.state = {
      absences: null,
    };
  }

  fetchAbsencesData = async () => {
    const { studentId, authData, clearContent, setContent } = this.props;
    console.log('Fetching absences data for studentId:', studentId); // Debugging log

    try {
      const response = await fetch(`http://localhost:8080/api/absences/student/${studentId}`, {
        headers: {
          Authorization: `Bearer ${authData.accessToken}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        console.log('Fetched absences data:', data); // Debugging log
        clearContent(); // Clear the content before rendering the new data
        setContent(this.renderAbsencesTable(data)); // Render the table in the cleared div
      } else {
        console.error('Failed to fetch absences data');
      }
    } catch (error) {
      console.error('Error fetching absences data:', error);
    }
  };

  renderAbsencesTable = (absences) => {
    if (!absences) return null;

    return (
      <table>
        <thead>
          <tr>
            <th>Description</th>
            <th>Date</th>
            <th>Class Session ID</th>
          </tr>
        </thead>
        <tbody>
          {absences.map(absence => (
            <tr key={absence.id}>
              <td>{absence.description}</td>
              <td>{absence.date}</td>
              <td>{absence.classSessionId}</td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  };

  render() {
    return (
      <button onClick={this.fetchAbsencesData}>
        Load Student Absences
      </button>
    );
  }
}

export default StudentAbsences;
