# SR03 — WebSocket

Application web de chat développée avec Spring Boot, dans le cadre de l'UV SR03 (UTC, GI04).

Elle comprend :
- une interface d'administration (Thymeleaf) pour gérer les utilisateurs
- une API REST utilisee par un front React, pour les salons et les invitations
- un chat via WebSocket
---

## Fonctionnalités

- Authentification par email / mot de passe, mots de passe hachés en BCrypt.
- Espace administrateur (réservé aux admins, protégé par session) :
  - CRUD utilisateurs, activation / désactivation d'un compte
  - filtre (actif/désactivé), recherche et pagination de la liste
  - réinitialisation de mot de passe par email
  - on ne peut pas supprimer le dernier administrateur, ni se désactiver soi-même.
- Channels : création, modification, suppression, dates de validité, propriétaire, via l'API REST.
- Invitations : envoi, acceptation, refus ; rejoindre / quitter un salon 
- Chat : diffusion des messages aux participants d'un même salon, avec envoi de fichiers.

---

## Stack technique

| Domaine | Technologie |
|---------|------------|
| Langage | Java 24 |
| Framework | Spring Boot 4.0.5 (Web MVC, REST, WebSocket) |
| Vues serveur | Thymeleaf |
| Persistance | Spring Data JPA / Hibernate |
| Base de données | SQLite |
| Hachage mots de passe | BCrypt |
| Mapping DTO | ModelMapper |
| Validation | Hibernate Validator |
| Email | Jakarta Mail (SMTP) |
| Build | Maven |

---

## Architecture

Découpage en couches Controller -> Service -> Repository, avec des DTO et des Mappers entre les couches.

```
src/main/java/fr/utc/sr03/
├── Application.java      
├── model/                # entités JPA : User, Channel, Invitation, InvitationId
├── dto/                  # objets de transfert : UserDTO, LoginDTO, ChannelDTO, InvitationDTO, PageResponse
├── repository/           # DAO Spring Data : UserRepository, ChannelRepository, InvitationRepository
├── mapper/               # conversions : PageMapper, InvitationMapper
├── services/             # logique métier : UserService, AuthService, ChannelService, InvitationService, JakartaEmail
├── controller/           # MVC Thymeleaf : LoginController, AdminController
│   └── api/              # REST : AuthController, UserController, ChannelController, InvitationController
├── exception/            # UnauthorizedException, ForbiddenException, ApiExceptionHandler
└── websocket/            # WebSocketConfig, WebSocketHandler, MessageSocket, FileData

src/main/resources/
├── application.properties
├── templates/            # vues Thymeleaf (login, admin, fragments)
└── static/style/style.css
```

---

## Les deux interfaces

Le projet a deux interfaces :

1. L'interface admin, en Thymeleaf rendu côté serveur, réservée aux administrateurs. Elle est dans ce dépôt.
2. Le front React/Vite, séparé, qui tourne sur http://localhost:5173 et consomme l'API REST et le WebSocket. Un utilisateur non-admin est redirigé vers ce front après connexion.

---

## Lancement

- Connexion / interface admin : http://localhost:8080/login
- API REST : http://localhost:8080/api/v1/
- WebSocket : ws://localhost:8080/channel?channel_id=<id>&user_id=<id>

---

## API REST

Base : /api/v1

| Méthode | Route | Rôle |
|---------|-------|------|
| POST | /auth/login | Authentification |
| GET | /users?exclude={id} | Liste des utilisateurs (pour inviter) |
| GET | /channels?userId=&state=&page=&size= | Salons d'un utilisateur (state : owner / usertarget / all) |
| POST | /channels | Créer un salon |
| PUT | /channels/{id} | Modifier un salon |
| DELETE | /channels/{id} | Supprimer un salon |
| DELETE | /channels/{channelId}/members/{userId} | Quitter un salon |
| GET | /invitations?userTarget= et /invitations/sent?owner= | Invitations reçues / envoyées |
| POST | /invitations | Envoyer une invitation |
| PUT | /invitations/{u}/{t}/{c}/accept et /refuse | Accepter / refuser |
| DELETE | /invitations/{u}/{t}/{c} | Supprimer une invitation |

L'API ne garde pas de session côté serveur, c'est le front React qui gère l'état de connexion.

---

## Sécurité

- Mots de passe hachés avec BCrypt.
- Les pages /admin/** vérifient en session qu'un administrateur est connecté, sinon redirection vers /login. Ça empêche d'y accéder directement par l'URL.
- Les exceptions sont traduites en codes HTTP via @RestControllerAdvice (401, 403, 409).

---

## Contexte

Projet réalisé par Laurent CHEN et Eloise LAUNAY.

