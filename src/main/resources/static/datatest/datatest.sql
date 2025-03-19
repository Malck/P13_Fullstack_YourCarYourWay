-- Création de la base de données
CREATE DATABASE CarRental;

-- Utilisation de la base de données
USE CarRental;

-- Table pour les utilisateurs
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('CLIENT', 'ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table pour les voitures
CREATE TABLE Cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    acriss_category VARCHAR(10) NOT NULL, -- Catégorie ACRISS 
    year INT NOT NULL,
    price_per_day DECIMAL(10, 2) NOT NULL,
    available BOOLEAN DEFAULT TRUE
);

-- Table pour les réservations
CREATE TABLE Reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    car_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    departure_city VARCHAR(100) NOT NULL,  -- Ville de départ
    return_city VARCHAR(100) NOT NULL,    -- Ville de retour
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (car_id) REFERENCES Cars(id)
);

-- Table pour les paiements
CREATE TABLE Payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'PAID', 'CANCELLED') NOT NULL, -- Statut du paiement
    method ENUM('CREDIT_CARD', 'PAYPAL', 'BANK_TRANSFER') NOT NULL, -- Méthode de paiement
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date du paiement
    FOREIGN KEY (reservation_id) REFERENCES Reservations(id)
);
