import React from 'react';
import { MDBSpinner } from 'mdb-react-ui-kit';
import MenuBar from '../components/MenuBar';
import Footer from '../components/Footer';
import '../../layout/css/loading-page.css';

function LoadingPage() {
    return (
        <div className='h-75'>
            <MenuBar />
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="main-content d-flex justify-content-center align-items-center">
                            <MDBSpinner role='status'>
                                <span className='visually-hidden'>Loading...</span>
                            </MDBSpinner>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    );
}
export default LoadingPage;