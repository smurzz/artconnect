import React, { useState, useEffect } from 'react';
import { MenuBar } from './components/MenuBar';
import { Alert } from 'react-bootstrap';
import Footer from './components/Footer';
import '../layout/css/login.css';
import { useDispatch, useSelector } from 'react-redux';
import RegisterModel from './components/RegisterModal';

import * as fPasswordActions from '../../redux/forgot-password/ForgotPasswordAction';

import Banner from '../images/signInBanner.jpg';

export function ForgotPassword() {
    const dispatch = useDispatch();
    const fPasswordData = useSelector(state => state.fPassword);

    const [email, setEmail] = useState('');

    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (email) {
            setIsLoading(true);
            console.log(email);
            await dispatch(fPasswordActions.forgotPassword(email));
            setIsLoading(false);
        }
    }

    useEffect(() => {
        const resSuccess = fPasswordData.status === 200;
        const resError = fPasswordData.error;
        if (resSuccess) {
            setIsLoading(false);
            setEmail('');
            setErrorMessage("");
            setSuccessMessage(
                <Alert className="alarm text-center mt-3" variant='success'> We have sent a reset password link to your email. Please check. </Alert>);
        }
        if (resError) {
            setErrorMessage(resError.message ? (<Alert className="alarm text-center mt-3" variant='danger'>
                {resError.message} </Alert>) : (<Alert className="alarm text-center mt-3" variant='danger'> Error by sending email </Alert>));
        }
    }, [fPasswordData]);

    return (
        <div className='login-container'>
            <MenuBar />
            <main className="form-signin h-100" style={{ backgroundImage: `url(${Banner})` }}>
                <div className='form-container w-100 m-auto'>
                    <form>
                        <h1 className="h3 mb-3 fw-normal">Forgot your password?</h1>
                        <p className="text-right text-muted mt-2 mb-3">Please enter the email you use to log in.</p>
                        <div className="form-floating my-3">
                            <input
                                type="email"
                                name='email'
                                className="form-control"
                                id="floatingInput"
                                placeholder="name@example.com"
                                value={email}
                                onChange={async (e) => { setEmail(e.target.value); }}
                                required
                            />
                            <label for="floatingInput">Your email address</label>
                        </div>
                        <button
                            className="btn btn-dark w-100 py-2"
                            type="submit"
                            onClick={handleSubmit}
                            disabled={isLoading}
                        >
                            Send
                        </button>
                        <p className="text-right text-muted mt-2 mb-0"><a href="/login"
                            className="fw text-body"><u>Back to Login</u></a></p>
                        <p className="text-right text-muted mt-2 mb-0"><a href="/register"
                            className="fw text-body"><u>Create new account</u></a></p>
                        {errorMessage}
                        {successMessage}
                        <p className="mt-5 mb-3 text-body-secondary">&copy; 2023</p>
                    </form>
                </div>
            </main>
            <Footer />
        </div>
    );
}