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
}
