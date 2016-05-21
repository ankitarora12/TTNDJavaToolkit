package com.toolkit.email.impl;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import com.toolkit.email.SendEmail;
import com.toolkit.exceptions.InvalidAddressException;

/**
 * This service class is design to implement email.
 * 
 * @author Nidhi
 *
 */

public class SendEmailImpl implements SendEmail {

	private String userName;
	private String host;
	private String port;

	private Properties properties;
	private Session session;
	private String password;
	private String[] bcc;
	private String[] cc;

	/**
	 * @return the bcc
	 */
	public String[] getBcc() {
		return bcc;
	}

	/**
	 * @param bcc
	 *            the bcc to set
	 */
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the cc
	 */
	public String[] getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		if (port == null)
			port = "25";
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		if (properties == null) {
			properties = new Properties();

			properties.setProperty("mail.transport.protocol", "smtp");
			properties.setProperty("mail.host", getHost());
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", getPort());
			properties.put("mail.debug", "true");
			properties.put("mail.smtp.socketFactory.port", getPort());
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			properties.put("mail.smtp.STARTTLS.enable", "true");
			properties.put("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
			System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
		}
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the session
	 * @throws InvalidAddressException
	 */
	public Session getSession() throws InvalidAddressException {

		if (session == null) {
			if (userName != null && password != null) {
				this.session = Session.getDefaultInstance(getProperties(), new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(getUserName(), getPassword());
					}
				});
			} else {
				throw new InvalidAddressException("Invalid UserName and password");
			}
		}
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @return the host
	 */
	public String getHost() {

		if (host == null)
			host = "localhost";
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 */
	@Override
	public void sendSimpleMail(String[] toMailAddress, String subject, String body) {
		try {
			MimeMessage message = new MimeMessage(getSession());

			message.setFrom(new InternetAddress(getUserName()));

			if (toMailAddress != null && toMailAddress.length > 0) {
				Address address[] = (Address[]) getAddress(toMailAddress);
				if (address == null) {
					throw new InvalidAddressException("Invalid send To Addresses");
				} else
					message.addRecipients(RecipientType.TO, address);

			} else {
				throw new InvalidAddressException("Send To Addresses cannot be null");
			}

			if (getBcc() != null && getBcc().length > 0) {
				Address address[] = (Address[]) getAddress(getBcc());

				if (address != null) {
					message.addRecipients(RecipientType.BCC, address);
				}
			}

			if (getCc() != null && getCc().length > 0) {
				Address address[] = (Address[]) getAddress(getCc());

				if (address != null) {
					message.addRecipients(RecipientType.CC, address);
				}
			}

			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);

		} catch (InvalidAddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	@Override
	public void sendHTMLMail(String[] toMailAddress, String subject, String body) {
		try {
			MimeMessage message = new MimeMessage(getSession());

			message.setFrom(new InternetAddress(getUserName()));

			if (toMailAddress != null && toMailAddress.length > 0) {
				Address address[] = (Address[]) getAddress(toMailAddress);

				if (address == null) {
					throw new InvalidAddressException("Invalid send To Addresses");
				} else
					message.addRecipients(RecipientType.TO, address);
			} else {
				throw new InvalidAddressException("Send To Addresses cannot be null");
			}

			if (getBcc() != null && getBcc().length > 0) {
				Address address[] = (Address[]) getAddress(getBcc());

				if (address != null) {
					message.addRecipients(RecipientType.BCC, address);
				}
			}

			if (getCc() != null && getCc().length > 0) {
				Address address[] = (Address[]) getAddress(getCc());

				if (address != null) {
					message.addRecipients(RecipientType.CC, address);
				}
			}
			message.setSubject(subject);
			message.setContent(body, "text/html");
			Transport.send(message);

		} catch (InvalidAddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendWithAttachment(String[] toMailAddress, String subject, String body, List<DataSource> file) {
		try {
			MimeMessage message = new MimeMessage(getSession());

			message.setFrom(new InternetAddress(getUserName()));

			if (toMailAddress != null && toMailAddress.length > 0) {
				Address address[] = (Address[]) getAddress(toMailAddress);

				if (address == null) {
					throw new InvalidAddressException("Invalid send To Addresses");
				} else
					message.addRecipients(RecipientType.TO, address);
			} else {
				throw new InvalidAddressException("Send To Addresses cannot be null");
			}

			if (getBcc() != null && getBcc().length > 0) {
				Address address[] = (Address[]) getAddress(getBcc());

				if (address != null) {
					message.addRecipients(RecipientType.BCC, address);
				}
			}

			if (getCc() != null && getCc().length > 0) {
				Address address[] = (Address[]) getAddress(getCc());

				if (address != null) {
					message.addRecipients(RecipientType.CC, address);
				}
			}
			message.setSubject(subject);
			
			MimeMultipart prepareBody=new MimeMultipart();
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html");
			
			//adding message body
			prepareBody.addBodyPart(messageBodyPart);
			
			// adding files
			MimeBodyPart attachfile = null;
			if(file!=null)
			for (DataSource addFile : file) {
				attachfile=new MimeBodyPart();
				
				DataSource bds = addFile;
				//bds.
				attachfile.setDataHandler(new DataHandler(bds)); 
				attachfile.setFileName(bds.getName()); 
				
				prepareBody.addBodyPart(attachfile);
			}
			
			message.setContent(prepareBody);
			Transport.send(message);

		} catch (InvalidAddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	private InternetAddress[] getAddress(String[] toMailAddress) {
		InternetAddress address[] = null;
		try {
			if (toMailAddress.length == 1) {
				address = new InternetAddress[toMailAddress.length];
				address[0] = new InternetAddress(toMailAddress[0]);

			} else if (toMailAddress.length > 1) {
				int i = 0;
				address = new InternetAddress[toMailAddress.length];
				for (String mailAddress : toMailAddress) {
					address[i] = new InternetAddress(mailAddress);
					i++;
				}

			}
			return address;
		} catch (AddressException e) {
			e.printStackTrace();
			return null;
		}

	}

}
