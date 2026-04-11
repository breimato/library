-- Pending reservation with expires_at in the past (for mark-expired job). id 401.
INSERT INTO reservations (id, user_id, book_id, loan_id, expires_at, status_id, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    401,
    100,
    200,
    NULL,
    TIMESTAMPTZ '2020-06-01 12:00:00+00',
    0,
    TIMESTAMPTZ '2020-01-01 10:00:00+00',
    TIMESTAMPTZ '2020-01-01 10:00:00+00'
);
