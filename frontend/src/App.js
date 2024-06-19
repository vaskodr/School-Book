import React from 'react';
import NavigationBar from './UI/NavigationBar/NavigationBar';
import './App.css';

function App() {

  return (
    <div>
      <NavigationBar />
      <div className="text-box left-box">
        <h1>Електронен дневник</h1>

        <p>Добре дошли в онлайн платформата, създадена за удобство в следенето и управлението на учебния процес!</p>
        <p>Натиснете "Вход", за да влезете в акаунта си, предоставен от училището.</p>
      </div>
    </div>
  );
}

export default App;