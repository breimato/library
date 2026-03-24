-- ------------------------------------------------------------
-- USER STATUSES LOOKUP TABLE
-- ------------------------------------------------------------
CREATE TABLE user_statuses (
    id   INT          PRIMARY KEY,
    name VARCHAR(20)  NOT NULL UNIQUE
);

INSERT INTO user_statuses (id, name) VALUES (0, 'active'), (1, 'suspended'), (2, 'blocked');

-- ------------------------------------------------------------
-- MIGRATE users.status (enum) → users.status_id (int FK)
-- ------------------------------------------------------------
ALTER TABLE users ADD COLUMN status_id INT NOT NULL DEFAULT 0 REFERENCES user_statuses(id);

UPDATE users
SET status_id = CASE status
    WHEN 'active'    THEN 0
    WHEN 'suspended' THEN 1
    WHEN 'blocked'   THEN 2
END;

ALTER TABLE users ALTER COLUMN status_id DROP DEFAULT;
ALTER TABLE users DROP COLUMN status;
DROP TYPE user_status;
