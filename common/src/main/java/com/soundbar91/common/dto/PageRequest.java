package com.soundbar91.common.dto;

import com.soundbar91.common.constant.AppConstants;

/**
 * 페이징 요청 DTO
 */
public class PageRequest {

    private int page;
    private int size;
    private String sort;

    public PageRequest() {
        this.page = AppConstants.DEFAULT_PAGE_NUMBER;
        this.size = AppConstants.DEFAULT_PAGE_SIZE;
    }

    public PageRequest(int page, int size) {
        this.page = Math.max(page, AppConstants.DEFAULT_PAGE_NUMBER);
        this.size = validateSize(size);
    }

    public PageRequest(int page, int size, String sort) {
        this(page, size);
        this.sort = sort;
    }

    private int validateSize(int size) {
        if (size < AppConstants.MIN_PAGE_SIZE) {
            return AppConstants.DEFAULT_PAGE_SIZE;
        }
        if (size > AppConstants.MAX_PAGE_SIZE) {
            return AppConstants.MAX_PAGE_SIZE;
        }
        return size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, AppConstants.DEFAULT_PAGE_NUMBER);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = validateSize(size);
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * offset 계산 (page * size)
     */
    public int getOffset() {
        return page * size;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
               "page=" + page +
               ", size=" + size +
               ", sort='" + sort + '\'' +
               '}';
    }
}
