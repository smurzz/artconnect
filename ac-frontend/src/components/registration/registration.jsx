import React from 'react';

import {HiOutlineMail} from 'react-icons/hi';
import {RiLockPasswordLine} from 'react-icons/ri';
import {BsPerson} from 'react-icons/bs';
import './registration.css'; 

const registration = () => {
  return (
    <div className="container">
        <div className="forms">
            <div className="form login">
                <span className="title">Registration</span>

                <form action="#">
                    <div className="input-field">
                        <input type="text" placeholder='Enter your name' required />
                        <i>
                            <BsPerson />
                        </i>
                    </div>
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
                    <div className="input-field">
                        <input type="password" placeholder='Confirm your password' required />
                        <i>
                            <RiLockPasswordLine />
                        </i>
                    </div>

                    <div className="input-field button">
                        <input type="button" value="Register now" required/>
                    </div>

                </form>
                <div className="checkbox-text">
                    <p>Already have an account ?</p>
                    <a href="#" className='text'>Login</a>
                </div>
            </div>
        </div>
    </div>
  )
}

export default registration