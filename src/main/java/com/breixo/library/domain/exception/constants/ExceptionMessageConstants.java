package com.breixo.library.domain.exception.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The Class Exception Message Constants. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessageConstants {

    /** The Constant BOOK_NOT_FOUND_CODE_ERROR. */
    public static final String BOOK_NOT_FOUND_CODE_ERROR = "LIB-BOOK-001";

    /** The Constant BOOK_NOT_FOUND_MESSAGE_ERROR. */
    public static final String BOOK_NOT_FOUND_MESSAGE_ERROR = "Error: Book not found";

    /** The Constant BOOK_CREATION_ERROR_CODE_ERROR. */
    public static final String BOOK_CREATION_ERROR_CODE_ERROR = "LIB-BOOK-002";

    /** The Constant BOOK_CREATION_ERROR_MESSAGE_ERROR. */
    public static final String BOOK_CREATION_ERROR_MESSAGE_ERROR = "Error: Book creation failed";

    /** The Constant BOOK_UPDATE_ERROR_CODE_ERROR. */
    public static final String BOOK_UPDATE_ERROR_CODE_ERROR = "LIB-BOOK-003";

    /** The Constant BOOK_UPDATE_ERROR_MESSAGE_ERROR. */
    public static final String BOOK_UPDATE_ERROR_MESSAGE_ERROR = "Error: Book update failed";

    /** The Constant BOOK_COPIES_NOT_AVAILABLE_CODE_ERROR. */
    public static final String BOOK_COPIES_NOT_AVAILABLE_CODE_ERROR = "LIB-BOOK-004";

    /** The Constant BOOK_COPIES_NOT_AVAILABLE_MESSAGE_ERROR. */
    public static final String BOOK_COPIES_NOT_AVAILABLE_MESSAGE_ERROR = "Error: No available copies for this book";

    /** The Constant BOOK_RETIRED_CODE_ERROR. */
    public static final String BOOK_RETIRED_CODE_ERROR = "LIB-BOOK-005";

    /** The Constant BOOK_RETIRED_MESSAGE_ERROR. */
    public static final String BOOK_RETIRED_MESSAGE_ERROR = "Error: Book has been retired from the library";

    /** The Constant INVALID_ISBN_CODE_ERROR. */
    public static final String INVALID_ISBN_CODE_ERROR = "LIB-ISBN-001";

    /** The Constant INVALID_ISBN_FORMAT_MESSAGE_ERROR. */
    public static final String INVALID_ISBN_FORMAT_MESSAGE_ERROR = "Error: Invalid ISBN-13 format";

    /** The Constant INVALID_ISBN_CHECK_DIGIT_MESSAGE_ERROR. */
    public static final String INVALID_ISBN_CHECK_DIGIT_MESSAGE_ERROR = "Error: Invalid ISBN-13 check digit";

    /** The Constant USER_NOT_FOUND_CODE_ERROR. */
    public static final String USER_NOT_FOUND_CODE_ERROR = "LIB-USER-001";

    /** The Constant USER_NOT_FOUND_MESSAGE_ERROR. */
    public static final String USER_NOT_FOUND_MESSAGE_ERROR = "Error: User not found";

    /** The Constant USER_EMAIL_ALREADY_EXISTS_CODE_ERROR. */
    public static final String USER_EMAIL_ALREADY_EXISTS_CODE_ERROR = "LIB-USER-002";

    /** The Constant USER_EMAIL_ALREADY_EXISTS_MESSAGE_ERROR. */
    public static final String USER_EMAIL_ALREADY_EXISTS_MESSAGE_ERROR = "Error: User email already exists";

    /** The Constant USER_UPDATE_ERROR_CODE_ERROR. */
    public static final String USER_UPDATE_ERROR_CODE_ERROR = "LIB-USER-003";

    /** The Constant USER_UPDATE_ERROR_MESSAGE_ERROR. */
    public static final String USER_UPDATE_ERROR_MESSAGE_ERROR = "Error: User update failed";

    /** The Constant USER_BLOCKED_CODE_ERROR. */
    public static final String USER_BLOCKED_CODE_ERROR = "LIB-USER-004";

    /** The Constant USER_BLOCKED_MESSAGE_ERROR. */
    public static final String USER_BLOCKED_MESSAGE_ERROR = "Error: User is blocked and cannot borrow books";

    /** The Constant USER_SUSPENDED_CODE_ERROR. */
    public static final String USER_SUSPENDED_CODE_ERROR = "LIB-USER-005";

    /** The Constant USER_SUSPENDED_MESSAGE_ERROR. */
    public static final String USER_SUSPENDED_MESSAGE_ERROR = "Error: User is suspended and cannot borrow books";

    /** The Constant USER_HAS_PENDING_FINES_CODE_ERROR. */
    public static final String USER_HAS_PENDING_FINES_CODE_ERROR = "LIB-USER-006";

    /** The Constant USER_HAS_PENDING_FINES_MESSAGE_ERROR. */
    public static final String USER_HAS_PENDING_FINES_MESSAGE_ERROR = "Error: User has pending fines and cannot borrow books";

    /** The Constant LOAN_NOT_FOUND_CODE_ERROR. */
    public static final String LOAN_NOT_FOUND_CODE_ERROR = "LIB-LOAN-001";

    /** The Constant LOAN_NOT_FOUND_MESSAGE_ERROR. */
    public static final String LOAN_NOT_FOUND_MESSAGE_ERROR = "Error: Loan not found";

    /** The Constant LOAN_CREATION_ERROR_CODE_ERROR. */
    public static final String LOAN_CREATION_ERROR_CODE_ERROR = "LIB-LOAN-002";

    /** The Constant LOAN_CREATION_ERROR_MESSAGE_ERROR. */
    public static final String LOAN_CREATION_ERROR_MESSAGE_ERROR = "Error: Loan creation failed";

    /** The Constant LOAN_UPDATE_ERROR_CODE_ERROR. */
    public static final String LOAN_UPDATE_ERROR_CODE_ERROR = "LIB-LOAN-003";

    /** The Constant LOAN_UPDATE_ERROR_MESSAGE_ERROR. */
    public static final String LOAN_UPDATE_ERROR_MESSAGE_ERROR = "Error: Loan update failed";

    /** The Constant LOAN_USER_HAS_OVERDUE_LOANS_CODE_ERROR. */
    public static final String LOAN_USER_HAS_OVERDUE_LOANS_CODE_ERROR = "LIB-LOAN-004";

    /** The Constant LOAN_USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR. */
    public static final String LOAN_USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR = "Error: Loan cannot be created because user has overdue loans";

    /** The Constant LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR. */
    public static final String LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR = "LIB-LOAN-005";

    /** The Constant LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR. */
    public static final String LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR = "Error: Loan cannot be created because user has reached the maximum number of active loans";

    /** The Constant LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR. */
    public static final String LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR = "LIB-LOAN-006";

    /** The Constant LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR. */
    public static final String LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR = "Error: Loan cannot be created because user already has this book on loan";

    /** The Constant LOAN_ALREADY_RETURNED_CODE_ERROR. */
    public static final String LOAN_ALREADY_RETURNED_CODE_ERROR = "LIB-LOAN-007";

    /** The Constant LOAN_ALREADY_RETURNED_MESSAGE_ERROR. */
    public static final String LOAN_ALREADY_RETURNED_MESSAGE_ERROR = "Error: Loan has already been returned";

    /** The Constant LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR. */
    public static final String LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR = "LIB-LOAN-008";

    /** The Constant LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR. */
    public static final String LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR = "Error: Loan cannot be created because book is reserved by another user";

    /** The Constant LOAN_INVALID_STATUS_TRANSITION_CODE_ERROR. */
    public static final String LOAN_INVALID_STATUS_TRANSITION_CODE_ERROR = "LIB-LOAN-009";

    /** The Constant LOAN_INVALID_STATUS_TRANSITION_MESSAGE_ERROR. */
    public static final String LOAN_INVALID_STATUS_TRANSITION_MESSAGE_ERROR = "Error: Invalid loan status transition";

    /** The Constant LOAN_RETURN_DATE_INVALID_CODE_ERROR. */
    public static final String LOAN_RETURN_DATE_INVALID_CODE_ERROR = "LIB-LOAN-010";

    /** The Constant LOAN_RETURN_DATE_INVALID_MESSAGE_ERROR. */
    public static final String LOAN_RETURN_DATE_INVALID_MESSAGE_ERROR = "Error: Return date cannot be in the future";

    /** The Constant LOAN_DUE_DATE_INVALID_CODE_ERROR. */
    public static final String LOAN_DUE_DATE_INVALID_CODE_ERROR = "LIB-LOAN-011";

    /** The Constant LOAN_DUE_DATE_INVALID_MESSAGE_ERROR. */
    public static final String LOAN_DUE_DATE_INVALID_MESSAGE_ERROR = "Error: New due date must be after current due date";

    /** The Constant FINE_NOT_FOUND_CODE_ERROR. */
    public static final String FINE_NOT_FOUND_CODE_ERROR = "LIB-FINE-001";

    /** The Constant FINE_NOT_FOUND_MESSAGE_ERROR. */
    public static final String FINE_NOT_FOUND_MESSAGE_ERROR = "Error: Fine not found";

    /** The Constant FINE_CREATION_ERROR_CODE_ERROR. */
    public static final String FINE_CREATION_ERROR_CODE_ERROR = "LIB-FINE-002";

    /** The Constant FINE_CREATION_ERROR_MESSAGE_ERROR. */
    public static final String FINE_CREATION_ERROR_MESSAGE_ERROR = "Error: Fine creation failed";

    /** The Constant FINE_UPDATE_ERROR_CODE_ERROR. */
    public static final String FINE_UPDATE_ERROR_CODE_ERROR = "LIB-FINE-003";

    /** The Constant FINE_UPDATE_ERROR_MESSAGE_ERROR. */
    public static final String FINE_UPDATE_ERROR_MESSAGE_ERROR = "Error: Fine update failed";

    /** The Constant RESERVATION_NOT_FOUND_CODE_ERROR. */
    public static final String RESERVATION_NOT_FOUND_CODE_ERROR = "LIB-RESERVATION-001";

    /** The Constant RESERVATION_NOT_FOUND_MESSAGE_ERROR. */
    public static final String RESERVATION_NOT_FOUND_MESSAGE_ERROR = "Error: Reservation not found";

    /** The Constant RESERVATION_UPDATE_ERROR_CODE_ERROR. */
    public static final String RESERVATION_UPDATE_ERROR_CODE_ERROR = "LIB-RESERVATION-002";

    /** The Constant RESERVATION_UPDATE_ERROR_MESSAGE_ERROR. */
    public static final String RESERVATION_UPDATE_ERROR_MESSAGE_ERROR = "Error: Reservation update failed";

    /** The Constant RESERVATION_CREATION_ERROR_CODE_ERROR. */
    public static final String RESERVATION_CREATION_ERROR_CODE_ERROR = "LIB-RESERVATION-003";

    /** The Constant RESERVATION_CREATION_ERROR_MESSAGE_ERROR. */
    public static final String RESERVATION_CREATION_ERROR_MESSAGE_ERROR = "Error: Reservation creation failed";

    /** The Constant RESERVATION_USER_BLOCKED_CODE_ERROR. */
    public static final String RESERVATION_USER_BLOCKED_CODE_ERROR = "LIB-RESERVATION-004";

    /** The Constant RESERVATION_USER_BLOCKED_MESSAGE_ERROR. */
    public static final String RESERVATION_USER_BLOCKED_MESSAGE_ERROR = "Error: User is blocked and cannot reserve books";

    /** The Constant RESERVATION_USER_SUSPENDED_CODE_ERROR. */
    public static final String RESERVATION_USER_SUSPENDED_CODE_ERROR = "LIB-RESERVATION-005";

    /** The Constant RESERVATION_USER_SUSPENDED_MESSAGE_ERROR. */
    public static final String RESERVATION_USER_SUSPENDED_MESSAGE_ERROR = "Error: User is suspended and cannot reserve books";

    /** The Constant RESERVATION_USER_HAS_PENDING_FINES_CODE_ERROR. */
    public static final String RESERVATION_USER_HAS_PENDING_FINES_CODE_ERROR = "LIB-RESERVATION-006";

    /** The Constant RESERVATION_USER_HAS_PENDING_FINES_MESSAGE_ERROR. */
    public static final String RESERVATION_USER_HAS_PENDING_FINES_MESSAGE_ERROR = "Error: User has pending fines and cannot reserve books";

    /** The Constant RESERVATION_ALREADY_EXISTS_CODE_ERROR. */
    public static final String RESERVATION_ALREADY_EXISTS_CODE_ERROR = "LIB-RESERVATION-007";

    /** The Constant RESERVATION_ALREADY_EXISTS_MESSAGE_ERROR. */
    public static final String RESERVATION_ALREADY_EXISTS_MESSAGE_ERROR = "Error: User already has an active reservation for this book";

    /** The Constant RESERVATION_COPIES_AVAILABLE_CODE_ERROR. */
    public static final String RESERVATION_COPIES_AVAILABLE_CODE_ERROR = "LIB-RESERVATION-008";

    /** The Constant RESERVATION_COPIES_AVAILABLE_MESSAGE_ERROR. */
    public static final String RESERVATION_COPIES_AVAILABLE_MESSAGE_ERROR = "Error: Book has available copies, user should borrow it directly";

    /** The Constant RESERVATION_BOOK_RETIRED_CODE_ERROR. */
    public static final String RESERVATION_BOOK_RETIRED_CODE_ERROR = "LIB-RESERVATION-009";

    /** The Constant RESERVATION_BOOK_RETIRED_MESSAGE_ERROR. */
    public static final String RESERVATION_BOOK_RETIRED_MESSAGE_ERROR = "Error: Book is retired and cannot be reserved";

    /** The Constant LOAN_REQUEST_NOT_FOUND_CODE_ERROR. */
    public static final String LOAN_REQUEST_NOT_FOUND_CODE_ERROR = "LIB-LOANREQ-001";

    /** The Constant LOAN_REQUEST_NOT_FOUND_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_NOT_FOUND_MESSAGE_ERROR = "Error: Loan request not found";

    /** The Constant LOAN_REQUEST_CREATION_ERROR_CODE_ERROR. */
    public static final String LOAN_REQUEST_CREATION_ERROR_CODE_ERROR = "LIB-LOANREQ-002";

    /** The Constant LOAN_REQUEST_CREATION_ERROR_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_CREATION_ERROR_MESSAGE_ERROR = "Error: Loan request creation failed";

    /** The Constant LOAN_REQUEST_UPDATE_ERROR_CODE_ERROR. */
    public static final String LOAN_REQUEST_UPDATE_ERROR_CODE_ERROR = "LIB-LOANREQ-003";

    /** The Constant LOAN_REQUEST_UPDATE_ERROR_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_UPDATE_ERROR_MESSAGE_ERROR = "Error: Loan request update failed";

    /** The Constant LOAN_REQUEST_INVALID_STATUS_TRANSITION_CODE_ERROR. */
    public static final String LOAN_REQUEST_INVALID_STATUS_TRANSITION_CODE_ERROR = "LIB-LOANREQ-004";

    /** The Constant LOAN_REQUEST_INVALID_STATUS_TRANSITION_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_INVALID_STATUS_TRANSITION_MESSAGE_ERROR = "Error: Invalid loan request status transition";

    /** The Constant LOAN_REQUEST_USER_HAS_PENDING_FINES_CODE_ERROR. */
    public static final String LOAN_REQUEST_USER_HAS_PENDING_FINES_CODE_ERROR = "LIB-LOANREQ-005";

    /** The Constant LOAN_REQUEST_USER_HAS_PENDING_FINES_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_USER_HAS_PENDING_FINES_MESSAGE_ERROR = "Error: Loan request cannot be created because user has pending fines";

    /** The Constant LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR. */
    public static final String LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR = "LIB-LOANREQ-006";

    /** The Constant LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR = "Error: Loan request cannot be created because user has reached the maximum number of active loans";

    /** The Constant LOAN_REQUEST_BOOK_AVAILABLE_CODE_ERROR. */
    public static final String LOAN_REQUEST_BOOK_AVAILABLE_CODE_ERROR = "LIB-LOANREQ-007";

    /** The Constant LOAN_REQUEST_BOOK_AVAILABLE_MESSAGE_ERROR. */
    public static final String LOAN_REQUEST_BOOK_AVAILABLE_MESSAGE_ERROR = "Error: Book has available copies, user should borrow it directly instead of requesting";

    /** The Constant FORBIDDEN_ACTION_CODE_ERROR. */
    public static final String FORBIDDEN_ACTION_CODE_ERROR = "LIB-AUTH-001";

    /** The Constant FORBIDDEN_ACTION_MESSAGE_ERROR. */
    public static final String FORBIDDEN_ACTION_MESSAGE_ERROR = "Error: You do not have permission to perform this action";

    /** The Constant FORBIDDEN_OWN_RESOURCE_CODE_ERROR. */
    public static final String FORBIDDEN_OWN_RESOURCE_CODE_ERROR = "LIB-AUTH-002";

    /** The Constant FORBIDDEN_OWN_RESOURCE_MESSAGE_ERROR. */
    public static final String FORBIDDEN_OWN_RESOURCE_MESSAGE_ERROR = "Error: You can only perform this action on your own resources";
}
