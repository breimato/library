-- Sample book (id 200). Run after reset + user if FKs from other domains reference users.
INSERT INTO books (id, isbn, title, author, genre, total_copies, available_copies, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    200,
    '9780134685991',
    'Integration Book',
    'Integration Author',
    'Fiction',
    5,
    5,
    TIMESTAMPTZ '2026-01-01 10:00:00+00',
    TIMESTAMPTZ '2026-01-01 10:00:00+00'
);
