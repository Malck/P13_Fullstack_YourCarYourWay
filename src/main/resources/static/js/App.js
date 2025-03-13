'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;
const colors = {};

function getUserColor(username) {
    if (colors[username]) return colors[username];
    const hue = Array.from(username).reduce((hash, char) => char.charCodeAt(0) + ((hash << 5) - hash), 0) % 360;
    return colors[username] = `hsl(${hue}, 70%, 70%)`;
}

function connect(event) {
    username = document.querySelector('#name').value.trim();
    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.send("/app/chat.register", {}, JSON.stringify({ sender: username, type: 'JOIN' }));
    connectingElement.classList.add('hidden');
}

function onError() {
    connectingElement.textContent = 'Impossible de se connecter au serveur WebSocket.';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({ sender: username, content: messageContent, type: 'CHAT' }));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    const messageElement = document.createElement('li');
    const userColor = getUserColor(message.sender);

    if (message.type === 'JOIN' || message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        messageElement.textContent = message.sender + (message.type === 'JOIN' ? ' a rejoint le chat !' : ' a quitté le chat !');
    } else {
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

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

usernameForm.addEventListener('submit', connect);
messageForm.addEventListener('submit', sendMessage);