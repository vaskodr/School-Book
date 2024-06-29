import React from 'react';
import NavigationBar from './UI/NavigationBar/NavigationBar';
import './App.css';

function App() {

    return (
        // <AuthProvider>
        //     <Router>
        //         <Routes>
        //             <Route path="/login" element={<LoginForm />} />
        //             <Route path="/student/dashboard/:schoolId" element={<StudentDashboard />} />
        //             <Route path="/admin/dashboard" element={<AdminDashboard />} />
        //             <Route path="/teacher/dashboard/:schoolId" element={<TeacherDashboard />} />
        //             <Route path="/admin/dashboard/school/:schoolId/classes" element={<SchoolClasses />} />
        //             <Route path="/admin/dashboard/school/:schoolId/class/:classId" element={<ClassDetails />} />
        //             {/* Add other routes here */}
        //             <Route path="*" element={<Navigate to="/login" />} />
        //         </Routes>
        //     </Router>
        // </AuthProvider>
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