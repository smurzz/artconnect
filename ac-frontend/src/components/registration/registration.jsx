import React from "react";
import { HiOutlineMail } from "react-icons/hi";
import { RiLockPasswordLine } from "react-icons/ri";
import { BsPerson } from "react-icons/bs";
import "./registration.css";
import { useState, useEffect } from "react";
import { Link, Route, Routes, useNavigate } from "react-router-dom";
import "./registration.css";
import { AuthService } from "../../lib/util";

const REGISTER_URL = "/auth/register";
const Registration = () => {
  const [firstname, setFirstname] = useState("");
  const [firstnameFocus, setFirstnameFocus] = useState(false);

  const [success, setSuccess] = useState(false);

  const [lastname, setLastname] = useState("");
  const [lastnameFocus, setLastnameFocus] = useState(false);

  const [email, setEmail] = useState("");
  const [emailFocus, setEmailFocus] = useState(false);

  const [pwd, setPwd] = useState("");
  const [pwdFocus, setPwdFocus] = useState(false);

  const [matchPwd, setMatchPwd] = useState("");
  const [validMatch, setValidMatch] = useState(false);
  const [matchFocus, setMatchFocus] = useState(false);

  const [errMsg, setErrMsg] = useState("");
  const navigate = useNavigate();

  //Hook that makes sure both passwords are the same
  useEffect(() => {
    setValidMatch(pwd === matchPwd);
  }, [pwd, matchPwd]);

  //Hook that clears Errormessages an soon as a User changes the Input inside the Formfield
  useEffect(() => {
    setErrMsg("");
  }, [email, firstname, lastname, pwd, matchPwd]);

  const handleSumbit = async (e) => {
    e.preventDefault();
    try {
      console.log(firstname, lastname, email);
      const response = await AuthService.postData(REGISTER_URL, {
        firstname: firstname,
        lastname: lastname,
        email: email,
        password: pwd,
      });
      setSuccess(true);
      setPwd("");
      setMatchPwd("");
      setEmail("");
      setFirstname("");
      setLastname("");
      console.log("before reponseData:");
      console.log("isnide reponseData:");
      navigate("/protected", { state: { message: response.data } });
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        console.log("in errHeader");
        setErrMsg("Email Taken");
      } else {
        setErrMsg("Registration Failed");
      }
    }
  };

  return (
    <div className="container">
      <div className="forms">
        <div className="form login">
          <p className={errMsg ? "errMsg" : "offScreen"} aria-live="assertive">
            {errMsg}
          </p>
          <span className="title">Registration</span>
          <form onSubmit={handleSumbit}>
            <div className="input-field">
              <input
                type="text"
                autoComplete="off"
                onChange={(e) => setFirstname(e.target.value)}
                required
                onFocus={() => setFirstnameFocus(true)}
                onBlur={() => setFirstnameFocus(false)}
                placeholder="Enter your firstname"
              />
              <i>
                <BsPerson />
              </i>
            </div>
            <div className="input-field">
              <input
                type="text"
                onChange={(e) => setLastname(e.target.value)}
                required
                onFocus={() => setLastnameFocus(true)}
                onBlur={() => setLastnameFocus(false)}
                placeholder="Enter your surname"
              />
              <i>
                <BsPerson />
              </i>
            </div>
            <div className="input-field">
              <input
                type="email"
                autoComplete="off"
                onChange={(e) => setEmail(e.target.value)}
                onFocus={() => setEmailFocus(true)}
                onBlur={() => setEmailFocus(false)}
                placeholder="Enter your email"
                required
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
                onFocus={() => setPwdFocus(true)}
                onBlur={() => setPwdFocus(false)}
              />
              <i>
                <RiLockPasswordLine />
              </i>
            </div>
            <div className="input-field">
              <input
                type="password"
                placeholder="Confirm your password"
                required
                onChange={(e) => setMatchPwd(e.target.value)}
                value={matchPwd}
                onFocus={() => setMatchFocus(true)}
                onBlur={() => setMatchFocus(false)}
              />
              <i>
                <RiLockPasswordLine />
              </i>
            </div>
            <button className="input-field button">Sign Up</button>
          </form>
          <div className="checkbox-text">
            <p>Already have an account ?</p>
            <Link to="/Login" className="text">
              Login
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Registration;
