import React, {useEffect, useState} from "react";
import {HiOutlineMail} from "react-icons/hi";
import {RiLockPasswordLine} from "react-icons/ri";
import {Link, useNavigate} from "react-router-dom";
import axios from "../api/axios";

const FORGET_URL = "/forgot-password";

const ForgotPassword = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    axios.get('https://www.example.com/search',)
        .then(response => {
            // handle success
        })
        .catch(error => {
            // handle error
        });

    const handleSumbit = async (e) => {
            var _headers = {
                headers: {
                    "Content-Type": "application/json",
                },
            };
            let result = await axios.get(FORGET_URL,  {
                params: {
                    email: email,
                },
            }, _headers);
        }

    return (
        <div className="container">
            <div className="forms">
                <div>
                    <span className="title">Reset password</span>

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
                        <div className="padding-t">
                            <button className="button__secondary">Send Email</button>
                        </div>
                    </form>
                    <div className="checkbox-text">
                        <p>Back to login</p>
                        <Link to="/login" className="text">
                            login
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ForgotPassword;