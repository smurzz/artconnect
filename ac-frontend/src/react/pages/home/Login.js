import React, { useState, useEffect } from 'react';
import { Alert } from 'react-bootstrap';
import MenuBar from '../components/MenuBar';
import Footer from '../components/Footer';
import '../../layout/css/login.css'
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector, connect } from 'react-redux';

import * as authActions from '../../../redux/authentication/AuthenticationAction';

import Banner from '../../images/signInBanner.jpg';

const mapStateToPrors = state => {
    const { auth } = state
    return auth;
}

function Login(props) {
    const navigate = useNavigate();

    const [user, setUser] = useState(
        {
            email: "",
            password: ""
        });
    const [isLoading, setIsLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        const resSuccess = props.status === 200;
        setSuccess(resSuccess);
        setIsLoading(false);
        setUser({
            email: "",
            password: ""
        });
        setErrorMessage("");
    }, [props.status, props.loginUserAction]);

    useEffect(() => {
        if(props.error){
            const errResult = props.error;
            setError(errResult);
            setErrorMessage(props.error?.message ? (<Alert className="alarm text-center mt-3" variant='danger'>
            {props.error?.message} </Alert>) : (<Alert className="alarm text-center mt-3" variant='danger'> Error by Login </Alert>));
        }
        setIsLoading(false);
    }, [props.error, error]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        props.loginUserAction(user);
        setIsLoading(false);
    }

    if(success === true) {
        navigate('/home');
    }

    return (
        <div className='login-container'>
            <MenuBar />
            <main className="form-signin h-100" style={{ backgroundImage: `url(${Banner})` }}>
                <div className='form-container w-100 m-auto'>
                    <form>
                        <h1 className="h3 mb-3 fw-normal">Please sign in</h1>

                        <div className="form-floating">
                            <input
                                type="email"
                                name='email'
                                className="form-control"
                                id="floatingInput"
                                placeholder="name@example.com"
                                onChange={async (e) => { setUser({ ...user, email: e.target.value }); }}
                                required
                            />
                            <label for="floatingInput">Email address</label>
                        </div>
                        <div className="form-floating">
                            <input
                                type="password"
                                name='password'
                                className="form-control"
                                id="floatingPassword"
                                placeholder="Password"
                                onChange={async (e) => { setUser({ ...user, password: e.target.value }) }}
                                required
                            />
                            <label for="floatingPassword">Password</label>
                        </div>

                        <div className='form-links'>
                            <div className="form-check text-start my-3">
                                <input className="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault" />
                                <label className="form-check-label" for="flexCheckDefault">
                                    Remember me
                                </label>
                            </div>
                            <a href="/forgot-password">Forgot password?</a>
                        </div>
                        <button
                            className="btn btn-dark w-100 py-2"
                            type="submit"
                            onClick={handleSubmit}
                            disabled={isLoading}
                        >
                            {isLoading ? "Singing up" : "Sign in"}
                        </button>
                        <p className="small mt-2 pt-1 mb-0">Don't have an account? <a href="/register"
                            className="fw-bold text-body"><u>Register</u></a></p>
                        {errorMessage}
                        <p className="mt-5 mb-3 text-body-secondary">&copy; 2023</p>
                    </form>
                </div>
            </main>
            <Footer />
        </div>
    );
}

const mapDispatchToProps = dispatch => {
    return {
        loginUserAction: (user) => dispatch(authActions.authenticateUser(user.email, user.password))
    }
}

const ConnectedLogin = connect(mapStateToPrors, mapDispatchToProps)(Login)
export default ConnectedLogin;

/* */