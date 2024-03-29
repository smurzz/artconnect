import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Alert } from 'react-bootstrap';
import MenuBar from '../components/MenuBar';
import Footer from '../components/Footer';
import RegisterModel from '../components/home/RegisterModal';

import Banner from '../../images/signInBanner.jpg';
import '../../layout/css/login.css';

import * as authActions from '../../../redux/authentication/AuthenticationAction';

export default function Register() {
    const dispatch = useDispatch();
    const authData = useSelector(state => state.auth);

    const [user, setUser] = useState(
        {
            firstname: "",
            lastname: "",
            email: "",
            password: ""
        });
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);
    const [isTermsAccepted, setIsTermsAccepted] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const userDataValidTest = userDataValid();
        if (!isTermsAccepted) {
            setErrorMessage(<Alert className="alarm text-center mt-3" variant='danger'>You must accept the terms and conditions to be able to process your registration.</Alert>);
        }
        else if(userDataValidTest) {
            setIsLoading(true);
            await dispatch(authActions.signupUser(user));
        }else{
            setIsLoading(false);
        }
    }

    const userDataValid = () => {
        const errArray=[];
        const {firstname, lastname, email, password} = user;
        const isValidFirstname = firstname.length >= 2;
        const isValidLastname = lastname.length >= 2;
        const isValidEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
        const isValidPassword = password.length >= 3;

        if (!firstname.trim() | !isValidFirstname) {
            errArray.push("First name should contain at least 2 characters")
        }
        if (!lastname.trim() |!isValidLastname ) {
            errArray.push("Last name should contain at least 2 characters")
        }
        if (!email.trim() | !isValidEmail) {
            errArray.push("Invalid email address")
        }
        if (!password.trim() | !isValidPassword) {
            console.log("Password cannot be empty");
            errArray.push("Password should contain at least 3 characters")
        }

        if(errArray.length > 0){
            setErrorMessage(<Alert className="alarm text-center mt-3" variant='danger'>{errArray.map(str => <p>{str}</p>)}</Alert>);
            return false;
        }else{
            return true;
        }
    }

    useEffect(() => {
        const resSuccess = authData.status === 200;
        setSuccess(resSuccess);
        setIsLoading(false);
        setIsTermsAccepted(false);
        setUser({
            firstname: "",
            lastname: "",
            email: "",
            password: ""
        });
        setErrorMessage("");

    }, [authData.status]);

    useEffect(() => {
        const errResult = authData.error;
        setError(errResult);
        setIsLoading(false);
        setIsTermsAccepted(false);
        setErrorMessage(authData.error?.message ? (<Alert className="alarm text-center mt-3" variant='danger'>
            {authData.error?.message} </Alert>) : (<Alert className="alarm text-center mt-3" variant='danger'>  {authData.error?.status == 400 ? "The Email you have provided, has already been registered" : "Error signUp"}</Alert>));
    }, [authData.error, error]);

    useEffect(()=>{
        setErrorMessage("");
    }, [user])

    const handleModalClose = () => {
        setSuccess(false);
    };

    return (
        <div className='login-container'>
            <MenuBar />
            <main className="form-signin h-100" style={{ backgroundImage: `url(${Banner})` }}>
                <div className='form-container w-100 m-auto'>
                    <form>
                        <h1 className="h3 mb-3 fw-normal">Please sign up</h1>
                        <div className="form-floating mb-1">
                            <input
                                type="text"
                                name='firstname'
                                className="form-control"
                                id="floatingFname"
                                placeholder="John"
                                value={user.firstname}
                                onChange={async (e) => { setUser({ ...user, firstname: e.target.value }); }}
                                required />
                            <label for="floatingFname">First name</label>
                        </div>
                        <div className="form-floating mb-1">
                            <input
                                type="text"
                                name='lastname'
                                className="form-control"
                                id="floatingLname"
                                placeholder="Snow"
                                value={user.lastname}
                                onChange={async (e) => { setUser({ ...user, lastname: e.target.value }); }}
                                required
                            />
                            <label for="floatingLname">Last name</label>
                        </div>
                        <div className="form-floating mb-1">
                            <input
                                type="email"
                                name='email'
                                className="form-control"
                                id="floatingInput"
                                placeholder="name@example.com"
                                value={user.email}
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
                                value={user.password}
                                onChange={async (e) => { setUser({ ...user, password: e.target.value }) }}
                                required
                            />
                            <label for="floatingPassword">Password</label>
                        </div>

                        <div className="form-check text-start my-3">
                            <input
                                class="form-check-input me-2"
                                type="checkbox"
                                value=""
                                id="form2Example3c"
                                checked={isTermsAccepted}
                                onChange={(e) => setIsTermsAccepted(e.target.checked)}
                            />
                            <label class="form-check-label" for="form2Example3">
                                I agree all statements in <a href="#!">Terms of service</a>
                            </label>
                        </div>
                        <button
                            className="btn btn-dark w-100 py-2"
                            type="submit"
                            onClick={handleSubmit}
                            disabled={isLoading}
                        >
                            Register
                        </button>
                        <p className="text-right text-muted mt-2 mb-0">Have already an account? <a href="/login"
                            className="fw-bold text-body"><u>Login here</u></a></p>
                        {errorMessage}
                        <p className="mt-5 mb-3 text-body-secondary">&copy; 2023</p>
                    </form>
                </div>
                {success && <RegisterModel handleClose={handleModalClose} />}
            </main>
            <Footer />
        </div>
    );
}