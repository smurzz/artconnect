import React, { useState, useEffect } from 'react';
import { MenuBar } from './components/MenuBar';
import { Alert } from 'react-bootstrap';
import Footer from './components/Footer';
import '../layout/css/login.css';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation } from 'react-router-dom';
import RegisterModel from './components/RegisterModal';

import * as fPasswordActions from '../../redux/forgot-password/ForgotPasswordAction';

import Banner from '../images/signInBanner.jpg';

export function ResetPassword() {
    const dispatch = useDispatch();
    const fPasswordData = useSelector(state => state.fPassword);
    const location = useLocation();

    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get('token');

    const [resetPassword, setResetPassword] = useState({
        password: '',
        token: token
    });

    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (resetPassword.password) {
            setIsLoading(true);
            console.log(resetPassword);
            await dispatch(fPasswordActions.resetPassword(resetPassword));
            setIsLoading(false);
        }
    }

    useEffect(() => {
        const resSuccess = fPasswordData.status === 200;
        const resError = fPasswordData.error;
        if (resSuccess) {
            setIsLoading(false);
            setResetPassword('');
            setErrorMessage("");
            setSuccessMessage(
                <Alert className="alarm text-center mt-3" variant='success'> You have successfully changed your password. </Alert>);
        }
        if (resError) {
            console.log(resError);
            setErrorMessage(resError.status && resError.status === 500 ? (<Alert className="alarm text-center mt-3" variant='danger'> Whoops, that's an expired link. Please <a href='/forgot-password'>request a link again </a>  </Alert>) :
                resError.message ? (<Alert className="alarm text-center mt-3" variant='danger'> {resError.message} </Alert>) : 
            (<Alert className="alarm text-center mt-3" variant='danger'> Error by resetting password </Alert>));
        }
    }, [fPasswordData]);

    return (
        <div className='login-container'>
            <MenuBar />
            <main className="form-signin h-100" style={{ backgroundImage: `url(${Banner})` }}>
                <div className='form-container w-100 m-auto'>
                    <form>
                        <h1 className="h3 mb-3 fw-normal">Reset password</h1>
                        <p className="text-right text-muted mt-2 mb-3">Enter a new password to reset the password on your account.</p>
                        <div className="form-floating my-3">
                            <input
                                type="password"
                                name='password'
                                className="form-control"
                                id="floatingInput"
                                placeholder="password"
                                value={resetPassword.password}
                                onChange={async (e) => { setResetPassword({ ...resetPassword, password: e.target.value }) }}
                                required
                            />
                            <label for="floatingInput">Your new password</label>
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