CREATE TABLE message (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	text VARCHAR(255)
);

CREATE INDEX idx_message ON message(text);

INSERT INTO message (text) VALUES ('message 1');
INSERT INTO message (text) VALUES ('message 2');
INSERT INTO message (text) VALUES ('message 3');