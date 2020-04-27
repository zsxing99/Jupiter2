package db;

import java.util.List;
import java.util.Set;

import entity.Item;
import external.GitHub.SearchQuery;

public abstract class DBConnection {
    /**
     * Close the connection.
     */
    public abstract void close();

    /**
     * Insert the favorite items for a user.
     *
     * @param userId
     * @param itemIds
     */
    public abstract void setFavoriteItems(String userId, List<String> itemIds);

    /**
     * Delete the favorite items for a user.
     *
     * @param userId
     * @param itemIds
     */
    public abstract void unsetFavoriteItems(String userId, List<String> itemIds);

    /**
     * Get the favorite item id for a user.
     *
     * @param userId
     * @return itemIds
     */
    public abstract Set<String> getFavoriteItemIds(String userId);

    /**
     * Get the favorite items for a user.
     *
     * @param userId
     * @return items
     */
    public abstract Set<Item> getFavoriteItems(String userId);

    /**
     * Gets keywords based on item id
     *
     * @param itemId
     * @return set of categories
     */
    public abstract Set<String> getKeywords(String itemId);

    /**
     * Search items near a geolocation and a term (optional).
     *
     * @param query
     *            (Nullable)
     * @return list of items
     */
    public abstract List<Item> searchItems(SearchQuery query);

    /**
     * Save item into db.
     *
     * @param item
     */
    public abstract void saveItem(Item item);

    /**
     * Get full name of a user. (This is not needed for main course, just for demo
     * and extension).
     *
     * @param userId
     * @return full name of the user
     */
    public abstract String getFullname(String userId);

    /**
     * Return whether the credential is correct. (This is not needed for main
     * course, just for demo and extension)
     *
     * @param userId
     * @param password
     * @return boolean
     */
    public abstract boolean verifyLogin(String userId, String password);

    public abstract boolean registerUser(String userId, String password, String firstname, String lastname);
}

