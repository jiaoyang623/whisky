package org.daniel.jsp.beans;

public class LiveSourceBean {
	private long id;
	private String url;
	private long time;
	private int quality;

	public long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public long getTime() {
		return time;
	}

	public int getQuality() {
		return quality;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

}
