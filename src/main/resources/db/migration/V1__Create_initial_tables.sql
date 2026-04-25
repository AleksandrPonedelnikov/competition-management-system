-- создание таблицы пользователей
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'PARTICIPANT'
);

--создание таблицы соревнований
CREATE TABLE competitions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANNED',
    organizer_id BIGINT NOT NULL,
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

--создание таблицы команд
CREATE TABLE teams(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    competition_id BIGINT NOT NULL,
    FOREIGN KEY (competition_id) REFERENCES competitions(id)
);

--создание таблицы участников команд
CREATE TABLE team_members (
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- создание таблицы матчей
CREATE TABLE matches (
    id BIGSERIAL PRIMARY KEY,
    competition_id BIGINT NOT NULL,
    team1_id BIGINT NOT NULL,
    team2_id BIGINT NOT NULL,
    match_date TIMESTAMP NOT NULL,
    result VARCHAR(50),
    FOREIGN KEY (competition_id) REFERENCES competitions(id),
    FOREIGN KEY (team1_id) references teams(id),
    FOREIGN KEY (team2_id) references teams(id)
);