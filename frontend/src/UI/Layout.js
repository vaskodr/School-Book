import React from 'react';
import NavigationBar from './NavigationBar/NavigationBar';

const Layout = ({ children }) => {
  return (
    <div>
      <NavigationBar />
      <main>{children}</main>
    </div>
  );
};

export default Layout;
