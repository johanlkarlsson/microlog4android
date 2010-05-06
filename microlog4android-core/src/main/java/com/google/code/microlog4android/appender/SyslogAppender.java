/**
 * 
 */
package com.google.code.microlog4android.appender;

import com.google.code.microlog4android.Level;

/**
 * 
 * @author Johan Karlsson
 *
 */
public class SyslogAppender extends DatagramAppender {
	
	private SyslogMessage syslogMessage = new SyslogMessage();
	
	public SyslogAppender(){
		super.setPort(SyslogMessage.DEFAULT_SYSLOG_PORT);
		
		syslogMessage.setTag(SyslogMessage.DEFAULT_SYSLOG_TAG);
		syslogMessage.setFacility(SyslogMessage.FACILITY_USER_LEVEL_MESSAGE);
		syslogMessage.setSeverity(SyslogMessage.SEVERITY_DEBUG);
	}
	
	/**
	 * Do the logging.
	 * @param level
	 *            the level to use for the logging.
	 * @param message
	 *            the message to log.
	 * @param t
	 *            the exception to log.
	 */
	public void doLog(String clientID, String name, long time, Level level,
			Object message, Throwable t) {
		if (logOpen && formatter != null) {
			sendMessage(syslogMessage.createMessageData(formatter.format(clientID, name, time, level, message, t)));
		}
	}

	/**
	 * Set the facility that is used when sending message.
	 * 
	 * @param facility
	 *            the facility to set
	 * @throws IllegalArgumentException
	 *             if the facility is not a valid one.
	 */
	public void setFacility(byte facility) {
		syslogMessage.setFacility(facility);
	}

	/**
	 * Get the severity at which the message shall be sent.
	 * 
	 * @param severity
	 *            the severity to set
	 * @throws IllegalArgumentException
	 *             if the severity is not a valid severity.
	 */
	public void setSeverity(byte severity) throws IllegalArgumentException {
		syslogMessage.setSeverity(severity);
	}

	/**
	 * Indicates whether the HEADER part of the message. If this is true, the
	 * HEADER part is created.
	 * 
	 * @param header
	 *            the addHeader to set
	 */
	public void setHeader(boolean header) {
		syslogMessage.setHeader(header);
	}

	/**
	 * Set the hostname to use for the HOSTNAME field of the syslog message.
	 * 
	 * @param hostname
	 *            the hostname to set
	 * @throws IllegalArgumentException
	 *             if the <code>hostname</code> is <code>null</code> or the
	 *             length is less than 1
	 */
	public void setHostname(String hostname) throws IllegalArgumentException {
		syslogMessage.setHostname(hostname);
	}

	/**
	 * Set the tag that is used for the TAG field in the MSG part of the
	 * message. The TAG length must not exceed 32 chars.
	 * 
	 * @param tag
	 *            the tag to set
	 * @throws IllegalArgumentException
	 *             if the tag is null or the length is incorrect.
	 */
	public void setTag(String tag) throws IllegalArgumentException {
		syslogMessage.setTag(tag);
	}

}
