import React, { useState } from 'react';
import { Button, InputGroup, Form } from 'react-bootstrap';

function SocialMedias(props) {
    const socialMedias = props.socialMedias || [];
    const onUpdateSocialMedias = props.onUpdateSocialMedias;
    const [sMedias, setSMedias] = useState([...socialMedias]);

    const addSocialMedia = () => {
        setSMedias((prevSMedias) => [
            ...prevSMedias,
            { title: '', link: '' },
        ]);
    };

    const removeSocialMedia = (index) => {
        setSMedias((prevSMedias) => {
            const updatedSMedias = [...prevSMedias];
            updatedSMedias.splice(index, 1);
            onUpdateSocialMedias(updatedSMedias);
            return updatedSMedias;
        });
    };

    const handleInputChange = (index, name, value) => {
        setSMedias((prevSMedias) => {
            const updatedSMedias = [...prevSMedias];
            const updatedSMedia = {
                ...updatedSMedias[index],
                [name]: value,
            };
            updatedSMedias[index] = updatedSMedia;
            onUpdateSocialMedias(updatedSMedias);
            return updatedSMedias;
        });
    };

    return (
        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
            <Button variant="outline-dark" className='mb-2' onClick={addSocialMedia}>Add Social Media</Button>
            {sMedias.length > 0 ? (
                sMedias.map((sMedia, index) => (
                    <Form key={index}>
                        <Form.Group className="mb-3">
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Title
                                </InputGroup.Text>
                                <Form.Control
                                    value={sMedia.title}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="title"
                                    onChange={(e) => handleInputChange(index, 'title', e.target.value)}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Link
                                </InputGroup.Text>
                                <Form.Control
                                    value={sMedia.link}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="link"
                                    onChange={(e) => handleInputChange(index, 'link', e.target.value)}
                                />
                            </InputGroup>
                            <Button variant="outline-danger" onClick={() => removeSocialMedia(index)}>
                                Remove
                            </Button>
                        </Form.Group>
                    </Form>
                ))
            ) : (
                <p>No social medias found.</p>
            )}
        </div>
    );
}

export default SocialMedias;