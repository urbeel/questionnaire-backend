package by.urbel.questionnaireportal.constants;

public interface Routes {
    String AUTH = "/api/auth";
    String SIGNUP = "/signup";
    String LOGIN = "/login";
    String CHANGE_PASSWORD = "/password";

    String FIELDS = "/api/fields";
    String ACTIVE_FIELDS = "/active";
    String FIELDS_SIZE = "/size";
    String FIELD_ID = "/{id}";

    String Q_ANSWERS = "/api/responses";
    String Q_ANSWERS_SIZE = "/size";

    String USERS = "/api/users";
    String USER_ID = "/{userId}";
    String APP_PREFIX = "/api";
}
