DROP TABLE IF EXISTS urls;
DROP TABLE IF EXISTS checks;

CREATE TABLE urls
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255),
    create_at TIMESTAMP
)

CREATE TABLE checks
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    url_id    BIGINT,
    status    VARCHAR(255),
    create_at TIMESTAMP
)
