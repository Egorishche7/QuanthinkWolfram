-- Create a user table
CREATE TABLE user
(
    u_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    u_email    VARCHAR(255) UNIQUE        NOT NULL,
    u_username VARCHAR(255)               NOT NULL,
    u_password VARCHAR(255)               NOT NULL,
    u_role     VARCHAR(10) DEFAULT 'user' NOT NULL CHECK (u_role IN ('admin', 'user'))
);

-- Creating a calculation history table
CREATE TABLE calculation
(
    c_id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    c_user_id      BIGINT,
    c_type         VARCHAR(255) NOT NULL,
    c_expression   VARCHAR(255) NOT NULL,
    c_result       TEXT         NOT NULL,
    c_date         TIMESTAMP    NOT NULL default CURRENT_TIMESTAMP,
    c_threads_used INT          NOT NULL,
    FOREIGN KEY (c_user_id) REFERENCES user (u_id)
);

-- Create a chat message table
CREATE TABLE message
(
    m_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    m_content   TEXT      NOT NULL,
    m_date      TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
    m_sender_id BIGINT    NOT NULL,
    FOREIGN KEY (m_sender_id) REFERENCES user (u_id)
);
