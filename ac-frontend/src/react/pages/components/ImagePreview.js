import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';

function ImagePreview() {
    const [showModal, setShowModal] = useState(false);
    const [imageSrc, setImageSrc] = useState('');

    const handleClick = (src) => {
        setImageSrc(src);
        setShowModal(true);
    };

    return (
        <div>
            {/* Your image elements */}
            <img className="pop" src="image1.jpg" alt="Thumbnail" onClick={() => handleClick('image1.jpg')} />
            <img className="pop" src="image2.jpg" alt="Thumbnail" onClick={() => handleClick('image2.jpg')} />

            {/* Modal component */}
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Body>
                    <button type="button" className="close" onClick={() => setShowModal(false)}>
                        <span aria-hidden="true">&times;</span>
                        <span className="sr-only">Close</span>
                    </button>
                    <img className="imagepreview" src={imageSrc} style={{ width: '100%' }} alt="Preview" />
                </Modal.Body>
            </Modal>
        </div>
    );
}

export default ImagePreview;  