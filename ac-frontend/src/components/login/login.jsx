import React from "react";
import { HiOutlineMail } from "react-icons/hi";
import { RiLockPasswordLine } from "react-icons/ri";
import { AuthService } from "../../lib/util";
import "./login.scss";
import { useRef, useState, useEffect } from "react";
import jwt_decode from "jwt-decode";
import { Link, useNavigate, useLocation } from "react-router-dom";
import axios from "../../api/axios";
const LOGIN_URL = "/auth/login";

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    setErrMsg("");
  }, [email, pwd]);

  const handleSumbit = async (e) => {
    try {
      e.preventDefault();

      console.log("inside handle submit");
      const response = await AuthService.postData(LOGIN_URL, {
        email: email,
        password: pwd,
      });
      const saveToken = await AuthService.saveToken(response);
      const savedTokenFromStorage = await AuthService.getCurrentToken();
      navigate("/protected", { state: { message: "login message" } });
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status !== 200) {
        setErrMsg("Login Failed");
      } else {
        setErrMsg("Login Failed");
      }
    }
  };

  return (
    <div className="container">
      <div className="forms">
        <p className={errMsg ? "errMsg" : "offScreen"} aria-live="assertive">
          {errMsg}
        </p>
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
              <button className="button__secondary">Sign In</button>
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
