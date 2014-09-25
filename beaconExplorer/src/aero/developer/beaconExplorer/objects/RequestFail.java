package aero.developer.beaconExplorer.objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RequestFail implements Serializable {
	int status;
	String statusText;
	String responseText;
	boolean failed = true;
	boolean noResponse = false;

	public RequestFail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestFail(int status, String statusText, String responseText,
			boolean success, boolean noResponse) {
		super();
		this.status = status;
		this.statusText = statusText;
		this.responseText = responseText;
		this.failed = success;
		this.noResponse = noResponse;
	}

	public boolean isFail() {
		return failed;
	}

	public void setFail(boolean failed) {
		this.failed = failed;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public boolean isNoResponse() {
		return noResponse;
	}

	public void setNoResponse(boolean noResponse) {
		this.noResponse = noResponse;
	}
}
