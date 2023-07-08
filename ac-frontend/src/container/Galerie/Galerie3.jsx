import React, {useEffect, useState} from 'react'
import {GalerieApiService} from "../../lib/apiGalerie"

const Galerie3 = () => {

    const [Galerie, setGalerie] = useState([]);

    useEffect(() => {
        async function getGaleryData() {
            const getGalerie= await GalerieApiService.getUnsecuredData("/galleries/");
            console.log("getgalerie: "+ getGalerie);
        }

        getGaleryData();

    }, [])

  return (
    <div>Galerie3</div>
  )
}

export default Galerie3