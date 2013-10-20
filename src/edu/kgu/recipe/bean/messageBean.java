package edu.kgu.recipe.bean;

public class messageBean {
	private String messageID;
	private String warningMsg;
	private String errorMsg;
	private String debugMsg;
	private String fatalMsg;
	
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getDebugMsg() {
		return debugMsg;
	}
	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}
	public String getFatalMsg() {
		return fatalMsg;
	}
	public void setFatalMsg(String fatalMsg) {
		this.fatalMsg = fatalMsg;
	}
}
