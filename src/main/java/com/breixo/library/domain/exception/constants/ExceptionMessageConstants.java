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

    /** The Constant USER_HAS_OVERDUE_LOANS_CODE_ERROR. */
    public static final String USER_HAS_OVERDUE_LOANS_CODE_ERROR = "LIB-LOAN-004";

    /** The Constant USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR. */
    public static final String USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR = "Error: User has overdue loans and cannot borrow books";

    /** The Constant USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR. */
    public static final String USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR = "LIB-LOAN-005";

    /** The Constant USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR. */
    public static final String USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR = "Error: User has reached the maximum number of active loans";

    /** The Constant USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR. */
    public static final String USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR = "LIB-LOAN-006";

    /** The Constant USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR. */
    public static final String USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR = "Error: User already has this book on loan";

    /** The Constant FINE_NOT_FOUND_CODE_ERROR. */
    public static final String FINE_NOT_FOUND_CODE_ERROR = "LIB-FINE-001";

    /** The Constant FINE_NOT_FOUND_MESSAGE_ERROR. */
    public static final String FINE_NOT_FOUND_MESSAGE_ERROR = "Error: Fine not found";

    /** The Constant FINE_CREATION_ERROR_CODE_ERROR. */
    public static final String FINE_CREATION_ERROR_CODE_ERROR = "LIB-FINE-002";

    /** The Constant FINE_CREATION_ERROR_MESSAGE_ERROR. */
    public static final String FINE_CREATION_ERROR_MESSAGE_ERROR = "Error: Fine creation failed";
}
