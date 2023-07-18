import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import Modal from 'react-bootstrap/Modal';

import * as galleryAction from '../../../../redux/gallery/GalleryAction';

function DeleteGalleryModel(props) {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { galleryId } = props;
  const galleryData = useSelector(state => state.gallery);

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleSubmit = async (e) => {
    await dispatch(galleryAction.deleteGallery(galleryId));
  }

  useEffect(() => {
    if(galleryData.status === 204){
      navigate('/home');
    }
  }, [dispatch, navigate, galleryData.status])

  return (
    <>
      <Button variant="outline-danger" size="lg" onClick={handleShow}>Delete Gallery</Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Gallery</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete your gallery? This action is irreversible and will permanently delete all your gallery information, including your artworks and images.</Modal.Body>
        <Modal.Body> Please note that once your gallery is deleted, it cannot be recovered. If you're certain about this decision, click the "Delete" button below.</Modal.Body>
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

export default DeleteGalleryModel;