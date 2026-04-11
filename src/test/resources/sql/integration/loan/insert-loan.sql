-- Active loan (id 300) for user 100 and book 200; triggers reduce available_copies on the book.
INSERT INTO loans (id, user_id, book_id, due_date, return_date, status_id, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    300,
    100,
    200,
    (CURRENT_DATE + 10),
    NULL,
    0,
    TIMESTAMPTZ '2026-01-01 12:00:00+00',
    TIMESTAMPTZ '2026-01-01 12:00:00+00'
);
