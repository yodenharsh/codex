package com.woxsen.codex.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
	
	private String memberPhotoImage;
	private String eventImage;

	public String getMemberPhotoImage() {
		return memberPhotoImage;
	}

	public void setMemberPhotoImage(String memberPhotoImage) {
		this.memberPhotoImage = memberPhotoImage;
	}

	public String getEventImage() {
		return eventImage;
	}

	public void setEventImage(String eventImage) {
		this.eventImage = eventImage;
	}
	
	
}
