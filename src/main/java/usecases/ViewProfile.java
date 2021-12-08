package usecases;

import entities.Airport;
import entities.Route;
import entities.SearchResults;
import entities.User;
import org.json.JSONException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * ViewProfile handles editing user profile information and different methods based on user subscription status.
 */

public class ViewProfile {
    private final User currentUser;

    public ViewProfile(User user) {
        currentUser = user;
    }

    public String getUsername() { return currentUser.getUsername(); }

    public String getPassword() {
        return currentUser.getPassword();
    }

    public void setPassword(String password) {
        currentUser.setPassword(password);
    }

    public String getEmail() {
        return currentUser.getEmail();
    }

    public void setEmail(String email) {
        currentUser.setEmail(email);
    }

    public String getPhoneNumber() {
        return currentUser.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        currentUser.setPhoneNumber(phoneNumber);
    }

    public int getAppRating() {
        return currentUser.getAppRating();
    }

    public void setAppRating(int appRating) {
        currentUser.setAppRating(appRating);
    }

    public void addRouteToHistory(Route routeJSON) throws JSONException, ParseException {
            currentUser.addRouteToHistory(routeJSON);


    }
    public User getCurrentUser(){
        return this.currentUser;
    }

    public void removeRoutebyID(String id) { currentUser.removeRoutebyID(id); }

    /**
     * Return the users route history in a json parseable string using the method in SearchResults.
     * @return StringBuilder of the users route history in json parseable String format
     */
    public String getRouteHistory() {
        List<Route> routeHistory = currentUser.getRouteHistory();
        SearchResults routeHistoryResults = new SearchResults(routeHistory);
        return routeHistoryResults.toString(this.getCurrentUser());
    }

    public String upgradeUserType() {
        return currentUser.upgradeUserType();
    }

    public String downgradeUserType() {
        return currentUser.downgradeUserType();
    }

    public String getUserType() { return currentUser.getUserType(); }

    // methods past here are premium user functions, so they check user type
    public String isValidRequest(boolean valid) {
        if (!valid) {
            return "Not available for Basic Users. Upgrade to Premium today!";
        }
        else {
            return "Successfully changed."; }
    }

    public String getClassType() {
        return currentUser.user.getClassType();
    }

    public String setClassType(String classType) { return isValidRequest(currentUser.user.setClassType(classType)); }

    public Date getRenewalDate() {
        return currentUser.user.getRenewalDate();
    }

    public String setRenewalDate(Date date) { return isValidRequest(currentUser.user.setRenewalDate(date)); }

    public String getColorScheme() { return currentUser.user.getColorScheme(); }

    public String setColorScheme(String colorScheme) { return isValidRequest(currentUser.user.setColorScheme(colorScheme)); }

    /**
     * Return a StringBuilder representing this users favourite airports
     * @return String
     */
    public String getFavouriteAirports() {
        StringBuilder returnString = new StringBuilder("[");
        List<Airport> favAirports = currentUser.user.getFavouriteAirports();

        for (Airport airport : favAirports) {
            returnString.append(airport.toString());
        }

        returnString.append("]");
        return new String(returnString);
    }

    public String addFavouriteAirport(Airport airport) { return isValidRequest(currentUser.user.addFavouriteAirport(airport)); }

    public String removeFavouriteAirport(Airport airport) { return isValidRequest(currentUser.user.removeFavouriteAirport(airport)); }

    public int getAutoLogoutTimer() { return currentUser.user.getAutoLogoutTimer(); }

    public String setAutoLogoutTimer(int autoLogoutTimer) {
        return isValidRequest(currentUser.user.setAutoLogoutTimer(autoLogoutTimer));
    }

    public String getHomeAirport() {
        return currentUser.user.getHomeAirport().toString();
    }

    public String setHomeAirport(Airport airport) { return isValidRequest(currentUser.user.setHomeAirport(airport)); }
}
