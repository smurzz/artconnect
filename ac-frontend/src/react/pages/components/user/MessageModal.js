import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Alert from 'react-bootstrap/Alert';
import Modal from 'react-bootstrap/Modal';

import * as messageActions from '../../../../redux/message/MessageAction';

function MessageModal(props) {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);

    const handleClose = (() => {
        setShow(false)
    });

    const handleShow = (() => {
        setShow(true);
    });

    const messageData = useSelector(state => state.message);

    const { userData } = props;
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        if (messageData.status === 201) {
            setMessage('');
            setIsLoading(false);
            setSuccessMessage(<Alert className="alarm text-center mt-3" variant='success'> Your message has been successfully sent to the user! </Alert>);
            setErrorMessage('');
        }
        if (messageData.error) {
            setMessage('');
            setIsLoading(false);
            setErrorMessage(messageData.error.message ? (<Alert className="alarm text-center mt-3" variant='danger'>
                {messageData.error.message} </Alert>) : (<Alert className="alarm text-center mt-3" variant='danger'> Error by sending a message </Alert>));
            setSuccessMessage('');
        }
    }, [messageData.status, messageData.error]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (message) {
            setIsLoading(true);
            await dispatch(messageActions.sentMessageByUserId(userData.id, message));
        }
    }

    return (
        <>
            <button
                type="button"
                class="btn btn-outline-dark w-100"
                data-mdb-ripple-color="dark"
                style={{ zIndex: '1' }}
                disabled={isLoading}
                placeholder='Please enter your message here'
                onClick={handleShow}
            >
                Message
            </button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Request for contact</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <p>
                            The contact request will be sent to the chosen user through the messaging system. If the user accepts your contact request, you will receive a response at your private email address.
                        </p>
                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlTextarea1"
                        >
                            <Form.Label>Your message to <span class="fw-bold">{userData.firstname} {userData.lastname}</span></Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name='message'
                                value={message}
                                onChange={async (e) => { setMessage(e.target.value); }}
                            />
                        </Form.Group>
                    </Form>
                    {messageData.pending && (<div class="spinner-border" role="status"></div>)}
                    {successMessage}
                    {errorMessage}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleSubmit}>
                        Send
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default MessageModal;