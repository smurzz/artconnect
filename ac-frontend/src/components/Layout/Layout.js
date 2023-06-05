import React, {useEffect} from "react";
import Header from "../header/header";
import Footer from "../footer/footer";
import { Outlet } from "react-router-dom";
import {logikService} from "../../lib/service";
import {Link,Navigate} from "react-router-dom";
import {  useState } from "react";
import HeaderLogout from "../../components/headerLogout/header";
const Layout = ({ children }) => {
    const [isAuth, setIsAuth] = useState(false);
    useEffect(() => {
        const checkAuth = async () => {
            const authStatus = await logikService.isLoggedIn();
            setIsAuth(authStatus);
        };
        checkAuth();
    }, [isAuth]);
  return (
    <React.Fragment>
        {isAuth ? <Header />: <HeaderLogout/>}

      <main>{children}</main>
        <Footer />
    </React.Fragment>
  );
};
export default Layout;
