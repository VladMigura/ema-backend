package com.itechart.ema.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String EMAIL_OR_PASSWORD_BAD_REQUEST = "Incorrect email or password.";
    public static final String CURRENT_PASSWORD_BAD_REQUEST = "Incorrect current password.";
    public static final String INCORRECT_MANAGER_ROLE = "The manager must have the corresponding role.";
    public static final String INCORRECT_SCRUM_MASTER_ROLE = "The scrum master must have the corresponding role.";

    public static final String REFRESH_TOKEN_UNAUTHORIZED = "Invalid refresh token.";

    public static final String NO_ACCESS_TO_MODIFY_THIS_RESOURCE_FORBIDDEN = "You have no access to modify this resource.";

    public static final String POST_NOT_FOUND = "Post could not be found.";
    public static final String PROJECT_NOT_FOUND = "Project could not be found.";
    public static final String TASK_NOT_FOUND = "Task could not be found.";
    public static final String TEAM_NOT_FOUND = "Team could not be found.";
    public static final String USER_NOT_FOUND = "User could not be found.";
    public static final String USER_EMAIL_NOT_FOUND = "User with such email not found.";
    public static final String MANAGER_NOT_FOUND = "Manager could not be found";
    public static final String SCRUM_MASTER_NOT_FOUND = "Scrum master could not be found";

    public static final String USER_EMAIL_CONFLICT = "User with such email already exists.";

}
