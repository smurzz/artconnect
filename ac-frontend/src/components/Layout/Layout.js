import React, {useEffect} from "react";
import Footer from "../footer/footer";
import { Outlet } from "react-router-dom";
import {logikService} from "../../lib/service";
import {Link,Navigate} from "react-router-dom";
import {  useState } from "react";
import HeaderLogout from "../../components/headerLogout/header";
const Layout = ({ children }) => {
  return (
    <React.Fragment >
        <header className="container">
        <HeaderLogout></HeaderLogout>
        </header>
      <main>{children}</main>
        <Footer />
    </React.Fragment>
  );
};
export default Layout;
