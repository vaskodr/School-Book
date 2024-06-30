import React from 'react';

class StudentInfo extends React.Component {
  render() {
    const { firstName, lastName, classDTO } = this.props;

    return (
      <div>
        <h2>
          Information for {firstName} {lastName}
        </h2>
        {classDTO ? <h2>School: {classDTO.schoolName}</h2> : <p>Loading school name...</p>}
        {classDTO ? (
          <p>Class: {classDTO.name} - Level: {classDTO.level}</p>
        ) : (
          <p>Loading student class info...</p>
        )}
      </div>
    );
  }
}

export default StudentInfo;
