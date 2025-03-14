'use strict';

// Récupère les éléments du DOM
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const connectingElement = document.querySelector('.connecting');

let stompClient = null; // Client STOMP pour la communication WebSocket
let username = null;    // Nom d'utilisateur actuel
const colors = {};      // Stocke les couleurs associées aux utilisateurs

/**
 * Génère une couleur unique pour un utilisateur en fonction de son nom.
 */
function getUserColor(username) {
    if (colors[username]) return colors[username];
    const hue = Array.from(username).reduce((hash, char) => char.charCodeAt(0) + ((hash << 5) - hash), 0) % 360;
    return colors[username] = `hsl(${hue}, 70%, 70%)`;
}

/**
 * Établit la connexion WebSocket lorsque l'utilisateur soumet son nom.
 */
function connect(event) {
    username = document.querySelector('#name').value.trim();
    if (username) {
        usernamePage.classList.add('hidden');    // Cache la page de connexion
        chatPage.classList.remove('hidden');    // Affiche la page de chat

        const socket = new SockJS('/ws');       // Crée une connexion WebSocket
        stompClient = Stomp.over(socket);       // Encapsule la connexion dans un client STOMP
        stompClient.connect({}, onConnected, onError); // Connecte le client STOMP
    }
    event.preventDefault();
}

/**
 * Callback appelé lorsque la connexion WebSocket est établie.
 */
function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived); // S'abonne au topic public
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({ sender: username, type: 'JOIN' })); // Envoie un message de bienvenue
    connectingElement.classList.add('hidden'); // Cache l'élément de connexion
}

/**
 * Callback appelé en cas d'erreur de connexion WebSocket.
 */
function onError() {
    connectingElement.textContent = 'Impossible de se connecter au serveur WebSocket.';
    connectingElement.style.color = 'red';
}

/**
 * Envoie un message de chat au serveur.
 */
function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({ sender: username, content: messageContent, type: 'CHAT' })); // Envoie le message
        messageInput.value = ''; // Réinitialise le champ de saisie
    }
    event.preventDefault();
}

/**
 * Callback appelé lorsqu'un message est reçu du serveur.
 */
function onMessageReceived(payload) {
    const message = JSON.parse(payload.body); // Parse le message reçu
    const messageElement = document.createElement('li'); // Crée un nouvel élément de message
    const userColor = getUserColor(message.sender); // Génère une couleur pour l'utilisateur

    if (message.type === 'JOIN' || message.type === 'LEAVE') {
        // Affiche un message d'événement (rejoindre ou quitter)
        messageElement.classList.add('event-message');
        messageElement.textContent = message.sender + (message.type === 'JOIN' ? ' a rejoint le chat !' : ' a quitté le chat !');
    } else {
        // Affiche un message de chat normal
        messageElement.classList.add('chat-message');
        messageElement.style.backgroundColor = userColor;

        const usernameElement = document.createElement('span');
        usernameElement.classList.add('username');
        usernameElement.textContent = message.sender;
        usernameElement.style.color = '#000';

        const textElement = document.createElement('p');
        textElement.textContent = message.content;

        messageElement.append(usernameElement, textElement);
    }

    messageArea.appendChild(messageElement); // Ajoute le message à la zone de chat
    messageArea.scrollTop = messageArea.scrollHeight; // Fait défiler vers le bas
}

// Écouteurs d'événements pour les formulaires
usernameForm.addEventListener('submit', connect); // Connexion au chat
messageForm.addEventListener('submit', sendMessage); // Envoi de message