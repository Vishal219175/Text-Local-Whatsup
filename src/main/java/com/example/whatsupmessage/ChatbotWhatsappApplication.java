package com.example.whatsupmessage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatbotWhatsappApplication {
	public static void main(String[] args) {
// Your main method code goes here
		ChatbotWhatsappApplication app = new ChatbotWhatsappApplication();
		BaseResponse response = app.whatsAppTextSMS("+9284121655"); // Replace with an actual mobile number
		System.out.println(response); // Print the response for testing
	}

	public BaseResponse whatsAppTextSMS(String mobileNumber) {
		String result = "";
		try {
			if (!mobileNumber.startsWith("+")) {
				return new BaseResponse(404, "FAILED", "Invalid mobile number. WhatsApp message not sent");
			}

			String apiEndpoint = WhatsAppConfig.getApiEndPoint();
			String serviceKey = WhatsAppConfig.getServiceKey();
			String appId = WhatsAppConfig.getApiKey();

			// Dynamic button parameters
			String dynamicButtonCaption = "Dynamic Button";
			String dynamicButtonUrl = "https://example.com/dynamic_button";

			String jsonPayload = String.format("{" +
					"\"appid\": \"" + appId + "\"," +
					"\"deliverychannel\": \"whatsapp\"," +
					"\"message\": {" +
					" \"template\": \"1272404983119428\"," +
					" \"parameters\": {" +
					" \"variable1\": {" +
					" \"text\": \"Team\"," + // Static text
					" \"button\": {" +
					" \"type\": \"button\"," +
					" \"caption\": \"%s\"," + // Dynamic button caption
					" \"id\": \"btn1\"," +
					" \"url\": \"%s\"" + // Dynamic button URL
					" }" +
					" }," +
					" \"variable2\": \"123456. Thank you for registering with us.\"" + // Static text
					" }" +
					"}," +
					"\"destination\": [" +
					" {" +
					" \"waid\": [\"%s\"]" +
					" }" +
					"]" +
					"}", dynamicButtonCaption, dynamicButtonUrl, mobileNumber);

			URL url = new URL(apiEndpoint);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("key", serviceKey);
			connection.setDoOutput(true);
			try (OutputStream outputStream = connection.getOutputStream()) {
				outputStream.write(jsonPayload.getBytes("UTF-8"));
			}
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				result = "WhatsApp message sent successfully";
			} else {
				return new BaseResponse(connection.getResponseCode(), "FAILED", "Error: " + connection.getResponseCode());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseResponse(500, "FAILED", "Error: " + e.getMessage());
		}
		return new BaseResponse(200, "SUCCESS", result);
	}
}