package controller;

import entities.Route;
import entities.User;
import org.json.JSONException;
import usecases.AirportReadWriter;
import usecases.LoginHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * UserController is responsible for implementing features such as logging in a user, and creating new accounts.
 */

public class UserController {
    private final LoginHandler loginHandler;

    public UserController(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    /**
     * Create a new user account.
     * username, email, and phone number must be unique
     * email and phone number must follow respective formatting
     *
     * @param username    unique name of the user
     * @param password    password of the user
     * @param email       unique email of the user
     * @param phoneNumber unique phone number of the user
     * @return true if account creation is successful.  false otherwise.
     */
    public String createAccount(String username, String password, String email, String phoneNumber) {
        try {
            ArrayList<String> errors = loginHandler.createAccount(username, password, email, phoneNumber);

            if (errors.size() == 0) {
                // account creation is successful
                return "Account Created Successfully";
            }

            String output = "";
            for (String item : errors) {
                output = output + item + "\n";
            }
            return output;
        } catch (NullPointerException e) {
            return "NullPointerException";
        }
    }

    public boolean login(String username, String password) {
        try {
            return loginHandler.loginAttempt(username, password);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void logout() {
        loginHandler.logout();
    }

    public void deleteAccount(String username) {
        loginHandler.deleteAccount(username);
    }

    public User getCurrentUser() {
        return loginHandler.currentUser.getCurrentUser();
    }

    /**
     * Serializes and deserializes userList. Call this at the end of changing ViewProfile.
     */
    public void saveSettings() {
        loginHandler.saveSettings();
    }

    // methods below are getters and setters for various user information
    public String getUsername() {
        return loginHandler.currentUser.getUsername();
    }

    public String getPassword() {
        return loginHandler.currentUser.getPassword();
    }

    public void setPassword(String pw) {
        loginHandler.currentUser.setPassword(pw);
    }

    public String getEmail() {
        return loginHandler.currentUser.getEmail();
    }

    public void setEmail(String e) {
        loginHandler.currentUser.setEmail(e);
    }

    public String getPhoneNumber() {
        return loginHandler.currentUser.getPhoneNumber();
    }

    public void setPhoneNumber(String pn) {
        loginHandler.currentUser.setPhoneNumber(pn);
    }

    public int getAppRating() {
        return loginHandler.currentUser.getAppRating();
    }

    public void setAppRating(int appRating) {
        loginHandler.currentUser.setAppRating(appRating);
    }

    public StringBuilder getRouteHistory() {
        return loginHandler.currentUser.getRouteHistory();
    }

    public String upgradeUserType() {
        return loginHandler.currentUser.upgradeUserType();
    }

    public String downgradeUserType() {
        return loginHandler.currentUser.downgradeUserType();
    }

    public String getUserType() {
        return loginHandler.currentUser.getUserType();
    }

    public String getClassType() {
        return loginHandler.currentUser.getClassType();
    }

    public String setClassType(String classType) {
        return loginHandler.currentUser.setClassType(classType);
    }

    public Date getRenewalDate() {
        return loginHandler.currentUser.getRenewalDate();
    }

    public String setRenewalDate(Date date) {
        return loginHandler.currentUser.setRenewalDate(date);
    }

    public String getColorScheme() {
        return loginHandler.currentUser.getColorScheme();
    }

    public String setColorScheme(String colorScheme) {
        return loginHandler.currentUser.setColorScheme(colorScheme);
    }

    public StringBuilder getFavouriteAirports() {
        return loginHandler.currentUser.getFavouriteAirports();
    }

    public String getUserDataJson() {
        String s = "{" +
                "\"userName\":" +
                "\"" +
                getUsername() +
                "\"," +
                "\"password\":" +
                "\"" +
                getPassword() +
                "\"," +
                "\"email\":" +
                "\"" +
                getEmail() +
                "\"," +
                "\"phoneNumber\":" +
                "\"" +
                getPhoneNumber() +
                "\"" +
                "}";
        return s;
    }

    public String getUserSettingsJson() {
        String s = "{" +
                "\"colorScheme\":" +
                "\"" +
                "Blue" +
                "\"," +
                "\"homeAirport\":" +
                "\"" +
                "Toronto" +
                "\"," +
                "\"favouriteAirport\":" +
                "\"" +
                "None" +
                "\"," +
                "\"autoLogoutTimer\":" +
                "\"" +
                10 +
                "\"" +
                "}";
        return s;
    }

    // TODO: add fav airport, remove fav airport

    public String addFavouriteAirport(String iataCode) {
        return loginHandler.currentUser.addFavouriteAirport(AirportReadWriter.getAirportByIata(iataCode));
    }

    public String removeFavouriteAirport(String iataCode) {
        return loginHandler.currentUser.removeFavouriteAirport(AirportReadWriter.getAirportByIata(iataCode));
    }

    public int getAutoLogoutTimer() {
        return loginHandler.currentUser.getAutoLogoutTimer();
    }

    public String setAutoLogoutTimer(int autoLogoutTimer) {
        return loginHandler.currentUser.setAutoLogoutTimer(autoLogoutTimer);
    }

    public StringBuilder getHomeAirport() {
        return loginHandler.currentUser.getHomeAirport();
    }


    // TODO: set home airport

    public void removeRoutebyID(String id) {
        loginHandler.currentUser.removeRoutebyID(id);
        saveSettings();
    }

    public String setHomeAirport(String iataCode) {
        return loginHandler.currentUser.setHomeAirport(AirportReadWriter.getAirportByIata(iataCode));
    }

    public String addRouteToHistory(Route routeJSON) {
        try {
            loginHandler.currentUser.addRouteToHistory(routeJSON);
            saveSettings();
            return "Successfully saved route to User history.";
        } catch (JSONException | ParseException e) {
            return "Unable to save route to User history.";
        }

    }
}