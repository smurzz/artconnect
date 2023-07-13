import React, {useState, useEffect} from "react";
import { Modal, Form} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import CloseIcon from '@mui/icons-material/Close';
const ModalSuccess = ({open, handleClose, meassageHeader, message, url}) => {
    const navigate = useNavigate();

    return (
        <Modal show={open} onHide={handleClose} centered>
            <Modal.Body>
                <div className="text-center">
                    <h5 className="mb-4">{meassageHeader}</h5>
                    <p className="mb-4">{message}</p>
                    <button
                        className=" bg-indigo-600 inline-flex items-center rounded-md px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 "
                        onClick={()=>{navigate(url)}}>
                        <CloseIcon/>
                    </button>
                </div>
            </Modal.Body>
        </Modal>
    );
};
export default ModalSuccess;