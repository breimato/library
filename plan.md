# Plan: FineCalculationService

## Context

Pure domain service that encapsulates the business rule for calculating overdue fines.
No external dependencies (no ports, no DB) — deterministic calculation.
Same interface + implementation pattern as `LoanPolicyValidationService`.

**Note:** No `Loan` model exists yet. The service works directly with dates,
making it immediately usable and independently testable regardless of how
the Loan entity is designed later.

---

## Business rule

```
fine = overdueDays * DAILY_FINE_RATE
```

| Condition               | Result            |
|-------------------------|-------------------|
| `returnDate <= dueDate` | `BigDecimal.ZERO` |
| `returnDate > dueDate`  | `days * 0.50`     |

`DAILY_FINE_RATE = 0.50` (€/day) — constant in the implementation.

---

## Method signature

```java
// interface
BigDecimal execute(@NotNull LocalDate dueDate, @NotNull LocalDate returnDate);
```

Returns `BigDecimal.ZERO` when not overdue, fine amount otherwise.
Caller is responsible for passing `LocalDate.now()` as `returnDate` for active loans.

---

## Architecture

```
domain/service/
  FineCalculationService.java        ← interface
  FineCalculationServiceImpl.java    ← implementation
```

---

## Files to create

### 1. `domain/service/FineCalculationService.java` — **Create**

```java
public interface FineCalculationService {
    BigDecimal execute(LocalDate dueDate, LocalDate returnDate);
}
```

### 2. `domain/service/FineCalculationServiceImpl.java` — **Create**

```java
@Component
public class FineCalculationServiceImpl implements FineCalculationService {

    private static final BigDecimal DAILY_FINE_RATE = new BigDecimal("0.50");

    @Override
    public BigDecimal calculate(@NotNull final LocalDate dueDate, @NotNull final LocalDate returnDate) {
        if (!returnDate.isAfter(dueDate)) {
            return BigDecimal.ZERO;
        }
        final var overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        return DAILY_FINE_RATE.multiply(BigDecimal.valueOf(overdueDays));
    }
}
```

### 3. `src/test/java/.../domain/service/FineCalculationServiceTest.java` — **Create**

Three scenarios — no mocks needed, `@InjectMocks FineCalculationServiceImpl` only:

| Scenario | dueDate | returnDate | Expected |
|---|---|---|---|
| Returned on time (same day) | D | D | `BigDecimal.ZERO` |
| Returned early | D | D - 3 | `BigDecimal.ZERO` |
| Returned late (2 days) | D | D + 2 | `new BigDecimal("1.00")` |

> `new BigDecimal("1.00")` is valid here — exact value required to verify calculation logic.

---

## Verification

```bash
mvn test
```

All existing tests still pass. Three new tests cover both branches and the happy path.
