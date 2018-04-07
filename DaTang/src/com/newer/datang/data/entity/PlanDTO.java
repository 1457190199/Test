package com.newer.datang.data.entity;


public class PlanDTO {
	private Integer taskId;
	private String beginDateBefore;
	private String beginDateAfter;
	private String endDateBefore;
	private String endDateAfter;
	private String isFeedback;
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getBeginDateBefore() {
		return beginDateBefore;
	}
	public void setBeginDateBefore(String beginDateBefore) {
		this.beginDateBefore = beginDateBefore;
	}
	public String getBeginDateAfter() {
		return beginDateAfter;
	}
	public void setBeginDateAfter(String beginDateAfter) {
		this.beginDateAfter = beginDateAfter;
	}
	public String getEndDateBefore() {
		return endDateBefore;
	}
	public void setEndDateBefore(String endDateBefore) {
		this.endDateBefore = endDateBefore;
	}
	public String getEndDateAfter() {
		return endDateAfter;
	}
	public void setEndDateAfter(String endDateAfter) {
		this.endDateAfter = endDateAfter;
	}
	public String getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(String isFeedback) {
		this.isFeedback = isFeedback;
	}
	public PlanDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PlanDTO(Integer taskId, String beginDateBefore, String beginDateAfter, String endDateBefore,
			String endDateAfter, String isFeedback) {
		super();
		this.taskId = taskId;
		this.beginDateBefore = beginDateBefore;
		this.beginDateAfter = beginDateAfter;
		this.endDateBefore = endDateBefore;
		this.endDateAfter = endDateAfter;
		this.isFeedback = isFeedback;
	}
	@Override
	public String toString() {
		return "PlanDTO [taskId=" + taskId + ", beginDateBefore=" + beginDateBefore + ", beginDateAfter="
				+ beginDateAfter + ", endDateBefore=" + endDateBefore + ", endDateAfter=" + endDateAfter
				+ ", isFeedback=" + isFeedback + "]";
	}
}
