import {GalerieApiService} from "../../lib/apiGalerie"

import
{useEffect, useState} from 'react'
import {useParams, useNavigate} from 'react-router-dom';
import React from "react";

export default function Kommentarfunktion({id}) {
    async function postComment() {
        //richtiges Kommentar übergeben
        const result = await GalerieApiService.postSecuredData("/artworks/" + id + "/comments", {
            "commentText": "Super!"
        })

        console.log("result: " + result);
    }

    async function updateComment() {
        const result = await GalerieApiService.putSecuredData("/artworks/" + id + "/" + "commentId", {
            "commentText": "Super! (Edited)"
        })
    }

    async function deleteComment() {
        const result = await GalerieApiService.deleteSecuredData("/artworks/" + id + "/" + "commentId");
    }

    return (
        <>
            <button onClick={() => postComment()}>
                {/*hier Kommentar übergeben*/}
                postCommentar
            </button>
        </>
    )

}