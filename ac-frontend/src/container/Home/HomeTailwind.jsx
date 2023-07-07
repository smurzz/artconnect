import { useState } from 'react'
import { Dialog } from '@headlessui/react'
import { Bars3Icon, XMarkIcon } from '@heroicons/react/24/outline'
import React from "react";
//allgemein Material
import Typography from '@mui/material/Typography';
import {useTheme} from '@mui/material/styles';
import {styled} from '@mui/material/styles';
import { Link, useNavigate } from "react-router-dom";
import Header from "../../components/headerComponent/headerLogout"
import "./homeTailwind.css"

//Carousel
import {Carousel} from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import Avatar from '@mui/material/Avatar';

const navigation = [
  { name: 'Product', href: '#' },
  { name: 'Features', href: '#' },
  { name: 'Resources', href: '#' },
  { name: 'Company', href: '#' },
]

const data = [
  {
    image: require("../../images/sliderOne.png"),
    caption: "Caption",
    description: "Description Here"
  },
  {
    image: require("../../images/sliderTwo.png"),
    caption: "Caption",
    description: "Description Here"
  },
  {
    image: require("../../images/sliderThree.png"),
    caption: "Caption",
    description: "Description Here"
  }

]
const stats = [
  { label: 'Create Your own Galeries. And dispay your Art.', value: 'Display ' },
  { label: 'Like an Artpiece? Ask the Artist about it in our Chat.', value: 'Chat' },
  { label: 'Love the Artpiece? it can be yours.', value: 'Aquire Art' }
]
const values = [
  {
    name: 'Be artistically inclusive',
    description:
    'We are dedicated to supporting talented artists who may be facing financial challenges, ensuring that art knows no boundaries. Our mission is to connect artists and buyers, creating a warm and welcoming community where friendships blossom, collaborations thrive, and creativity knows no limits.' },
  {
    name: 'Share artistic prosperity',
    description: 'We believe in the power of art and its ability to enrich lives. By showcasing a diverse range of artists, we aim to amplify their voices and provide them with opportunities to flourish. Together, we can build a marketplace that values fair compensation, enabling artists to thrive while connecting them with art enthusiasts who appreciate their unique visions.'
  },
  {
    name: 'Embrace artistic camaraderie',
    description:
      'Within our virtual walls, we foster a spirit of camaraderie among artists and art enthusiasts. We encourage mentorship, collaboration, and mutual support, creating an environment where creativity thrives. Here, everyone is inspired, valued, and connected on their artistic journey.',
  },
  {
    name: 'Take responsibility for arts impact',
    description:'We believe in the transformative power of art, and with that power comes responsibility. We are committed to promoting ethical practices within the art world, advocating for fair treatment, sustainable materials, and responsible consumption. Together, we can preserve art\'s cultural and environmental significance for generations to come.',
  },
  {
    name: 'Enjoy the beauty of artistic downtime',
    description:'Art is not just about creation; it\'s also about finding solace and inspiration in moments of relaxation. We encourage artists and art lovers to embrace the beauty of downtime, nurturing their well-being and personal growth. In these moments, creativity flows naturally, and a balanced approach to the artistic process is achieved.',
  },
  {
    name: 'Support emerging artists',
    description:
      'We are passionate about supporting emerging artists who are striving to make their mark in the art world. Our platform provides a nurturing space where these talented individuals can showcase their work, gain exposure, and connect with potential buyers. By empowering emerging artists, we aim to foster their growth and contribute to the vibrant tapestry of the art community. Together, lets discover the next generation of artistic brilliance.',
  },
]
 const team = [
   {
     name: 'Mona',
     role: 'Web-Developer',
     imageUrl: require('../../images/artists1.png')  },
   {
     name: 'Ronny',
     role: 'Project Manager',
     imageUrl:require('../../images/image 156ronny.png')},
   {
     name: 'Sofya',
     role: 'Software Developer',
     imageUrl: require('../../images/Sofya 1.png')},
   {
     name: 'Aaron',
     role: 'Web-Developer',
     imageUrl: require('../../images/artists2.png')
   },
   {
     name: 'Özkan',
     role: 'Software Developer',
    //  imageUrl: require('../../images/image 187ozi.png')
   },

  {
     name: 'Komola',
     role: 'Software Developer',
 imageUrl: require('../../images/artists3.png')
 },

 ]

