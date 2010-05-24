/*
 * Copyright 2009 The Microlog project @sourceforge.net
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.microlog4android.appender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.util.Log;

import com.google.code.microlog4android.Level;



/**
 * The <code>DatagramAppender</code> uses a <code>DatagramSocket</code> to send
 * <code>Datagram</code> to a server. This can be used on Android or in a
 * Java SE environment.
 * 
 * @author Johan Karlsson
 * 
 */
public class DatagramAppender extends AbstractAppender {
	private static final String TAG = "Microlog.DatagramAppender";
	
	public static final String DEFAULT_HOST = "127.0.0.1";

	private DatagramSocket datagramSocket;

	private InetAddress address;

	private String host = DEFAULT_HOST;

	private int port;

	private DatagramPacket datagramPacket;

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#open()
	 */
	@Override
	public void open() throws IOException {
		datagramSocket = new DatagramSocket();
		address = InetAddress.getByName(host);
		// We create the datagram packet here, but with dummy data. This is
		// done instead of lazy initialization to have better performance when
		// logging. The datagram is re-used and we set new data each time before
		// logging.
		datagramPacket = new DatagramPacket(new byte[] {}, 0, address, port);
		logOpen = true;
	}

	/**
	 * 
	 * @see com.google.code.microlog4android.appender.AbstractAppender#doLog(java.lang.String,
	 *      java.lang.String, long, com.google.code.microlog4android.Level,
	 *      java.lang.Object, java.lang.Throwable)
	 */
	@Override
	public void doLog(String clientID, String name, long time, Level level,
			Object message, Throwable t) {
		if (logOpen && formatter != null) {
			String logMessage = formatter.format(clientID, name, time, level,
					message, t);
			sendMessage(logMessage);
		}
	}

	/**
	 * Send the specified message as a <code>Datagram</code>.
	 * 
	 * @param message
	 *            the message to send.
	 */
	public void sendMessage(String message) {

		byte[] datagramData = message.getBytes();
		datagramPacket.setData(datagramData);

		try {
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			Log.e(TAG, "Failed to send datagram log " + e);
		}
	}

	/**
	 * No effect.
	 * 
	 * @see com.google.code.microlog4android.appender.AbstractAppender#clear()
	 */
	@Override
	public void clear() {
	}

	/**
	 * @see com.google.code.microlog4android.appender.AbstractAppender#close()
	 */
	@Override
	public void close() throws IOException {
		if (datagramSocket != null) {
			datagramSocket.close();
		}

		logOpen = false;
	}

	/**
	 * @see com.google.code.microlog4android.appender.Appender#getLogSize()
	 */
	public long getLogSize() {
		return SIZE_UNDEFINED;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
