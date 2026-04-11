-- Pending loan request (id 500) for user 100 and book 200.
INSERT INTO loan_requests (id, user_id, book_id, request_date, approval_date, status_id, rejection_reason, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    500,
    100,
    200,
    DATE '2026-01-02',
    NULL,
    0,
    NULL,
    TIMESTAMPTZ '2026-01-02 10:00:00+00',
    TIMESTAMPTZ '2026-01-02 10:00:00+00'
);
