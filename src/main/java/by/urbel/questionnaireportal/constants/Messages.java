package by.urbel.questionnaireportal.constants;

public interface Messages {
    String USER_ID_NOT_NULL = "User id cannot be null";
    String OLD_PASSWORD_NOT_EMPTY = "Old password cannot be empty";
    String OLD_PASSWORD_SIZE = "Old password must be between 6 and 16 characters long";
    String PASSWORD_NOT_EMPTY = "Password cannot be empty";
    String PASSWORD_SIZE = "Password must be between 6 and 16 characters long";
    String FIELD_ANSWER_ID_NOT_NULL = "Id of field answer must be null";
    String FIELD_ID_NOT_NULL = "Field id cannot be null";
    String FIELD_ID_NULL = "Field id must be null";
    String LABEL_NOT_EMPTY = "Label cannot be empty";
    String LABEL_SIZE = "Max length of label is 100 characters";
    String FIELD_TYPE_NOT_EMPTY = "Field type cannot be empty";
    String FIELD_TYPE_SIZE = "Max length of field type is 50 characters";
    String Q_ID_NOT_NULL = "Questionnaire id cannot be null";
    String REQUIRED_NOT_NULL = "IsRequired cannot be null";
    String ACTIVE_NOT_NULL = "IsActive cannot be null";
    String Q_ANSWER_ID_NOT_NULL = "Id of questionnaire answer must be null.";
    String FIELD_ANSWERS_NOT_EMPTY = "Questionnaire must have answers of fields";
    String EMAIL_NOT_EMPTY = "Email cannot be empty";
    String INVALID_EMAIL = "Invalid email";
    String EMAIL_SIZE = "Max length of email is 256 characters";
    String FIRSTNAME_SIZE = "Max length of firstname is 50 characters";
    String LASTNAME_SIZE = "Max length of lastname is 50 characters";
    String PHONE_SIZE = "Max length of phone number is 16 characters";

    String INCORRECT_EMAIL_PASSWORD = "Incorrect email or password";
    String INVALID_DTO = "Invalid fields of dto entity";
    String USER_NOT_FOUND = "User not found";
    String PASSWORDS_MUST_BE_DIFFERENT = "New and old passwords must be different";
    String EMAIL_USED = "Email is already used";
    String INCORRECT_OLD_PASSWORD = "Incorrect old password";

    String Q_NOT_FOUND = "Questionnaire not found";
    String FIELD_EXISTS = "Field already exists";
    String FIELD_NOT_FOUND = "Field not found";
    String NO_PERMISSIONS_DELETE_FIELD = "No permission to delete this field";
    String Q_ANSWER_EXISTS = "Answer of questionnaire already exists";
    String MUST_BE_ENUM = "Must be any of enum {enumClass}";

}
