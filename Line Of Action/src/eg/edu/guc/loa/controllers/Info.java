package eg.edu.guc.loa.controllers;

import java.util.List;

public class Info {

	private String clientType;
	private String version;
	private String nickname;
	private List<String> subscribers;
	
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<String> getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(List<String> subscribers) {
		this.subscribers = subscribers;
	}
	
	
}
