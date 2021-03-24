package com.accenture.lkm.business.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class DateRangeBean {

	@NotNull(message = "From date is required")
	@DateTimeFormat(pattern = "dd-MMM-yyyy")
	private Date fromDate;

	@NotNull(message = "To date is required")
	@DateTimeFormat(pattern = "dd-MMM-yyyy")
	private Date toDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
