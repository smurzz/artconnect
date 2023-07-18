import React from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function RegisterModel(props) {
    const { handleClose } = props;

  return (
    <>
      <Modal className='success-model' show={true} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Signing successful!</Modal.Title>
        </Modal.Header>
        <Modal.Body>Please check your email for the confirmation message we just sent you. The confirmation list is valid for 15 minutes.</Modal.Body>
        <Modal.Footer>
          <Button variant="outline-secondary" onClick={handleClose}>Close</Button>
          <a href="/login" class="btn btn-dark" role="button">Login</a>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default RegisterModel;