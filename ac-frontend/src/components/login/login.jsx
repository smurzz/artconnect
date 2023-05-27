import React, {useEffect, useState} from "react";
import {HiOutlineMail} from "react-icons/hi";
import {RiLockPasswordLine} from "react-icons/ri";
import { ApiService } from "../../lib/api";
import { AuthService } from "../../lib/util"
import "./login.scss";
import {Link, useNavigate} from "react-router-dom";

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
      e.preventDefault();
      const response = await ApiService.postLogin({
        email: email,
        password: pwd,
      });
      if(response === "success"){
        navigate("/protected", { state: { message: "login message" } });
      }else{
      setErrMsg(response);
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
