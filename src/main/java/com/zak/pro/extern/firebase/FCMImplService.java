package com.zak.pro.extern.firebase;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Service
public class FCMImplService implements FCMService {

	private static final Logger logger = LoggerFactory.getLogger(FCMImplService.class);

	@Override
	public void sendMessage(Map<String, String> data, PushNotificationRequest request)
			throws InterruptedException, ExecutionException {
		Message message = this.getPreconfiguredMessageWithData(data, request);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(message);
		String response = this.sendAndGetResponse(message);
		FCMImplService.logger
				.info("Sent message with data. Topic: " + request.getTopic() + ", " + response + " msg " + jsonOutput);
	}

	@Override
	public void sendMessageWithoutData(PushNotificationRequest request)
			throws InterruptedException, ExecutionException {
		Message message = this.getPreconfiguredMessageWithoutData(request);
		String response = this.sendAndGetResponse(message);
		FCMImplService.logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
	}

	@Override
	public void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException {
		Message message = this.getPreconfiguredMessageToToken(request);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(message);
		String response = this.sendAndGetResponse(message);
		FCMImplService.logger.info(
				"Sent message to token. Device token: " + request.getToken() + ", " + response + " msg " + jsonOutput);
	}

	private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
		return FirebaseMessaging.getInstance().sendAsync(message).get();
	}

	private AndroidConfig getAndroidConfig(String topic, String imageUrl) {
		return AndroidConfig.builder().setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
				.setPriority(AndroidConfig.Priority.HIGH)
				.setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
						.setImage(imageUrl).setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build())
				.build();
	}

	private ApnsConfig getApnsConfig(String topic) {
		return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
	}

	private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
		return this.getPreconfiguredMessageBuilder(request).setToken(request.getToken()).build();
	}

	private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
		return this.getPreconfiguredMessageBuilder(request).setTopic(request.getTopic()).build();
	}

	private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
		return this.getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken()).build();
	}

	private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
		AndroidConfig androidConfig = this.getAndroidConfig(request.getTopic(), request.getImageUrl());
		ApnsConfig apnsConfig = this.getApnsConfig(request.getTopic());
		return Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
				Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build());
	}
}
