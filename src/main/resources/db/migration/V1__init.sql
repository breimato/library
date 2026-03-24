-- ============================================================
-- BIBLIOTECA HEXAGONAL — DDL PostgreSQL
-- ============================================================
-- ------------------------------------------------------------
-- ENUMS
-- ------------------------------------------------------------
CREATE TYPE user_status AS ENUM ('active', 'suspended', 'blocked');
CREATE TYPE loan_status AS ENUM ('active', 'returned', 'overdue');
CREATE TYPE reservation_status AS ENUM (
    'pending',
    'notified',
    'fulfilled',
    'expired',
    'cancelled'
);
CREATE TYPE fine_status AS ENUM ('pending', 'paid', 'waived');
-- ------------------------------------------------------------
-- USERS
-- ------------------------------------------------------------
CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    status user_status NOT NULL DEFAULT 'active',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
-- ------------------------------------------------------------
-- BOOKS
-- ------------------------------------------------------------
CREATE TABLE books (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(150) NOT NULL,
    genre VARCHAR(100),
    total_copies SMALLINT NOT NULL CHECK (total_copies > 0),
    available_copies SMALLINT NOT NULL CHECK (available_copies >= 0),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_copies CHECK (available_copies <= total_copies)
);
-- ------------------------------------------------------------
-- LOANS
-- ------------------------------------------------------------
CREATE TABLE loans (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    book_id BIGINT NOT NULL REFERENCES books(id),
    loan_date DATE NOT NULL DEFAULT CURRENT_DATE,
    due_date DATE NOT NULL,
    return_date DATE,
    status loan_status NOT NULL DEFAULT 'active',
    fine_euros NUMERIC(6, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_due_after_loan CHECK (due_date > loan_date),
    CONSTRAINT chk_return_after_loan CHECK (
        return_date IS NULL
        OR return_date >= loan_date
    )
);
-- ------------------------------------------------------------
-- RESERVATIONS
-- ------------------------------------------------------------
CREATE TABLE reservations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    book_id BIGINT NOT NULL REFERENCES books(id),
    reserved_at TIMESTAMP NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMP NOT NULL,
    status reservation_status NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_expires_after_reserved CHECK (expires_at > reserved_at)
);
-- ------------------------------------------------------------
-- FINES
-- ------------------------------------------------------------
CREATE TABLE fines (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    loan_id BIGINT NOT NULL REFERENCES loans(id) UNIQUE,
    amount_euros NUMERIC(6, 2) NOT NULL CHECK (amount_euros > 0),
    status fine_status NOT NULL DEFAULT 'pending',
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_paid_at_requires_paid_status CHECK (
        paid_at IS NULL
        OR status IN ('paid', 'waived')
    )
);
-- ------------------------------------------------------------
-- ÍNDICES
-- ------------------------------------------------------------
-- Búsquedas frecuentes por usuario
CREATE INDEX idx_loans_user_id ON loans(user_id);
CREATE INDEX idx_loans_status ON loans(status);
CREATE INDEX idx_reservations_user_id ON reservations(user_id);
CREATE INDEX idx_reservations_book_id ON reservations(book_id);
-- Cola de reservas: encontrar el primero en espera para un libro
CREATE INDEX idx_reservations_pending ON reservations(book_id, reserved_at)
WHERE status = 'pending';
-- Préstamos activos vencidos (para el job de detección de mora)
CREATE INDEX idx_loans_overdue ON loans(due_date)
WHERE status = 'active';
-- Multas pendientes por cobrar
CREATE INDEX idx_fines_pending ON fines(status)
WHERE status = 'pending';
-- Búsqueda de libros por ISBN, título, autor
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_title ON books USING gin(to_tsvector('spanish', title));
CREATE INDEX idx_books_author ON books USING gin(to_tsvector('spanish', author));
-- ------------------------------------------------------------
-- TRIGGER: updated_at automático
-- ------------------------------------------------------------
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ BEGIN NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_users_updated_at BEFORE
UPDATE ON users FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_books_updated_at BEFORE
UPDATE ON books FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_loans_updated_at BEFORE
UPDATE ON loans FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_reservations_updated_at BEFORE
UPDATE ON reservations FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_fines_updated_at BEFORE
UPDATE ON fines FOR EACH ROW EXECUTE FUNCTION set_updated_at();