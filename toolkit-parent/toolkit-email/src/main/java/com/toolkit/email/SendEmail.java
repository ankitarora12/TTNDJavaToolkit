package com.toolkit.email;

import java.util.List;

import javax.activation.DataSource;

/**
 * 
 * @author Nidhi
 *
 */
public interface SendEmail {

	public void sendSimpleMail(String[] toMailAddress, String subject, String body);

	public void sendHTMLMail(String[] toMailAddress, String subject, String body);

	void sendWithAttachment(String[] toMailAddress, String subject, String body, List<DataSource> file);
}
