CREATE TABLE IF NOT EXISTS jedis (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    strength INTEGER NOT NULL,
    version INTEGER NOT NULL,
    PRIMARY KEY (id)
);