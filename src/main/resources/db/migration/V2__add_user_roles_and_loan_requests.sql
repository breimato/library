-- ============================================================
-- V2: User roles + loan_requests table
-- ============================================================

-- ------------------------------------------------------------
-- USER ROLE LOOKUP TABLE
-- ------------------------------------------------------------
CREATE TABLE user_role (
    id   SMALLINT    PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO user_role (id, name) VALUES
    (0, 'normal'),
    (1, 'manager'),
    (2, 'admin');

-- Add role_id column to users (default NORMAL)
ALTER TABLE users
    ADD COLUMN role_id SMALLINT NOT NULL DEFAULT 0,
    ADD CONSTRAINT fk_users_role_id
        FOREIGN KEY (role_id) REFERENCES user_role(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION;

CREATE INDEX idx_users_role_id ON users(role_id);

-- ------------------------------------------------------------
-- LOAN REQUEST STATUS LOOKUP TABLE
-- ------------------------------------------------------------
CREATE TABLE loan_request_status (
    id   SMALLINT    PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO loan_request_status (id, name) VALUES
    (0, 'pending'),
    (1, 'approved'),
    (2, 'rejected'),
    (3, 'cancelled');

-- ------------------------------------------------------------
-- LOAN REQUESTS TABLE
-- ------------------------------------------------------------
CREATE TABLE loan_requests (
    id               INT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          INT          NOT NULL,
    book_id          INT          NOT NULL,
    request_date     DATE         NOT NULL DEFAULT current_date,
    approval_date    DATE,
    status_id        SMALLINT     NOT NULL DEFAULT 0,
    rejection_reason VARCHAR(500),
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_loan_requests_user_id
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT fk_loan_requests_book_id
        FOREIGN KEY (book_id) REFERENCES books(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT fk_loan_requests_status_id
        FOREIGN KEY (status_id) REFERENCES loan_request_status(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT chk_approval_date_requires_approved_status
        CHECK (approval_date IS NULL OR status_id = 1)
);

CREATE INDEX idx_loan_requests_user_id  ON loan_requests(user_id);
CREATE INDEX idx_loan_requests_book_id  ON loan_requests(book_id);
CREATE INDEX idx_loan_requests_status_id ON loan_requests(status_id);

CREATE INDEX idx_loan_requests_pending ON loan_requests(user_id, book_id)
WHERE status_id = 0;

CREATE TRIGGER trg_loan_requests_updated_at
    BEFORE UPDATE ON loan_requests
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();
