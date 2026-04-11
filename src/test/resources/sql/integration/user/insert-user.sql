-- Inserts a sample user (id 100); keep in sync with tests that depend on these values.
INSERT INTO users (id, name, email, phone, status_id, role_id, created_at, updated_at)
OVERRIDING SYSTEM VALUE
VALUES (
    100,
    'Integration User',
    'integration.user@example.com',
    NULL,
    0,
    0,
    TIMESTAMPTZ '2026-01-01 10:00:00+00',
    TIMESTAMPTZ '2026-01-01 10:00:00+00'
);
