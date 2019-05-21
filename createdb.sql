-- Ryan Schulz
-- Assignment3
-- COEN 280 Spring 2019

CREATE TABLE MOVIE
(
MOVIE_ID INTEGER PRIMARY KEY,
NAME VARCHAR(300) NOT NULL,
C_RATING NUMERIC(4,2) NOT NULL,
YEAR INTEGER
)


CREATE TABLE GENRES
(
MOVIE_ID INTEGER NOT NULL,
GENRE VARCHAR(100) NOT NULL,
PRIMARY KEY (MOVIE_ID, GENRE),
FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(MOVIE_ID) ON DELETE CASCADE
)

CREATE TABLE TAG_MAP
(
TAG_ID INTEGER PRIMARY KEY,
TAG_NAME VARCHAR(100) NOT NULL
)

CREATE TABLE TAG_MOVIE
(
MOVIE_ID INTEGER NOT NULL,
TAG_ID INTEGER NOT NULL,
WEIGHT INTEGER NOT NULL,
PRIMARY KEY(MOVIE_ID, TAG_ID),
FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(MOVIE_ID) ON DELETE CASCADE,
FOREIGN KEY (TAG_ID) REFERENCES TAGS(TAG_ID) ON DELETE CASCADE
)

CREATE TABLE ORIGIN_COUNTRY
(
MOVIE_ID INTEGER NOT NULL,
COUNTRY VARCHAR(100) NOT NULL,
PRIMARY KEY (MOVIE_ID),
FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(MOVIE_ID) ON DELETE CASCADE
)

CREATE TABLE FILM_COUNTRY
(
MOVIE_ID INTEGER NOT NULL,
COUNTRY VARCHAR(100) NOT NULL,
PRIMARY KEY (MOVIE_ID, COUNTRY),
FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(MOVIE_ID) ON DELETE CASCADE
)