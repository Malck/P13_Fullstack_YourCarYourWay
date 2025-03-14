# P13_Fullstack_YourCarYourWay

Ce Proof of Concept (POC) démontre une application de chat en temps réel utilisant WebSockets pour une communication bidirectionnelle entre le client et le serveur. Il intègre également SockJS pour fournir une alternative aux WebSockets si ceux-ci ne sont pas disponibles, et STOMP (Simple Text Oriented Messaging Protocol) pour la gestion des messages.

## Fonctionnalités
Messagerie en temps réel : Les utilisateurs peuvent envoyer et recevoir des messages instantanément.

Notifications : Les utilisateurs sont informés lorsqu'un nouvel utilisateur rejoint ou quitte le chat.

Interface simple : Une interface utilisateur intuitive permet de saisir un nom d'utilisateur et d'envoyer des messages.

## Technologies Utilisées
- Backend : Java, Spring Boot, WebSocket, STOMP.

- Frontend : HTML, CSS, JavaScript, SockJS, STOMP.js.

- Outils : Maven pour la gestion des dépendances et la construction du projet.

## Comment Démarrer ?
Clonez le dépôt :

` git clone https://github.com/your-username/chat-application-poc.git
	    cd chat-application-poc` 

Installez et lancez le backend :

`cd backend
    mvn clean install
    mvn spring-boot:run`

Ouvrez le fichier frontend/index.html dans votre navigateur ou accédez à http://localhost:8080.

## Utilisation
Ouvrez votre navigateur et accédez à http://localhost:8080.

Saisissez votre nom d'utilisateur pour rejoindre le chat.

Envoyez et recevez des messages en temps réel.

## Contexte et Objectifs
Ce POC est une étape préliminaire pour intégrer une fonctionnalité de chat en temps réel dans l'application "Your Car Your Way". L'objectif est de permettre aux utilisateurs de communiquer avec le support en temps réel, améliorant ainsi l'expérience utilisateur et rendant l'application plus interactive.

## Structure du Code
Backend : Gère les connexions WebSocket, la diffusion des messages et les notifications d'utilisateurs.

Frontend :

index.html : Interface utilisateur pour le chat.

App.js : Logique côté client pour la gestion des connexions WebSocket, l'envoi et la réception de messages.
