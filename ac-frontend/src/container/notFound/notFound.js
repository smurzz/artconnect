import { Link } from "react-router-dom";
import Header from "../../components/headerComponent/headerLogout"
import React from "react";

export default function NotFound() {
    return (
        <>
        <header>
            <Header/>
        </header>
        <div>
            <div className="mx-auto mt-32 max-w-7xl px-6 sm:mt-40 lg:px-8">
                <div className="mx-auto max-w-2xl lg:mx-0">
                    <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Oops! You seem to be lost.</h2>
                    <p className="mt-6 text-lg leading-8 text-gray-600">
                        Our whimsical 404 page invites you to explore other mesmerizing artworks that await your discovery
                    </p>
                    <Link to="/" className="mt-6 text-lg leading-8 text-gray-600">Go Back home, hopeless Wanderer</Link>
                </div>
            </div>
        </div>
        </>
    )
}