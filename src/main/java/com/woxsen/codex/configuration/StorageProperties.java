package com.woxsen.codex.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
	
	private String memberPhotoImage;

	public String getMemberPhotoImage() {
		return memberPhotoImage;
	}

	public void setMemberPhotoImage(String memberPhotoImage) {
		this.memberPhotoImage = memberPhotoImage;
	}
	
	
}
