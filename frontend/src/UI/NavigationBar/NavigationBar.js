import React from 'react';
import './NavigationBar.css';
import LoginModal from '../LoginModal/LoginModal';

class NavigationBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoginModalOpen: false,
    };
  }

  handleLoginClick = () => {
    this.setState({ isLoginModalOpen: true });
  };

  handleCloseModal = () => {
    this.setState({ isLoginModalOpen: false });
  };

  render() {
    return (
      <div className="navbar">
        <button className="navbar-button" onClick={this.handleLoginClick}>Вход</button>
        {this.state.isLoginModalOpen && <LoginModal onClose={this.handleCloseModal} />}
      </div>
    );
  }
}

export default NavigationBar;