const blogPosts = [
  {
    id: 1,
    title: 'Vel expedita assumenda placeat aut nisi optio voluptates quas',
    href: '#',
    description:
      'Illo sint voluptas. Error voluptates culpa eligendi. Hic vel totam vitae illo. Non aliquid explicabo necessitatibus unde. Sed exercitationem placeat consectetur nulla deserunt vel. Iusto corrupti dicta.',
    imageUrl:
      'https://images.unsplash.com/photo-1496128858413-b36217c2ce36?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3603&q=80',
    date: 'Mar 16, 2020',
    datetime: '2020-03-16',
    author: {
      name: 'Michael Foster',
      imageUrl:
        'https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    },
  },
  // More posts...
]
const footerNavigation = {
  main: [
    { name: 'Blog', href: '#' },
    { name: 'Jobs', href: '#' },
    { name: 'Press', href: '#' },
    { name: 'Accessibility', href: '#' },
    { name: 'Partners', href: '#' },
  ],
  social: [
    {
      name: 'Facebook',
      href: '#',
      icon: (props) => (
        <svg fill="currentColor" viewBox="0 0 24 24" {...props}>
          <path
            fillRule="evenodd"
            d="M22 12c0-5.523-4.477-10-10-10S2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.988C18.343 21.128 22 16.991 22 12z"
            clipRule="evenodd"
          />
        </svg>
      ),
    },
    {
      name: 'Instagram',
      href: '#',
      icon: (props) => (
        <svg fill="currentColor" viewBox="0 0 24 24" {...props}>
          <path
            fillRule="evenodd"
            d="M12.315 2c2.43 0 2.784.013 3.808.06 1.064.049 1.791.218 2.427.465a4.902 4.902 0 011.772 1.153 4.902 4.902 0 011.153 1.772c.247.636.416 1.363.465 2.427.048 1.067.06 1.407.06 4.123v.08c0 2.643-.012 2.987-.06 4.043-.049 1.064-.218 1.791-.465 2.427a4.902 4.902 0 01-1.153 1.772 4.902 4.902 0 01-1.772 1.153c-.636.247-1.363.416-2.427.465-1.067.048-1.407.06-4.123.06h-.08c-2.643 0-2.987-.012-4.043-.06-1.064-.049-1.791-.218-2.427-.465a4.902 4.902 0 01-1.772-1.153 4.902 4.902 0 01-1.153-1.772c-.247-.636-.416-1.363-.465-2.427-.047-1.024-.06-1.379-.06-3.808v-.63c0-2.43.013-2.784.06-3.808.049-1.064.218-1.791.465-2.427a4.902 4.902 0 011.153-1.772A4.902 4.902 0 015.45 2.525c.636-.247 1.363-.416 2.427-.465C8.901 2.013 9.256 2 11.685 2h.63zm-.081 1.802h-.468c-2.456 0-2.784.011-3.807.058-.975.045-1.504.207-1.857.344-.467.182-.8.398-1.15.748-.35.35-.566.683-.748 1.15-.137.353-.3.882-.344 1.857-.047 1.023-.058 1.351-.058 3.807v.468c0 2.456.011 2.784.058 3.807.045.975.207 1.504.344 1.857.182.466.399.8.748 1.15.35.35.683.566 1.15.748.353.137.882.3 1.857.344 1.054.048 1.37.058 4.041.058h.08c2.597 0 2.917-.01 3.96-.058.976-.045 1.505-.207 1.858-.344.466-.182.8-.398 1.15-.748.35-.35.566-.683.748-1.15.137-.353.3-.882.344-1.857.048-1.055.058-1.37.058-4.041v-.08c0-2.597-.01-2.917-.058-3.96-.045-.976-.207-1.505-.344-1.858a3.097 3.097 0 00-.748-1.15 3.098 3.098 0 00-1.15-.748c-.353-.137-.882-.3-1.857-.344-1.023-.047-1.351-.058-3.807-.058zM12 6.865a5.135 5.135 0 110 10.27 5.135 5.135 0 010-10.27zm0 1.802a3.333 3.333 0 100 6.666 3.333 3.333 0 000-6.666zm5.338-3.205a1.2 1.2 0 110 2.4 1.2 1.2 0 010-2.4z"
            clipRule="evenodd"
          />
        </svg>
      ),
    },
    {
      name: 'Twitter',
      href: '#',
      icon: (props) => (
        <svg fill="currentColor" viewBox="0 0 24 24" {...props}>
          <path d="M8.29 20.251c7.547 0 11.675-6.253 11.675-11.675 0-.178 0-.355-.012-.53A8.348 8.348 0 0022 5.92a8.19 8.19 0 01-2.357.646 4.118 4.118 0 001.804-2.27 8.224 8.224 0 01-2.605.996 4.107 4.107 0 00-6.993 3.743 11.65 11.65 0 01-8.457-4.287 4.106 4.106 0 001.27 5.477A4.072 4.072 0 012.8 9.713v.052a4.105 4.105 0 003.292 4.022 4.095 4.095 0 01-1.853.07 4.108 4.108 0 003.834 2.85A8.233 8.233 0 012 18.407a11.616 11.616 0 006.29 1.84" />
        </svg>
      ),
    },
    {
      name: 'GitHub',
      href: '#',
      icon: (props) => (
        <svg fill="currentColor" viewBox="0 0 24 24" {...props}>
          <path
            fillRule="evenodd"
            d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"
            clipRule="evenodd"
          />
        </svg>
      ),
    },
    {
      name: 'YouTube',
      href: '#',
      icon: (props) => (
        <svg fill="currentColor" viewBox="0 0 24 24" {...props}>
          <path
            fillRule="evenodd"
            d="M19.812 5.418c.861.23 1.538.907 1.768 1.768C21.998 8.746 22 12 22 12s0 3.255-.418 4.814a2.504 2.504 0 0 1-1.768 1.768c-1.56.419-7.814.419-7.814.419s-6.255 0-7.814-.419a2.505 2.505 0 0 1-1.768-1.768C2 15.255 2 12 2 12s0-3.255.417-4.814a2.507 2.507 0 0 1 1.768-1.768C5.744 5 11.998 5 11.998 5s6.255 0 7.814.418ZM15.194 12 10 15V9l5.194 3Z"
            clipRule="evenodd"
          />
        </svg>
      ),
    },
  ],
}

export default function HomeTailwind() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false)

  return (
      <>
      <Header></Header>
      <main>
        <div className="bg-white">
            <Carousel className="carousel-inner"
                      showThumbs={false}
                      showStatus={false}
                      infiniteLoop={true}
                      autoPlay={true}
                      interval={5000}
            >
              <div className="carouselItem">
                <div className="container">
                  <div
                      className="slider__text d-flex flex-column justify-content-start align-items-start container__color m-4">
                    <h1 className="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl color ">CREATE ART</h1>
                    <p className="text__body textLeft">
                      "They sit for hours in cafés, talking incessantly about culture, art, revolution,
                      and so on and so forth, poisoning the air with theories upon theories that never
                      come true."
                      Are you like Frida Kahlo and prefer to create art rather than rationalize it with
                      words?
                      Then upload your works for free on our platform. </p>
                  </div>
                </div>
                <img className="d-block w-100 h-100 overflow-hidden" src={data[0].image} alt="First slide"/>
              </div>

              <div className="carouselItem">
                <div className="container">
                  <div
                      className="slider__text d-flex flex-column justify-content-start align-items-start  container__color m-4">
                    <h1 className="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl color ">SUPPORT ART</h1>
                    <p className="text__body textLeft">
                      "Art does not reproduce the visible, but rather makes visible." This was the motto
                      of Paul Klee, which inspired his theory of form and color. Art makes things visible,
                      and artists need to be made visible. You can support us by giving their artworks a
                      new home.
                    </p>
                  </div>
                </div>
                <img className="d-block w-100 h-100 overflow-hidden" src={data[1].image} alt="First slide"/>
              </div>

              <div className="carouselItem">
                <div className="container">
                  <div
                      className="m-4 slider__text d-flex flex-column justify-content-start align-items-start container__color">
                    <h1 className="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl color">EXPERIENCE ART</h1>
                    <p className="text__body textLeft">
                      "The art is not the bread, but rather the wine of life." In line with Jean Paul's
                      thought, more than 120
                      artists are already showcasing their works on our website. Let yourself be inspired
                      and take a glimpse into
                      the world of art and its artists.
                    </p>
                  </div>
                </div>
                <img className="d-block w-100 h-100 overflow-hidden" src={data[2].image} alt="First slide"/>
              </div>
            </Carousel>

          {/* Header */}
          {/* <header className="absolute inset-x-0 top-0 z-50">
        <nav className="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
          <div className="flex lg:flex-1">
            <a href="#" className="-m-1.5 p-1.5">
              <span className="sr-only">Your Company</span>
              <img
                className="h-8 w-auto"
                src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                alt=""
              />
            </a>
          </div>
          <div className="flex lg:hidden">
            <button
              type="button"
              className="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700"
              onClick={() => setMobileMenuOpen(true)}
            >
              <span className="sr-only">Open main menu</span>
              <Bars3Icon className="h-6 w-6" aria-hidden="true" />
            </button>
          </div>
          <div className="hidden lg:flex lg:gap-x-12">
            {navigation.map((item) => (
              <a key={item.name} href={item.href} className="text-sm font-semibold leading-6 text-gray-900">
                {item.name}
              </a>
            ))}
          </div>
          <div className="hidden lg:flex lg:flex-1 lg:justify-end">
            <a href="#" className="text-sm font-semibold leading-6 text-gray-900">
              Log in <span aria-hidden="true">&rarr;</span>
            </a>
          </div>
        </nav>
        <Dialog as="div" className="lg:hidden" open={mobileMenuOpen} onClose={setMobileMenuOpen}>
          <div className="fixed inset-0 z-50" />
          <Dialog.Panel className="fixed inset-y-0 right-0 z-50 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
            <div className="flex items-center justify-between">
              <a href="#" className="-m-1.5 p-1.5">
                <span className="sr-only">Your Company</span>
                <img
                  className="h-8 w-auto"
                  src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                  alt=""
                />
              </a>
              <button
                type="button"
                className="-m-2.5 rounded-md p-2.5 text-gray-700"
                onClick={() => setMobileMenuOpen(false)}
              >
                <span className="sr-only">Close menu</span>
                <XMarkIcon className="h-6 w-6" aria-hidden="true" />
              </button>
            </div>
            <div className="mt-6 flow-root">
              <div className="-my-6 divide-y divide-gray-500/10">
                <div className="space-y-2 py-6">
                  {navigation.map((item) => (
                    <a
                      key={item.name}
                      href={item.href}
                      className="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                    >
                      {item.name}
                    </a>
                  ))}
                </div>
                <div className="py-6">
                  <a
                    href="#"
                    className="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                  >
                    Log in
                  </a>
                </div>
              </div>
            </div>
          </Dialog.Panel>
        </Dialog>
      </header> */}

          <main className="isolate">
            {/* Hero section */}
            <div className="relative isolate -z-10">
              <svg
                  className="absolute inset-x-0 top-0 -z-10 h-[64rem] w-full stroke-gray-200 [mask-image:radial-gradient(32rem_32rem_at_center,white,transparent)]"
                  aria-hidden="true"
              >
                <defs>
                  <pattern
                      id="1f932ae7-37de-4c0a-a8b0-a6e3b4d44b84"
                      width={200}
                      height={200}
                      x="50%"
                      y={-1}
                      patternUnits="userSpaceOnUse"
                  >
                    <path d="M.5 200V.5H200" fill="none" />
                  </pattern>
                </defs>
                <svg x="50%" y={-1} className="overflow-visible fill-gray-50">
                  <path
                      d="M-200 0h201v201h-201Z M600 0h201v201h-201Z M-400 600h201v201h-201Z M200 800h201v201h-201Z"
                      strokeWidth={0}
                  />
                </svg>
                <rect width="100%" height="100%" strokeWidth={0} fill="url(#1f932ae7-37de-4c0a-a8b0-a6e3b4d44b84)" />
              </svg>
              <div
                  className="absolute left-1/2 right-0 top-0 -z-10 -ml-24 transform-gpu overflow-hidden blur-3xl lg:ml-24 xl:ml-48"
                  aria-hidden="true"
              >
                <div
                    className="aspect-[801/1036] w-[50.0625rem] bg-gradient-to-tr from-[#ff80b5] to-[#9089fc] opacity-30"
                    style={{
                      clipPath:
                          'polygon(63.1% 29.5%, 100% 17.1%, 76.6% 3%, 48.4% 0%, 44.6% 4.7%, 54.5% 25.3%, 59.8% 49%, 55.2% 57.8%, 44.4% 57.2%, 27.8% 47.9%, 35.1% 81.5%, 0% 97.7%, 39.2% 100%, 35.2% 81.4%, 97.2% 52.8%, 63.1% 29.5%)',
                    }}
                />
              </div>
              <div className="overflow-hidden">
                <div className="mx-auto max-w-7xl px-6 pb-32 pt-36 sm:pt-60 lg:px-8 lg:pt-32">
                  <div className="mx-auto max-w-2xl gap-x-14 lg:mx-0 lg:flex lg:max-w-none lg:items-center">
                    <div className="w-full max-w-xl lg:shrink-0 xl:max-w-2xl">
                      <h1 className="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">
                        We’re Changing the Way Artists Connect.
                      </h1>
                      <p className="relative mt-6 text-lg leading-8 text-gray-600 sm:max-w-md lg:max-w-none">

                        Welcome to our vibrant art gallery website, a virtual haven where artistic expressions come to life. We invite you to immerse yourself in a captivating journey through the imaginative minds and skilled hands of talented artists from around the world.
                      </p>
                    </div>
                    <div className="mt-14 flex justify-end gap-8 sm:-mt-44 sm:justify-start sm:pl-20 lg:mt-0 lg:pl-0">
                      <div className="ml-auto w-44 flex-none space-y-8 pt-32 sm:ml-0 sm:pt-80 lg:order-last lg:pt-36 xl:order-none xl:pt-80">
                        <div className="relative">
                          <img
                              src="https://images.unsplash.com/photo-1557804506-669a67965ba0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&h=528&q=80"
                              alt=""
                              className="aspect-[2/3] w-full rounded-xl bg-gray-900/5 object-cover shadow-lg"
                          />
                          <div className="pointer-events-none absolute inset-0 rounded-xl ring-1 ring-inset ring-gray-900/10" />
                        </div>
                      </div>
                      <div className="mr-auto w-44 flex-none space-y-8 sm:mr-0 sm:pt-52 lg:pt-36">
                        <div className="relative">
                          <img
                              src="https://images.unsplash.com/photo-1485217988980-11786ced9454?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&h=528&q=80"
                              alt=""
                              className="aspect-[2/3] w-full rounded-xl bg-gray-900/5 object-cover shadow-lg"
                          />
                          <div className="pointer-events-none absolute inset-0 rounded-xl ring-1 ring-inset ring-gray-900/10" />
                        </div>
                        <div className="relative">
                          <img
                              src="https://images.unsplash.com/photo-1559136555-9303baea8ebd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&crop=focalpoint&fp-x=.4&w=396&h=528&q=80"
                              alt=""
                              className="aspect-[2/3] w-full rounded-xl bg-gray-900/5 object-cover shadow-lg"
                          />
                          <div className="pointer-events-none absolute inset-0 rounded-xl ring-1 ring-inset ring-gray-900/10" />
                        </div>
                      </div>
                      <div className="w-44 flex-none space-y-8 pt-32 sm:pt-0">
                        <div className="relative">
                          <img
                              src="https://images.unsplash.com/photo-1670272504528-790c24957dda?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&crop=left&w=400&h=528&q=80"
                              alt=""
                              className="aspect-[2/3] w-full rounded-xl bg-gray-900/5 object-cover shadow-lg"
                          />
                          <div className="pointer-events-none absolute inset-0 rounded-xl ring-1 ring-inset ring-gray-900/10" />
                        </div>
                        <div className="relative">
                          <img
                              src="https://images.unsplash.com/photo-1670272505284-8faba1c31f7d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&h=528&q=80"
                              alt=""
                              className="aspect-[2/3] w-full rounded-xl bg-gray-900/5 object-cover shadow-lg"
                          />
                          <div className="pointer-events-none absolute inset-0 rounded-xl ring-1 ring-inset ring-gray-900/10" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* Content section */}
            <div className="mx-auto -mt-12 max-w-7xl px-6 sm:mt-0 lg:px-8 xl:-mt-8">
              <div className="mx-auto max-w-2xl lg:mx-0 lg:max-w-none">
                <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Our mission</h2>
                <div className="mt-6 flex flex-col gap-x-8 gap-y-20 lg:flex-row">
                  <div className="lg:w-full lg:max-w-2xl lg:flex-auto">
                    <p className="text-xl leading-8 text-gray-600">
                      At our art gallery website, our mission is to foster a global community that celebrates the boundless diversity of artistic expression. We are dedicated to providing a platform that empowers artists to showcase their work to a worldwide audience, amplifying their voices and connecting them with art enthusiasts and collectors. Our aim is to create a thriving ecosystem where artists find recognition, appreciation, and opportunities for growth.
                    </p>
                    <div className="mt-10 max-w-xl text-base leading-7 text-gray-700">
                      <p>
                        As part of our commitment to supporting artists, we strive to make the process of buying art accessible, transparent, and secure. Through our carefully curated collection, art enthusiasts can explore a vast array of styles, mediums, and themes, ensuring there is something for every taste. We believe that art has the power to enrich lives, spark conversations, and inspire change, and we actively promote the idea that everyone should have the opportunity to experience and acquire remarkable artworks.
                      </p>
                    </div>
                  </div>
                  <div className="lg:flex lg:flex-auto lg:justify-center">
                    <dl className="w-64 space-y-8 xl:w-80">
                      {stats.map((stat) => (
                          <div key={stat.label} className="flex flex-col-reverse gap-y-4">
                            <dt className="text-base leading-7 text-gray-600">{stat.label}</dt>
                            <dd className="text-5xl font-semibold tracking-tight text-gray-900">{stat.value}</dd>
                          </div>
                      ))}
                    </dl>
                  </div>
                </div>
              </div>
            </div>

            {/* Values section */}
            <div className="mx-auto mt-32 max-w-7xl px-6 sm:mt-40 lg:px-8">
              <div className="mx-auto max-w-2xl lg:mx-0">
                <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Our values</h2>
                {/*<p className="mt-6 text-lg leading-8 text-gray-600">
                  Lorem ipsum dolor sit amet consect adipisicing elit. Possimus magnam voluptatum cupiditate veritatis
                  in
                  accusamus quisquam.
                </p>*/}
              </div>
              <dl className="mx-auto mt-16 grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 text-base leading-7 sm:grid-cols-2 lg:mx-0 lg:max-w-none lg:grid-cols-3">
                {values.map((value) => (
                    <div key={value.name}>
                      <dt className="font-semibold text-gray-900">{value.name}</dt>
                      <dd className="mt-1 text-gray-600">{value.description}</dd>
                    </div>
                ))}
              </dl>
            </div>

            {/* Logo cloud */}
            <div className="relative isolate -z-10 mt-32 sm:mt-48">
              <div className="absolute inset-x-0 top-1/2 -z-10 flex -translate-y-1/2 justify-center overflow-hidden [mask-image:radial-gradient(50%_45%_at_50%_55%,white,transparent)]">
                <svg className="h-[40rem] w-[80rem] flex-none stroke-gray-200" aria-hidden="true">
                  <defs>
                    <pattern
                        id="e9033f3e-f665-41a6-84ef-756f6778e6fe"
                        width={200}
                        height={200}
                        x="50%"
                        y="50%"
                        patternUnits="userSpaceOnUse"
                        patternTransform="translate(-100 0)"
                    >
                      <path d="M.5 200V.5H200" fill="none" />
                    </pattern>
                  </defs>
                  <svg x="50%" y="50%" className="overflow-visible fill-gray-50">
                    <path d="M-300 0h201v201h-201Z M300 200h201v201h-201Z" strokeWidth={0} />
                  </svg>
                  <rect width="100%" height="100%" strokeWidth={0} fill="url(#e9033f3e-f665-41a6-84ef-756f6778e6fe)" />
                </svg>
              </div>
            </div>

            {/* Team section */}
            <div className="mx-auto mt-32 max-w-7xl px-6 sm:mt-48 lg:px-8">
              <div className="mx-auto max-w-2xl lg:mx-0">
                <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Our team</h2>
              </div>
               <ul
                  role="list"
                  className="mx-auto mt-20 grid max-w-2xl grid-cols-2 gap-x-8 gap-y-16 text-center sm:grid-cols-3 md:grid-cols-4 lg:mx-0 lg:max-w-none lg:grid-cols-5 xl:grid-cols-6"
              >
                {team.map((person) => (
                    <li key={person.name}>
                      <img className="mx-auto h-26 w-24 rounded" src={person.imageUrl} alt="" />
                      <h3 className="mt-6 text-base font-semibold leading-7 tracking-tight text-gray-900">{person.name}</h3>
                      <p className="text-sm leading-6 text-gray-600">{person.role}</p>
                    </li>
                ))}
              </ul>
            </div>

            {/* Blog section */}
          </main>

        </div>
      </main>
      </>
  )
}
