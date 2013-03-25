#!/bin/sh

#
 # Copyright 2013 EURANOVA
 # Licensed under the Apache License, Version 2.0 (the "License");
 # you may not use this file except in compliance with the License.
 # You may obtain a copy of the License at
#
 # http://www.apache.org/licenses/LICENSE-2.0
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS,
 # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 # See the License for the specific language governing permissions and
 # limitations under the License.
 # 
 # @author Cyrille Duverne
#

nohup java -Djava.library.path=/usr/local/lib -Dlog4j.configuration=file:/var/lib/RoQ/RoQ/roq/config/log4j.properties -cp /var/lib/RoQ/RoQ/roq/lib/roq-management-1.0-SNAPSHOT-jar-with-dependencies.jar org.roqmessaging.management.launcher.GlobalConfigurationLauncher /var/lib/RoQ/RoQ/roq/config/GCM.properties & >> /var/log/roq.log

