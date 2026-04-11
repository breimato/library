-- Clears mutable business tables; lookup tables from Flyway migrations are preserved.
TRUNCATE TABLE fines, reservations, loans, loan_requests, books, users RESTART IDENTITY CASCADE;
