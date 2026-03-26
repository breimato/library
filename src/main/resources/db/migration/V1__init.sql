-- ============================================================
-- BIBLIOTECA HEXAGONAL — DDL PostgreSQL
-- ============================================================

-- ------------------------------------------------------------
-- STATUS LOOKUP TABLES
-- ------------------------------------------------------------
CREATE TABLE user_status (
    id   SMALLINT    PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO user_status (id, name) VALUES
    (0, 'active'),
    (1, 'suspended'),
    (2, 'blocked');

CREATE TABLE loan_status (
    id   SMALLINT    PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO loan_status (id, name) VALUES
    (0, 'active'),
    (1, 'returned'),
    (2, 'overdue');

CREATE TABLE reservation_status (
    id   SMALLINT    PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO reservation_status (id, name) VALUES
    (0, 'pending'),
    (1, 'notified'),
    (2, 'fulfilled'),
    (3, 'expired'),
    (4, 'cancelled');

CREATE TABLE fine_status (
    id   SMALLINT    PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO fine_status (id, name) VALUES
    (0, 'pending'),
    (1, 'paid'),
    (2, 'waived');

-- ------------------------------------------------------------
-- USERS
-- ------------------------------------------------------------
CREATE TABLE users (
    id         INT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    phone      VARCHAR(20),
    status_id  SMALLINT    NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_users_status_id
        FOREIGN KEY (status_id) REFERENCES user_status(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION
);

-- ------------------------------------------------------------
-- BOOKS
-- ------------------------------------------------------------
CREATE TABLE books (
    id               INT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    isbn             CHAR(13)     NOT NULL UNIQUE,
    title            VARCHAR(255) NOT NULL,
    author           VARCHAR(150) NOT NULL,
    genre            VARCHAR(100),
    total_copies     SMALLINT     NOT NULL CHECK (total_copies > 0),
    available_copies SMALLINT     NOT NULL CHECK (available_copies >= 0),
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_copies CHECK (available_copies <= total_copies)
);

-- ------------------------------------------------------------
-- LOANS
-- ------------------------------------------------------------
CREATE TABLE loans (
    id          INT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     INT         NOT NULL,
    book_id     INT         NOT NULL,
    due_date    DATE        NOT NULL,
    return_date DATE,
    status_id   SMALLINT    NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_loans_user_id
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT fk_loans_book_id
        FOREIGN KEY (book_id) REFERENCES books(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT fk_loans_status_id
        FOREIGN KEY (status_id) REFERENCES loan_status(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT chk_due_after_loan
        CHECK (due_date > created_at::date),
    CONSTRAINT chk_return_after_loan
        CHECK (return_date IS NULL OR return_date >= created_at::date)
);

-- ------------------------------------------------------------
-- RESERVATIONS
-- ------------------------------------------------------------
CREATE TABLE reservations (
    id         INT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    INT         NOT NULL,
    book_id    INT         NOT NULL,
    loan_id    INT,
    expires_at TIMESTAMPTZ NOT NULL,
    status_id  SMALLINT    NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_reservations_user_id
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT fk_reservations_book_id
        FOREIGN KEY (book_id) REFERENCES books(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT fk_reservations_loan_id
        FOREIGN KEY (loan_id) REFERENCES loans(id)
        ON DELETE SET NULL ON UPDATE NO ACTION,
    CONSTRAINT fk_reservations_status_id
        FOREIGN KEY (status_id) REFERENCES reservation_status(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT chk_expires_after_reserved
        CHECK (expires_at > created_at)
);

-- ------------------------------------------------------------
-- FINES
-- ------------------------------------------------------------
CREATE TABLE fines (
    id           INT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    loan_id      INT          NOT NULL UNIQUE,
    amount_euros NUMERIC(6,2) NOT NULL CHECK (amount_euros > 0),
    status_id    SMALLINT     NOT NULL,
    paid_at      TIMESTAMPTZ,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_fines_loan_id
        FOREIGN KEY (loan_id) REFERENCES loans(id)
        ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_fines_status_id
        FOREIGN KEY (status_id) REFERENCES fine_status(id)
        ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT chk_paid_at_requires_paid_status
        CHECK (paid_at IS NULL OR status_id IN (1, 2))
);

-- ------------------------------------------------------------
-- ÍNDICES
-- ------------------------------------------------------------
-- Búsquedas frecuentes por usuario y estado
CREATE INDEX idx_loans_user_id    ON loans(user_id);
CREATE INDEX idx_loans_status_id  ON loans(status_id);
CREATE INDEX idx_reservations_user_id ON reservations(user_id);
CREATE INDEX idx_reservations_book_id ON reservations(book_id);

-- Cola de reservas: el primero en espera para un libro
CREATE INDEX idx_reservations_pending ON reservations(book_id, created_at)
WHERE status_id = 0;

-- Préstamos activos vencidos (para el job de detección de mora)
CREATE INDEX idx_loans_overdue ON loans(due_date)
WHERE status_id = 0;

-- Multas pendientes por cobrar
CREATE INDEX idx_fines_pending ON fines(status_id)
WHERE status_id = 0;

-- Búsqueda de libros por ISBN, título, autor
CREATE INDEX idx_books_isbn   ON books(isbn);
CREATE INDEX idx_books_title  ON books USING gin(to_tsvector('spanish', title));
CREATE INDEX idx_books_author ON books USING gin(to_tsvector('spanish', author));

-- ------------------------------------------------------------
-- TRIGGER: updated_at automático
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_books_updated_at
    BEFORE UPDATE ON books
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_loans_updated_at
    BEFORE UPDATE ON loans
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_reservations_updated_at
    BEFORE UPDATE ON reservations
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_fines_updated_at
    BEFORE UPDATE ON fines
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

-- ------------------------------------------------------------
-- TRIGGER: available_copies sincronizado con préstamos activos
--
-- loan_status IDs:
--   0 = active   → libro prestado
--   1 = returned → libro devuelto
--   2 = overdue  → libro aún prestado (vencido)
--
-- status_id != 1 significa que el libro está fuera
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION sync_available_copies() RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.status_id != 1 THEN
            UPDATE books SET available_copies = available_copies - 1 WHERE id = NEW.book_id;
        END IF;
        RETURN NEW;

    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.status_id != 1 AND NEW.status_id = 1 THEN
            UPDATE books SET available_copies = available_copies + 1 WHERE id = NEW.book_id;
        ELSIF OLD.status_id = 1 AND NEW.status_id != 1 THEN
            UPDATE books SET available_copies = available_copies - 1 WHERE id = NEW.book_id;
        END IF;
        RETURN NEW;

    ELSIF TG_OP = 'DELETE' THEN
        IF OLD.status_id != 1 THEN
            UPDATE books SET available_copies = available_copies + 1 WHERE id = OLD.book_id;
        END IF;
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_loans_sync_available_copies
    AFTER INSERT OR UPDATE OF status_id OR DELETE ON loans
    FOR EACH ROW EXECUTE FUNCTION sync_available_copies();
