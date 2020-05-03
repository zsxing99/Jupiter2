package external.github;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SearchQuery {

    private static final String URL_TEMPLATE = "https://jobs.github.com/positions.json?";
    private static final String DEFAULT_DESCRIPTION = "developer";

    private Set<SearchQueryParam> params;

    public SearchQuery() {
        params = new HashSet<>();
        params.add(new SearchQueryParam("description", DEFAULT_DESCRIPTION));
    }

    public static class SearchQueryParam {
        private String param;

        public void setValue(String value) {
            this.value = value;
        }

        private String value;

        public SearchQueryParam(String param, String value) {
            this.param = param;
            this.value = value;
        }

        public String getParam() {
            return param;
        }

        public String getValue() {
            return value;
        }

        /**
         * two params are said to be equal if they have the same param name
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchQueryParam that = (SearchQueryParam) o;
            return param.equals(that.param);
        }

        @Override
        public int hashCode() {
            return Objects.hash(param);
        }
    }

    /**
     * add param to the params list, override existing param if duplicate
     * @param param
     */
    public void addParam(SearchQueryParam param) {
        if (param.getValue() == null || param.getParam() == null) {
            return;
        }
        try {
            param.setValue(URLEncoder.encode(param.getValue(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        params.remove(param);
        params.add(param);
    }

    public String toUrl() {
        StringBuilder builder = new StringBuilder(URL_TEMPLATE);
        for (SearchQueryParam param : params) {
            builder.append(param.getParam()).append("=").append(param.getValue());
            builder.append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
