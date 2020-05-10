package com.wyr.garage.data;

import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.db.AppDatabase;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            LoggedInUser user = AppDatabase.getInstance().userDao().getUser(username, password);
            if (user != null) {
                return new Result.Success<LoggedInUser>(user);
            }
        } catch (Exception e) {

        }
        return new Result.Error(new IOException("Error logging in"));
    }

    public void logout() {
        // TODO: revoke authentication
    }

//    private boolean isMyUser(String username, String password) {
//        return "admin".equals(username) && "admin".equals(password);
//    }
}
