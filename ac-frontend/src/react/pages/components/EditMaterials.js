import React, { useState } from 'react';
import { Button, InputGroup, Form } from 'react-bootstrap';

function EditMaterials(props) {
    const artMaterials = props.artMaterials || [];
    const onUpdateMaterials = props.onUpdateMaterials;
    const [materials, setMaterials] = useState(artMaterials);

    const addMaterials = () => {
        setMaterials((prevMaterial) => [
            ...prevMaterial,
            ""
        ]);
    };

    const removeMaterials = (index) => {
        setMaterials((preMat) => {
            const updatedMat = [...preMat];
            updatedMat.splice(index, 1);
            onUpdateMaterials(updatedMat);
            return updatedMat;
        });
    };

    const handleInputChange = (index, value) => {
        setMaterials((prevMat) => {
            const updatedMats = [...prevMat];
            let updatedMat;

            updatedMat = {
                    ...updatedMats[index],
                    value,
                };
            updatedMats[index] = updatedMat;
            onUpdateMaterials(updatedMats);
            return updatedMats;
        });
    };

    return (
        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
            <Button variant="outline-dark" className='mb-2' onClick={addMaterials}>Add Materials</Button>
            {materials.length > 0 ? (
                materials?.map((material, index) => (
                    <Form key={index}>
                        <Form.Group className="mb-3">
                            <InputGroup className="mb-3">
                                <InputGroup.Text id="inputGroup-sizing-default">
                                    Materials used
                                </InputGroup.Text>
                                <Form.Control
                                    value={material}
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    name="material"
                                    onChange={(e) => handleInputChange(index, e.target.value)}
                                />
                            </InputGroup>
                            <Button variant="outline-danger" onClick={() => removeMaterials(index)}>
                                Remove
                            </Button>
                        </Form.Group>
                    </Form>
                ))
            ) : (
                <p>No materials found.{artMaterials}</p>
            )}
        </div>
    );
}

export default EditMaterials;
