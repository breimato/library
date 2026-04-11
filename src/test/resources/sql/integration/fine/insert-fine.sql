-- Fine (id 600) for loan 300. Run after insert-loan.sql.
INSERT INTO fines (id, loan_id, amount_euros, status_id, paid_at, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    600,
    300,
    12.50,
    0,
    NULL,
    TIMESTAMPTZ '2026-01-01 14:00:00+00',
    TIMESTAMPTZ '2026-01-01 14:00:00+00'
);
