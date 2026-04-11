-- Overdue-eligible loan (id 303). Run after insert-user + insert-book.
INSERT INTO loans (id, user_id, book_id, due_date, return_date, status_id, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    303,
    100,
    200,
    DATE '2020-06-01',
    NULL,
    0,
    TIMESTAMPTZ '2020-01-01 12:00:00+00',
    TIMESTAMPTZ '2020-01-01 12:00:00+00'
);
