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
package org.roqmessaging.management.launcher;

import java.io.File;

import org.roqmessaging.management.HostConfigManager;
import org.roqmessaging.management.launcher.hook.ShutDownHook;

/**
 * Class HostConfigManagerLauncher 
 * <p> Description: Launcher for {@linkplain HostConfigManager}
 * Launched by  java -Djava.library.path=/usr/local/lib -cp roq-management-1.0-SNAPSHOT-jar-with-dependencies.jar org.roqmessaging.management.launcher.HostConfigManagerLauncher <global config IP>
 * 
 * @author sskhiri
 */
public class HostConfigManagerLauncher {
	
	/**
	 * @param args(1) the host local manager property file
	 */
	public static void main(String[] args) {
		System.out.println("Starting the local host configuration manager with "+args.length);
		System.out.println("Starting the  global configuration manager");
		HostConfigManager hostManager = null;
		if(args.length ==0) {
			hostManager  = new HostConfigManager("HCM.properties");
		}
		if(args.length ==1) {
			File file = new File(args[0]);
			if(file.exists())hostManager  = new HostConfigManager(args[0]);
			else{
				System.out.println(" File does not exist...");
				System.exit(0);
			}
		}
		ShutDownHook hook = new ShutDownHook(hostManager.getShutDownMonitor());
		Runtime.getRuntime().addShutdownHook(hook);
		//Start
		Thread configThread = new Thread(hostManager);
		configThread.start();
		try {
			while (true) {
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

