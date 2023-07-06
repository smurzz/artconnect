import React, {useState, useEffect} from "react";
import {Button, Modal, Form} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";

const PopupModal = ({open, handleClose, userId}) => {
    const navigate = useNavigate();

    return (
        <Modal show={open} onHide={handleClose} centered>
            <Modal.Body>
                <div className="text-center">
                    <h5 className="mb-4">Your Art Piece Information has been successfully uploaded.</h5>
                    <p className="mb-4">Would you like to upload an image?</p>
                    <Button variant="primary" className="btn-lg" onClick={()=>{navigate("/uploadImage/"+userId)}}>
                        Proceed to Upload Image
                    </Button>
                    <Button variant="primary" className="btn-lg" onClick={()=>{navigate("/galerie")}}>
                       maybe later
                    </Button>
                </div>
            </Modal.Body>
        </Modal>
    );
};
export default PopupModal;