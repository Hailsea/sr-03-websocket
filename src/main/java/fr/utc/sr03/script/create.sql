DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS  channels CASCADE ;
DROP TABLE  IF EXISTS memberships CASCADE ;
DROP TABLE IF EXISTS invitations CASCADE ;

CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       firstName VARCHAR(50),
                       lastName VARCHAR(50),
                       email VARCHAR(100),
                       password VARCHAR(100),
                       admin BOOLEAN
);

CREATE TABLE channels (
                          channel_id SERIAL PRIMARY KEY,
                          activated BOOLEAN,
                          title VARCHAR(100),
                          description VARCHAR(100),
                          date DATE,
                          duration DATE,
                          creator_id INTEGER,
                          CONSTRAINT fk_creator FOREIGN KEY(creator_id) REFERENCES users(user_id)
);

CREATE TABLE memberships(
                           user_id SERIAL,
                           channel_id SERIAL,
                           PRIMARY KEY (user_id, channel_id),
                           CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(user_id),
                           CONSTRAINT fk_channel FOREIGN KEY(channel_id) REFERENCES channels(channel_id)

);

CREATE TABLE invitations (
                            invitation_id SERIAL PRIMARY KEY,
                            inviter_id INTEGER NOT NULL,
                            invited_id INTEGER NOT NULL,
                            channel_id INTEGER NOT NULL,
                            FOREIGN KEY (inviter_id) REFERENCES users(user_id),
                            FOREIGN KEY (invited_id) REFERENCES users(user_id),
                            FOREIGN KEY (channel_id) REFERENCES channels(channel_id)
);
