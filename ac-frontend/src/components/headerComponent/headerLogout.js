import * as React from 'react';
import {useState} from 'react'
import {useNavigate, Link} from "react-router-dom";
import {logikService} from "../../lib/service";
import "./header.css"
import Button from '@mui/material/Button';

import {Fragment} from 'react'
import {Disclosure, Menu, Transition} from '@headlessui/react'
import {MagnifyingGlassIcon} from '@heroicons/react/20/solid'
import {Bars3Icon, BellIcon, XMarkIcon} from '@heroicons/react/24/outline'

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const navigation = [
    {name: 'Product', href: '#'},
    {name: 'Features', href: '#'},
    {name: 'Marketplace', href: '#'},
    {name: 'Company', href: '#'},
]
const settings = [
    {name: 'Profile', href: '#'},
    {name: 'Logout', href: '#'},
];

export default function ResponsiveAppBar() {
    const navigate = useNavigate();
    const navigateTo=(navigation)=>{
        var registerLoginNavigation ="/"+ navigation;
        navigate(registerLoginNavigation);
    }

    const logout = async (event) => {
        const logout = await logikService.logout();
        console.log("logout");
        if (logout == "success") {
            navigate("/");
        }
    }


    return (
        <header>
            <Disclosure as="nav" className="bg-white">
                {({open}) => (
                    <>
                        <div className="mx-auto max-w-7xl px-2 sm:px-4 lg:px-8">
                            <div className="flex h-16 justify-between">
                                <div className="flex px-2 lg:px-0">

                                    <div className="flex flex-shrink-0 items-center">
                                        <img
                                            className="block h-8 w-auto lg:hidden"
                                            src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                                            alt="Your Company"
                                        />
                                        <img
                                            className="hidden h-8 w-auto lg:block"
                                            src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                                            alt="Your Company"
                                        />
                                    </div>

                                    <div className="hidden lg:ml-6 lg:flex lg:space-x-8">
                                        {/* Current: "border-indigo-500 text-gray-900", Default: "border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700" */}
                                        <Link to="/"
                                              className="link inline-flex items-center border-b-2 border-indigo-500
                                              px-1 pt-1 text-sm font-medium text-gray-900"
                                        >
                                            Dashboard
                                        </Link>
                                    </div>
                                </div>

                                <div className="flex flex-1 items-center justify-center px-2 lg:ml-6 lg:justify-end">
                                    <div className="w-full max-w-lg lg:max-w-xs">
                                        <label htmlFor="search" className="sr-only">
                                            Search
                                        </label>
                                        <div className="relative">
                                            <div
                                                className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
                                                <MagnifyingGlassIcon className="h-5 w-5 text-gray-400" aria-hidden="true"/>
                                            </div>
                                            <input
                                                id="search"
                                                name="search"
                                                className="block w-full rounded-md border-0 bg-white py-1.5 pl-10 pr-3 text-gray-900 ring-1 ring-inset ring-gray-300
                                                placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                placeholder="Search"
                                                type="search"
                                            />
                                        </div>
                                    </div>
                                </div>

                                <div className="flex px-2 lg:px-0">

                                    <div className="hidden lg:ml-6 lg:flex lg:space-x-8">
                                        {/* Current: "border-indigo-500 text-gray-900", Default: "border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700" */}
                                        <Link to="/login"
                                              className="link inline-flex items-center border-b-2 border-indigo-500
                                              px-1 pt-1 text-sm font-medium text-gray-900"
                                        >
                                            Login
                                        </Link>
                                        <Link to="/register"
                                            className="link inline-flex items-center border-b-2 border-transparent
                                             px-1 pt-1 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700"
                                        >
                                            Register
                                        </Link>
                                    </div>
                                </div>

                                <div className="flex items-center lg:hidden">
                                    {/* Mobile menu button */}
                                    <Disclosure.Button
                                        className="inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-100 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500">
                                        <span className="sr-only">Open main menu</span>
                                        {open ? (
                                            <XMarkIcon className="block h-6 w-6" aria-hidden="true"/>
                                        ) : (
                                            <Bars3Icon className="block h-6 w-6" aria-hidden="true"/>
                                        )}
                                    </Disclosure.Button>
                                </div>
                                <div className="hidden lg:ml-4 lg:flex lg:items-center">

                                    {/* Profile dropdown */}
                                    <Menu as="div" className="relative ml-4 flex-shrink-0">
                                        <Transition
                                            as={Fragment}
                                            enter="transition ease-out duration-100"
                                            enterFrom="transform opacity-0 scale-95"
                                            enterTo="transform opacity-100 scale-100"
                                            leave="transition ease-in duration-75"
                                            leaveFrom="transform opacity-100 scale-100"
                                            leaveTo="transform opacity-0 scale-95"
                                        >
                                            <Menu.Items
                                                className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                                                <Menu.Item>
                                                    {({active}) => (
                                                        <Link to="/"
                                                              className={classNames(active ? 'link bg-gray-100' : '', 'link block px-4 py-2 text-sm text-gray-700')}
                                                        >
                                                            Your Galerie
                                                        </Link>
                                                    )}
                                                </Menu.Item>
                                                <Menu.Item>
                                                    {({active}) => (
                                                        <a
                                                            href="#"
                                                            className={classNames(active ? 'link bg-gray-100 fullwidth' : '', 'link block px-4 py-2 text-sm text-gray-700 fullwidth')}
                                                        >
                                                            Settings
                                                        </a>
                                                    )}
                                                </Menu.Item>
                                                <Menu.Item>
                                                    {({active}) => (
                                                        <button

                                                            onClick ={() =>{logout()}}
                                                            href="#"
                                                            className={classNames(active ? 'link bg-gray-100 fullwidth' : '', 'link block px-4 py-2 text-sm text-gray-700 fullwidth')}
                                                        >
                                                            Sign out
                                                        </button>
                                                    )}
                                                </Menu.Item>
                                            </Menu.Items>
                                        </Transition>
                                    </Menu>
                                </div>
                            </div>
                        </div>

                        <Disclosure.Panel className="lg:hidden">
                            <div className="space-y-1 pb-3 pt-2">
                                {/* Current: "bg-indigo-50 border-indigo-500 text-indigo-700", Default: "border-transparent text-gray-600 hover:bg-gray-50 hover:border-gray-300 hover:text-gray-800" */}
                                <Disclosure.Button
                                    as="a"
                                    href="#"
                                    className="link block border-l-4 border-indigo-500 bg-indigo-50 py-2 pl-3 pr-4 text-base font-medium text-indigo-700"
                                >
                                    Dashboard
                                </Disclosure.Button>
                                <Disclosure.Button
                                    as="a"
                                    href="#"
                                    onClick ={() =>{navigate("/login")}}
                                    className="link block px-4 py-2 text-base font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-800"
                                >
                                    Login
                                </Disclosure.Button>
                                <Disclosure.Button
                                    as="a"
                                    href="#"
                                    className="link block px-4 py-2 text-base font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-800"
                                    onClick ={() =>{navigate("/register")}}
                                >
                                    Register
                                </Disclosure.Button>
                            </div>
                        </Disclosure.Panel>
                    </>
                )}
            </Disclosure>
        </header>
    )
}