import React from "react";
import logo from "../../images/Logo.png";
import "./header.css";
const Header = () => {
  return (
    <header>
      <img className="header__logo" src={logo} alt="ArtConnectLogo" />
      <nav className="navbar"></nav>
      <div class="navbar__container"></div>
    </header>
  );
};
export default Header;
