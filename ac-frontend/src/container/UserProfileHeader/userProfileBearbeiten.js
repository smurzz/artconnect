import React, {Fragment} from 'react'
import EmptyGalerie from '../../container/Galerie/Galerie2'
import {EnvelopeIcon, PhoneIcon} from '@heroicons/react/20/solid'
import {Menu, Transition} from '@headlessui/react'
import {CodeBracketIcon, EllipsisVerticalIcon, FlagIcon, StarIcon} from '@heroicons/react/20/solid'
import {PaperClipIcon} from '@heroicons/react/20/solid'
import Header from "../../components/headerComponent/headerLogedIn";
import {useNavigate, Link} from "react-router-dom";


const profile = {
    name: 'Vyacheslav Thomas',
    email: 'Vyacheslav@example.com',
    web: 'Vyacheslav.com',
    avatar:
        'https://images.unsplash.com/photo-1463453091185-61582044d556?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80',
    backgroundImage:
        'https://images.unsplash.com/photo-1444628838545-ac4016a5418a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80',
    fields: [
        ['Phone', '(555) 123-4567'],
        ['Email', 'Vyacheslav@example.com'],
        ['Title', 'Senior Front-End Developer'],
        ['Team', 'Product Development'],
        ['Location', 'San Francisco'],
        ['Sits', 'Oasis, 4th floor'],
        ['Salary', '12$'],
        ['Birthday', 'June 8, 1990'],
    ],
}

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const Profile = () => {
    const navigate = useNavigate();
    return (
        <>
            <div>
                <Header></Header>
                <div>
                    <div>
                        <img className="h-32 w-full object-cover lg:h-48" src={profile.backgroundImage} alt=""/>
                    </div>
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <div className="-mt-12 sm:-mt-16 sm:flex sm:items-end sm:space-x-5">
                            <div className="flex">
                                <img className="h-24 w-24 rounded-full ring-4 ring-white sm:h-32 sm:w-32"
                                     src={profile.avatar} alt=""/>
                            </div>
                            <div
                                className="mt-6 sm:flex sm:min-w-0 sm:flex-1 sm:items-center sm:justify-end sm:space-x-6 sm:pb-1">
                                <div className="mt-6 min-w-0 flex-1 sm:hidden md:block">
                                    <h1 className="truncate text-2xl font-bold text-gray-900">{profile.name}</h1>
                                </div>
                                <div
                                    className="mt-6 flex flex-col justify-stretch space-y-3 sm:flex-row sm:space-x-4 sm:space-y-0">
                                    <button
                                        onClick ={() =>{navigate("/galerie")}}
                                        type="button"
                                        className="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                                    >
                                        <span>Back to Galerie</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="mt-6 hidden min-w-0 flex-1 sm:block md:hidden">
                            <h1 className="truncate text-2xl font-bold text-gray-900">{profile.name}</h1>
                        </div>
                    </div>
                </div>
                <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                    <div className="mx-auto max-w-3xl">
                    </div>
                    <div className="mt-6 border-t border-gray-100">
                        <dl className="divide-y divide-gray-100">
                            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                                <dt className="text-sm font-medium leading-6 text-gray-900">Full name</dt>
                                <dd className="mt-1 flex text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                                    <span className="flex-grow">{profile.name}</span>
                                    <span className="ml-4 flex-shrink-0">
                <button type="button" className="rounded-md bg-white font-medium text-indigo-600 hover:text-indigo-500">
                  Update
                </button>
              </span>
                                </dd>
                            </div>
                            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                                <dt className="text-sm font-medium leading-6 text-gray-900">Tel.</dt>
                                <dd className="mt-1 flex text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                                    <span className="flex-grow">01725450303</span>
                                    <span className="ml-4 flex-shrink-0">
                <button type="button" className="rounded-md bg-white font-medium text-indigo-600 hover:text-indigo-500">
                  Update
                </button>
              </span>
                                </dd>
                            </div>
                            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                                <dt className="text-sm font-medium leading-6 text-gray-900">Email address</dt>
                                <dd className="mt-1 flex text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                                    <span className="flex-grow">{profile.email}</span>
                                    <span className="ml-4 flex-shrink-0">
                <button type="button" className="rounded-md bg-white font-medium text-indigo-600 hover:text-indigo-500">
                  Update
                </button>
              </span>
                                </dd>
                            </div>
                            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                                <dt className="text-sm font-medium leading-6 text-gray-900">Website</dt>
                                <dd className="mt-1 flex text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
              <span className="flex-grow">
                <a href={''}>
                {profile.web}
                </a>
                </span>
                                    <span className="ml-4 flex-shrink-0">
                <button type="button" className="rounded-md bg-white font-medium text-indigo-600 hover:text-indigo-500">
                  Update
                </button>
              </span>
                                </dd>
                            </div>
                            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                                <dt className="text-sm font-medium leading-6 text-gray-900">Biografie</dt>
                                <dd className="mt-1 flex text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
              <span className="flex-grow">
                Fugiat ipsum ipsum deserunt culpa aute sint do nostrud anim incididunt cillum culpa consequat. Excepteur
                qui ipsum aliquip consequat sint. Sit id mollit nulla mollit nostrud in ea officia proident. Irure
                nostrud pariatur mollit ad adipisicing reprehenderit deserunt qui eu.
              </span>
                                    <span className="ml-4 flex-shrink-0">
                <button type="button" className="rounded-md bg-white font-medium text-indigo-600 hover:text-indigo-500">
                  Update
                </button>
              </span>
                                </dd>
                            </div>
                        </dl>
                    </div>
                </div>
            </div>


        </>

    )
}

export default Profile