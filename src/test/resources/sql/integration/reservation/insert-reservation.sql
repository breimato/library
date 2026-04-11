-- Pending reservation (id 400) for user 100 and book 200.
INSERT INTO reservations (id, user_id, book_id, loan_id, expires_at, status_id, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    400,
    100,
    200,
    NULL,
    TIMESTAMPTZ '2030-01-01 12:00:00+00',
    0,
    TIMESTAMPTZ '2026-01-01 10:00:00+00',
    TIMESTAMPTZ '2026-01-01 10:00:00+00'
);
