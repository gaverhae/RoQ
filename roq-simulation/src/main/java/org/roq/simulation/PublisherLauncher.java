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
package org.roq.simulation;

import org.roqmessaging.core.PubClientLib;

/**
 * Class PublisherLauncher
 * <p> Description: Launch a publisher according to command lines arguments.
 * 
 * @author sskhiri
 */
public class PublisherLauncher {

	/**
	 * String monitor, int rate, int minutes, int payload,
			boolean tstmp
	 */
	public static void main(String[] args){
		PubClientLib pubClient = new PubClientLib(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]), Boolean.parseBoolean(args[4])); //monitor, msg/min, duration, payload
		Thread t = new Thread(pubClient);
		t.start();
	}

}
