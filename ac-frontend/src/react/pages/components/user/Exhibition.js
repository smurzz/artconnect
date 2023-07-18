import React, { useState } from 'react';
import { Button, InputGroup, Form } from 'react-bootstrap';

function Exhibition(props) {
    const userExhib = props.userExhib || [];
    const onUpdateExhibitions = props.onUpdateExhibitions;
    const [exhibitions, setExhibitions] = useState([...userExhib]);

    const addExhibition = () => {
        setExhibitions((prevExhibitions) => [
            ...prevExhibitions,
            { title: '', location: '', year: '', description: '' },
        ]);
    };

    const removeExhibition = (index) => {
        setExhibitions((prevExhibitions) => {
            const updatedExhibitions = [...prevExhibitions];
            updatedExhibitions.splice(index, 1);
            onUpdateExhibitions(updatedExhibitions);
            return updatedExhibitions;
        });
    };

    const handleInputChange = (index, name, value) => {
        setExhibitions((prevExhibitions) => {
            const updatedExhibitions = [...prevExhibitions];
            let updatedExhibition;

            if (name === 'year') {
                const currentYear = new Date().getFullYear();
                if (value <= currentYear) {
                    updatedExhibition = {
                        ...updatedExhibitions[index],
                        [name]: value,
                    };
                } else {
                    return prevExhibitions; 
                }
            } else {
                updatedExhibition = {
                    ...updatedExhibitions[index],
                    [name]: value,
                };
            }
            updatedExhibitions[index] = updatedExhibition;
            onUpdateExhibitions(updatedExhibitions);
            return updatedExhibitions;
        });
    };

    return (
        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
            <Button variant="outline-dark" className='mb-2' onClick={addExhibition}>Add Exhibition</Button>
            {exhibitions.length > 0 ? (
                exhibitions.map((exhibition, index) => (
                    <Form key={index}>
                        <Form.Group className="mb-3">
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Title
                                </InputGroup.Text>
                                <Form.Control
                                    value={exhibition.title}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="title"
                                    onChange={(e) => handleInputChange(index, 'title', e.target.value)}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Location
                                </InputGroup.Text>
                                <Form.Control
                                    value={exhibition.location}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="location"
                                    onChange={(e) => handleInputChange(index, 'location', e.target.value)}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Year
                                </InputGroup.Text>
                                <Form.Control
                                    type="number"
                                    value={exhibition.year}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="year"
                                    onChange={(e) => handleInputChange(index, 'year', e.target.value)}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Description
                                </InputGroup.Text>
                                <Form.Control
                                    as="textarea"
                                    value={exhibition.description}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="description"
                                    onChange={(e) => handleInputChange(index, 'description', e.target.value)}
                                />
                            </InputGroup>
                            <Button variant="outline-danger" onClick={() => removeExhibition(index)}>
                                Remove
                            </Button>
                        </Form.Group>
                    </Form>
                ))
            ) : (
                <p>No exhibitions found.</p>
            )}
        </div>
    );
}

export default Exhibition;
