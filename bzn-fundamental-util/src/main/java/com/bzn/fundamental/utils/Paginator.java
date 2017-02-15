package com.bzn.fundamental.utils;

public class Paginator {

	private long totalCount = 0;
	private int currentPage = 1;
	private int pageSize = 10;

	public Paginator() {
		this(10);
	}

	public Paginator(int pageSize) {
		this.setPageSize(pageSize);
	}

	public Paginator(int currPage, int pageSize) {
		this.setCurrentPage(currPage);
		this.setPageSize(pageSize);
	}

	public int getOffset() {
		return (totalCount == 0 ? 0 : (((currentPage - 1) * pageSize) + 1) - 1);
	}

	public int getLimit() {
		return (int) (currentPage < this.getTotalPages() ? pageSize
				: (totalCount - ((this.getTotalPages() - 1) * pageSize))); // TODO
	}

	public int getTotalPages() {
		return (int) (((totalCount - 1) / pageSize) + 1);
	}

	public int prev() {
		if (hasPrev()) {
			return this.getCurrentPage() - 1;
		}
		return this.getCurrentPage();
	}

	public int next() {
		if (hasNext()) {
			return this.getCurrentPage() + 1;
		}
		return this.getCurrentPage();
	}

	public boolean isFirst() {
		return this.getCurrentPage() == 1;
	}

	public boolean hasNext() {
		return this.getCurrentPage() < this.getTotalPages();
	}

	public boolean isLast() {
		return this.getCurrentPage() == this.getTotalPages();
	}

	public boolean hasPrev() {
		return this.getCurrentPage() > 1;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
