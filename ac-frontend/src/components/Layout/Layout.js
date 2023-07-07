import React, {useEffect} from "react";
import Footer from "../footer/footer";
import { Outlet } from "react-router-dom";
import {logikService} from "../../lib/service";
import {Link,Navigate} from "react-router-dom";
import {  useState } from "react";
const Layout = ({ children }) => {

  return (
    <React.Fragment >
      {children}
        <Footer />
    </React.Fragment>
  );
};
export default Layout;
