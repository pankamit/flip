package com.flip.event;

import lombok.Data;

@Data
public class OrderReplyEvent {

	private String reply;
	
	private String replyInfo;
}
