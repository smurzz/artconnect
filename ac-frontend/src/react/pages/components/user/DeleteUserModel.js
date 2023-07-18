import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import Modal from 'react-bootstrap/Modal';

import * as userActions from '../../../../redux/user/UserAction';
import * as authActions from '../../../../redux/authentication/AuthenticationAction';

function DeleteUserModel(props) {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { userId } = props;
  const userData = useSelector(state => state.users);

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleSubmit = async (e) => {
    await dispatch(userActions.deleteUser(userId));
  }

  useEffect(() => {
    if(userData.status === 204){
      dispatch(authActions.logoutUser());
      navigate('/');
    }
  }, [dispatch, navigate, userData.status])

  return (
    <>
      <Button variant="outline-danger" size="lg" onClick={handleShow}>Delete Account</Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Account</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete your account? This action is irreversible and will permanently delete all your account information, including your profile, exhibitions, and social media data. Please note that once your account is deleted, it cannot be recovered. If you're certain about this decision, click the "Delete" button below.</Modal.Body>
        <Modal.Footer>
          <Button variant="outline-secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="danger" onClick={handleSubmit}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default DeleteUserModel;