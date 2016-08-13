package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DnsPaginatedListResponse<T> extends DnsListResponse<T> {

    // TODO: Build in methods for automated page-turning.

    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public static class Pagination {

        @Expose
        @SerializedName("current_page")
        private int currentPage;

        @Expose
        @SerializedName("per_page")
        private int entriesPerPage;

        @Expose
        @SerializedName("total_entries")
        private int totalEntries;

        @Expose
        @SerializedName("total_pages")
        private int totalPages;

        public int getCurrentPage() {
            return currentPage;
        }

        public int getEntriesPerPage() {
            return entriesPerPage;
        }

        public int getTotalEntries() {
            return totalEntries;
        }

        public int getTotalPages() {
            return totalPages;
        }
    }
}
