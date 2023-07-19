# ArtConnect
#### _von ArtConnect Team_

ArtConnect is a web-platform for artists that allows them to showcase their artwork online and receive messages with purchase inquiries or other questions from platform's users. They also receive feedback from other users. The platform includes various tools, including an intuitive user interface and a search function to find artworks by artist, genre, or medium. Additionally, there is a rating system that provides valuable feedback to the artists.

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=smurzz_artconnect)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=smurzz_artconnect&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=smurzz_artconnect)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=smurzz_artconnect&metric=coverage)](https://sonarcloud.io/summary/new_code?id=smurzz_artconnect)

### Team:
- Ronny Schmitz (_Project Management, design_);
- Mona Becher (_Frontend: web development, design_)
- Aaron Engelmann (_Frontend, design_)
- Sofya Murzakova (_Backend: software development, deployment_)
- Komola Benzinger (_Backend: software development, testing_)
- Özkan Kizilkan (_Backend: software development, testing_)

Nach dem Playtest und der Umfrage ergibt sich folgendes Fazit:

### Installation:

######  Method 1: Local Installation

To run the application locally, follow these steps:

1. Clone the repository to your local machine.
2. In a terminal, navigate to the ``cd artconnect/ac-backend`` folder and run ```mvn spring-boot:run```. This will start the server and connect it to your local MongoDB instance.
3. In another terminal, navigate to the ``cd artconnect/ac-frontend`` folder and run ``npm install`` and ``npm start``. This will start the frontend on http://localhost:3000.
4. Open your web browser and go to http://localhost:3000 to access the application.

###### Method 2: Docker Installation
Alternatively, you can run the application using Docker. Follow these steps:
1. Make sure you have Docker installed on your machine.
2. Copy the docker-compose.yaml document in your machine.
3. Run the following command to start the application: ``LATEST_TAG=v1.0.5 docker-compose up -d``.
4. Once the application is up and running, open your web browser and go to http://localhost:3000 to access the frontend.

### Usege and features:

**For Art Enthusiasts:**

1. Discover Inspiring Artwork: Explore a vast collection of captivating artworks created by talented artists. Utilize our powerful search feature to find pieces based on artists, genres, or specific keywords.
2. Connect with Artists: Engage directly with artists by leaving comments, offering feedback, and sending communication requests. Learn more about their artistic journey and gain insights into their creative process.
3. Rate and Like Art: Show appreciation for outstanding artwork by providing ratings and liking your favorite pieces. Your feedback helps artists thrive and encourages them on their artistic endeavors.

**For Artists:**

1. Create Your Portfolio: Showcase your artistic brilliance with a personalized gallery. Curate and display your artwork professionally to attract art enthusiasts and potential buyers.
2. Receive Valuable Feedback: Benefit from the rating system that enables art enthusiasts to rate your galleries. Gain valuable insights into the impact of your work and improve your craft.
3. Connect with the Community: Network and connect with fellow artists and art enthusiasts. Collaborate, share experiences, and celebrate creativity within our thriving artistic community.

**Getting Started:**

1. Register or Log In: Register for an ArtConnect account to unlock all features or log in if you already have an account.
2. Explore Artworks: Delve into a diverse and inspiring collection of artworks from artists worldwide. Discover hidden gems and find your next favorite piece.
3. Engage and Interact: Engage with artists through comments, likes, and communication requests. Connect with like-minded individuals and expand your artistic network.
4. Showcase Your Creativity: If you're an artist, create your gallery, upload your artwork, and share your unique vision with the world.

##### Links:
- [Link to the Webspace](http://18.195.200.98:3000/)
- [Download zip-Datei last version of the app)](https://github.com/smurzz/artconnect/releases/tag/v1.0.5)

