import React from 'react';
import { MenuBar } from '../components/MenuBar';
import Footer from '../components/Footer';

function NotFoundPage() {
    return (
        <>
            <MenuBar />
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="main-content d-flex justify-content-center align-items-center">
                            <div class="text-center">
                                <h1 class="display-1 fw-bold">404</h1>
                                <p class="fs-3"> <span class="text-danger">Opps!</span> Page not found.</p>
                                <p class="lead">
                                    The page you’re looking for doesn’t exist.
                                </p>
                                <a href="/" class="btn btn-primary">Go Home</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </>
    );
}
export default NotFoundPage;

