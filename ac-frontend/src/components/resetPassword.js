import React, {useEffect, useState} from "react";
import {HiOutlineMail} from "react-icons/hi";
import {RiLockPasswordLine} from "react-icons/ri";
import {Link, useNavigate, useLocation} from "react-router-dom";
import axios from "../api/axios";
import { ApiService } from "../lib/api";

const FORGET_URL = "/forgot-password";

const ResetPassword = () => {

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get('token');
    console.log("token: "+token);
    const [pwd, setPwd] = useState("");

    const [matchPwd, setMatchPwd] = useState("");
    const [validMatch, setValidMatch] = useState(false);
    const [errMsg, setErrMsg] = useState("");
    const navigate = useNavigate();

    //Hook that makes sure both passwords are the same
    useEffect(() => {
        setValidMatch(pwd === matchPwd);
    }, [pwd, matchPwd]);

    //Hook that clears Errormessages an soon as a User changes the Input inside the Formfield
    useEffect(() => {
        setErrMsg("");
    }, [pwd, matchPwd]);

    const handleSumbit = async (e) => {
        e.preventDefault();
        const response = await ApiService.postResetPassword({
            password: pwd,
            token: token
        });
        if(response === "success"){
            navigate("/protected", { state: { message: "login message" } });
        }else{
            setErrMsg(response);
        }
       console.log("handle submit");
    };

    return (
        <div className="container">
            <div className="forms">
                <div className="form login">
                    <p className={errMsg ? "errMsg" : "offScreen"} aria-live="assertive">
                        {errMsg}
                    </p>
                    <span className="title">Reset Password</span>
                    <form onSubmit={handleSumbit}>
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
                        <div className="input-field">
                            <input
                                type="password"
                                placeholder="Confirm your password"
                                required
                                onChange={(e) => setMatchPwd(e.target.value)}
                                value={matchPwd}
                            />
                            <i>
                                <RiLockPasswordLine />
                            </i>
                        </div>
                        <button className="input-field button">Sign Up</button>
                    </form>
                </div>
            </div>
        </div>
    );
};



export default ResetPassword;