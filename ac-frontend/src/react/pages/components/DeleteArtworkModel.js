import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import Modal from 'react-bootstrap/Modal';

import * as artworkActions from '../../../redux/artwork/ArtworkAction';

function DeleteArtworkModel(props) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { artworkId } = props;
    const artworkData = useSelector(state => state.artwork);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleSubmit = async (e) => {
        await dispatch(artworkActions.deleteArtwork(artworkId));
    }

    useEffect(() => {
        if(artworkData.status === 204){
            navigate('/home');
        }
    }, [dispatch, navigate, artworkData.status])

    return (
        <>
            <Button variant="outline-danger" size="lg" onClick={handleShow}>Delete Artwork</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete Artwork</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete the Artwork? This action is irreversible and will permanently delete your artwork</Modal.Body>
                <Modal.Body> Please note that once your Artwork is deleted, it cannot be recovered. If you're certain about this decision, click the "Delete" button below.</Modal.Body>
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

export default DeleteArtworkModel;