import React from "react";
import { HiOutlineMail } from "react-icons/hi";
import { RiLockPasswordLine } from "react-icons/ri";
import postData from "../../lib/util";
import "./login.css";

import { useRef, useState, useEffect } from "react";

import { Link, useNavigate, useLocation } from "react-router-dom";
import axios from "../../api/axios";
const LOGIN_URL = "/auth/login";

const Login = () => {
  const [email, setEmail] = useState(" ");
  const [pwd, setPwd] = useState("");
  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    setErrMsg("");
  }, [email, pwd]);

  const handleSumbit = async (e) => {
    try {
      e.preventDefault();

      const response = await postData(LOGIN_URL, {
        email: email,
        password: pwd,
      });
      console.log(JSON.stringify(response));
      const accessToken = response?.data.access_token;
      const refreshToken = response?.data.refresh_token;
      console.log("accessToken: " + accessToken);
      console.log("refreshToken: " + refreshToken);
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        setErrMsg("Username Taken");
      } else {
        setErrMsg("Login Failed");
      }
    }
  };

  return (
    <div className="container">
      <p className={errMsg ? "errMsg" : "offScreen"} aria-live="assertive">
        {errMsg}
      </p>
      <div className="forms">
        <div className="form login">
          <span className="title">Login</span>

          <form onSubmit={handleSumbit}>
            <div className="input-field">
              <input
                type="text"
                placeholder="Enter your email"
                required
                onChange={(e) => setEmail(e.target.value)}
                value={email}
              />
              <i>
                <HiOutlineMail />
              </i>
            </div>
            <div className="input-field">
              <input
                type="password"
                placeholder="Enter your password"
                required
                onChange={(e) => setPwd(e.target.value)}
                value={pwd}
              />
              <i>
                <RiLockPasswordLine />
              </i>
            </div>

            <div className="padding-t">
              <button className="input-field button">Sign In</button>
            </div>
          </form>
          <div className="checkbox-text">
            <p>Not a member yet ?</p>
            <Link to="/Register" className="text">
              Sign Up
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
