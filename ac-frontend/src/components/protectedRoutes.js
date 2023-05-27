import { Outlet } from "react-router-dom";
import {logikService} from "../lib/service";
import {Link,Navigate} from "react-router-dom";
import { useEffect, useState } from "react";
const  ProtectedRoutes = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [isAuth, setIsAuth] = useState(false);
    console.log("inside protected routes");
    useEffect(() => {
        const checkAuth = async () => {
            const authStatus = await logikService.isLoggedIn();
            setIsAuth(authStatus);
            setIsLoading(false);
        };
        checkAuth();
    }, []);
    if (isLoading) {
        return <div>Loading...</div>;
    }
    return isAuth ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;