import React, {useState, useEffect} from "react";
import {Button, Modal, Form} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";

const PopupModal = ({open, handleClose, userId}) => {
    const navigate = useNavigate();

    return (
        <Modal show={open} onHide={handleClose} centered>
            <Modal.Body>
                <div className="text-center">
                    <h5 className="mb-4">Your Art Piece Information has been successfully uploaded.</h5>
                    <p className="mb-4">Would you like to upload an image?</p>
                    <button
                        className=" bg-indigo-600 inline-flex items-center rounded-md px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 "
                        onClick={() => {
                            navigate("/uploadImage/" + userId)
                        }}>
                        Proceed to upload Image
                    </button>
                    <button
                        className="inline-flex items-center rounded-md px-3 py-2 text-sm font-semibold text-black shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 "
                        onClick={() => {
                        navigate("/galerie")
                    }}>
                        maybe later
                    </button>
                </div>
            </Modal.Body>
        </Modal>
    );
};
export default PopupModal;