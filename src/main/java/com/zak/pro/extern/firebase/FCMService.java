package com.zak.pro.extern.firebase;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public interface FCMService {

	void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException;

	void sendMessageWithoutData(PushNotificationRequest request) throws InterruptedException, ExecutionException;

	void sendMessage(Map<String, String> data, PushNotificationRequest request)
			throws InterruptedException, ExecutionException;

}
