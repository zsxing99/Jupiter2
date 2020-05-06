package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Set;

public class Item {

    private String itemId;
    private String name;
    private String address;
    private Set<String> keywords;
    private String imageUrl;
    private String url;
    private String description;
    private String company;
    private String companyUrl;

    private Item(ItemBuilder builder) {
        this.itemId = builder.itemId;
        this.name = builder.name;
        this.address = builder.address;
        this.imageUrl = builder.imageUrl;
        this.url = builder.url;
        this.keywords = builder.keywords;
        this.description = builder.description;
        this.companyUrl = builder.companyUrl;
        this.company = builder.company;
    }


    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("item_id", itemId);
        object.put("name", name);
        object.put("address", address);
        object.put("keywords", new JSONArray(keywords));
        object.put("image_url", imageUrl);
        object.put("url", url);
        object.put("company", company);
        object.put("company_url", companyUrl);
        return object;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId.equals(item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public static class ItemBuilder {
        private String itemId;
        private String name;
        private String address;
        private String imageUrl;
        private String url;
        private Set<String> keywords;
        private String description;
        private String company;
        private String companyUrl;

        public ItemBuilder setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ItemBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ItemBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ItemBuilder setKeywords(Set<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

        public ItemBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder setCompany(String company) {
            this.company = company;
            return this;
        }

        public ItemBuilder setCompanyUrl(String companyUrl) {
            this.companyUrl = companyUrl;
            return this;
        }
    }


}
