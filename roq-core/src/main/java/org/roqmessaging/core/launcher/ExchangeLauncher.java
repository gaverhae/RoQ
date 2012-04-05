/**
 * Copyright 2012 EURANOVA
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.roqmessaging.core.launcher;

import org.apache.log4j.Logger;
import org.roqmessaging.core.Exchange;
import org.roqmessaging.core.utils.RoQUtils;
import org.zeromq.ZMQ;

/**
 * Class ExchangeSimulator
 * <p>
 * Description: Launch an exchange instance with the specified configuration.
 * 
 * @author sskhiri
 */
public class ExchangeLauncher {

	/**
	 * Must contain 4 attributes: 1. The front port <br>
	 * 2. The back port <br>
	 * 3. the address of the monitor to bind tcp:// monitor:monitorPort<br>
	 * 4. The address of the stat monitor to bind tcp://monitor:statport<br>
	 * 
	 * example: 5559 5560 tcp://localhost:5571, tcp://localhost:5800
	 * 
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final Logger logger = Logger.getLogger(ExchangeLauncher.class);
		System.out.println("Starting Exchange with monitor " + args[2] + ", stat= " + args[3]);
		System.out.println("Front  port  " + args[0] + ", Back port= " + args[1]);
		if (args.length != 4) {
			System.out
					.println("The argument should be <int front port> <int back port> < tcp:// monitor:monitorPort>  <tcp:// monitor:statport>");
		}
		try {
			int frontPort = Integer.parseInt(args[0]);
			int backPort = Integer.parseInt(args[1]);
			// Add a communication socket that will notify the monitor that this
			// instance stops
			final ZMQ.Context shutDownContext;
			final ZMQ.Socket shutDownSocket;
			shutDownContext = ZMQ.context(1);
			shutDownSocket = shutDownContext.socket(ZMQ.PUB);
			shutDownSocket.connect(args[2]);
			shutDownSocket.setLinger(3500);
			// Instanciate the exchange
			final Exchange exchange = new Exchange(frontPort, backPort, args[2], args[3]);
			// Add the shutdown hook
			Runtime.getRuntime().addShutdownHook(new Thread() {
				// TODO implement a JMX version
				@Override
				public void run() {
					logger.info("Shutting down Exchange");
					shutDownSocket.send(("6," + RoQUtils.getInstance().getLocalIP()).getBytes(), 0);
					exchange.shutDown();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						logger.error("Error when thread sleeping (shutting down phase))", e);
					}
				}
			});
			// Launch the thread
			Thread t = new Thread(exchange);
			t.start();
		} catch (NumberFormatException e) {
			System.out.println(" The arguments are not valid, must: <int: front port> <int: back port>");
		}
	}

}
