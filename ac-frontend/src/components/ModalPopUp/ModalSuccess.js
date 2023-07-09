import React, {useState, useEffect} from "react";
import {Button, Modal, Form} from "react-bootstrap";
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
                    <Button variant="primary" className="btn-lg" onClick={()=>{navigate(url)}}>
                        <CloseIcon/>
                    </Button>
                </div>
            </Modal.Body>
        </Modal>
    );
};
export default ModalSuccess;