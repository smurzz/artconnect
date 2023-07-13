import React, {useEffect} from "react";
import { Link } from "react-router-dom";
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";
export default function PageNotFound() {
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])

    return (
      <>
          {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
        <main className="grid min-h-full place-items-center bg-white px-6 py-24 sm:py-32 lg:px-8">
          <div className="text-center">
            <p className="text-base font-semibold text-indigo-600">404</p>
            <h1 className="mt-4 text-3xl font-bold tracking-tight text-gray-900 sm:text-5xl">Page not found</h1>
            <p className="mt-6 text-base leading-7 text-gray-600">Sorry, we couldn’t find the page you’re looking for.</p>
            <div className="mt-10 flex items-center justify-center gap-x-6">
                <Link to="/" className="mt-6 text-lg leading-8 text-gray-600">Go Back home, hopeless Wanderer</Link>

              <a href="#" className="text-sm font-semibold text-gray-900">
              </a>
            </div>
          </div>
        </main>
      </>
    )
  }
  