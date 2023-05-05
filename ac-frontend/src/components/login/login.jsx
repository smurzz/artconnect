import React from 'react';

import {HiOutlineMail} from 'react-icons/hi';
import {RiLockPasswordLine} from 'react-icons/ri';
import './login.css'; 

const login = () => {
  return (
    <div className="container">
        <div className="forms">
            <div className="form login">
                <span className="title">Login</span>

                <form action="#">
                    <div className="input-field">
                        <input type="text" placeholder='Enter your email' required />
                        <i>
                            <HiOutlineMail />
                        </i>
                    </div>
                    <div className="input-field">
                        <input type="password" placeholder='Enter your password' required />
                        <i>
                            <RiLockPasswordLine />
                        </i>
                    </div>

                    <div className='padding-t'>
                        <a href="#" className='text padding-t'>Forgot Password?</a>
                    </div>

                    <div className="input-field button">
                        <input type="button" value="Login now" required/>
                    </div>

                </form>
                <div className="checkbox-text">
                    <p>Not a member yet ?</p>
                    <a href="#" className='text'>Sign Up</a>
                </div>
            </div>
        </div>
    </div>
  )
}

export default login