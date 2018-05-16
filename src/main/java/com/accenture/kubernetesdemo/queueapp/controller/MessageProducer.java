package com.accenture.kubernetesdemo.queueapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.kubernetesdemo.queueapp.QueueConfiguration;
import com.accenture.kubernetesdemo.queueapp.domain.Protocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MessageProducer {
	
	private static Log LOGGER = LogFactory.getLog(MessageProducer.class);
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@RequestMapping(value = "/message/create")
	@ResponseBody
	public String sendMessage(String content, Long actId) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Protocol protocol = new Protocol(content, actId);
		String json = mapper.writeValueAsString(protocol);
		rabbitTemplate.convertAndSend(QueueConfiguration.topicExchangeName, "demo.strucken", json);
		LOGGER.info("Message <"+json+"> sent");
		return "Message <" + json + "> sent";
	}
}
