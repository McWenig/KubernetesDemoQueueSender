package com.accenture.kubernetesdemo.queueapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.kubernetesdemo.queueapp.dao.ActDao;
import com.accenture.kubernetesdemo.queueapp.domain.Act;

@Controller
public class ActController {

	private static Log LOGGER = LogFactory.getLog(ActController.class);

	@Autowired
	private MessageProducer messageProducer;

	/**
	 * Create a new act with an auto-generated id and email and name as passed
	 * values.
	 */
	@RequestMapping(value = "/act/create")
	@ResponseBody
	public String create(String name) {
		try {
			Act act = new Act(name);
			actDao.create(act);
			LOGGER.info("New Act <" + act.toString() + "> created");
			messageProducer.sendMessage(act.getName(), act.getId());
			LOGGER.info("Message sent");
		} catch (Exception ex) {
			LOGGER.error("Error while creating Act <" + name + ">", ex);
			return "Error creating the act: " + ex.toString();
		}
		return "Act succesfully created!";
	}

	/**
	 * Delete the act with the passed id.
	 */
	@RequestMapping(value = "/act/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			Act act = new Act(id);
			actDao.delete(act);
		} catch (Exception ex) {
			return "Error deleting the act: " + ex.toString();
		}
		return "Act succesfully deleted!";
	}

	/**
	 * Update the email and the name for the act indentified by the passed id.
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public String updateName(long id, String name) {
		try {
			Act act = actDao.getById(id);
			act.setName(name);
			actDao.update(act);
		} catch (Exception ex) {
			return "Error updating the act: " + ex.toString();
		}
		return "Act succesfully updated!";
	}

	// Private fields

	// Wire the ActDao used inside this controller.
	@Autowired
	private ActDao actDao;

}