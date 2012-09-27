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
package org.roqmessaging.management.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.roqmessaging.management.config.scaling.HostScalingRule;
import org.roqmessaging.management.config.scaling.IAutoScalingRule;
import org.roqmessaging.management.config.scaling.LogicalQScalingRule;
import org.roqmessaging.management.config.scaling.XchangeScalingRule;

/**
 * Class AutoScalingRuleStorageManager
 * <p> Description: Responsible for storing/reading the autoscaling configurations. The logic is 
 * encapsulate in this manager.
 * 
 * @author Sabri Skhiri
 */
public class AutoScalingRuleStorageManager {
	private Logger logger = Logger.getLogger(AutoScalingRuleStorageManager.class);
	
	/**
	 * @return all the exchange auto scaling rules.
	 * @throws SQLException  in case of SQL error
	 */
	public List<IAutoScalingRule> getAllExchangeScalingRule(Statement statement) throws SQLException{
		List<IAutoScalingRule> result = new ArrayList<IAutoScalingRule>();
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		ResultSet rs = statement.executeQuery("select rule_id, Throughput, Time_Spend" +
		" from AS_Xchange_Rules;");
		while (rs.next()) {
			IAutoScalingRule rule = new XchangeScalingRule(rs.getInt("Throughput"), rs.getFloat("Time_Spend"));
			rule.setID(rs.getInt("rule_id"));
			logger.debug("Reading rule: "+ rule.toString());
			result.add(rule);
		}
		statement.close();
		return result;
	}
	
	/**
	 * @param statement the SQL statement from the DB connexion
	 * @param ruleID the rule ID in DB
	 * @return  the exchange auto scaling rules.
	 * @throws SQLException  in case of SQL error
	 */
	public XchangeScalingRule getExchangeScalingRule(Statement statement, int ruleID) throws SQLException{
		logger.debug("Reading  Xchange Scaling rule with ID ="+ ruleID);
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		ResultSet rs = statement.executeQuery("select rule_id, Throughput, Time_Spend" +
		" from AS_Xchange_Rules where rule_id="+ruleID+";");
		if (rs.next()) {
			XchangeScalingRule rule = new XchangeScalingRule(rs.getInt("Throughput"), rs.getFloat("Time_Spend"));
			rule.setID(rs.getInt("rule_id"));
			logger.debug("Reading rule: "+ rule.toString());
			statement.close();
			return rule;
		}
		logger.debug("No rule were found with this ID");
		return null;
	}
	
	/**
	 * Get all the scaling rule related to logical queue KPI.
	 * @return all the queue auto scaling rules.
	 * @throws SQLException  in case of SQL error
	 */
	public List<IAutoScalingRule> getAllLogicalQScalingRule(Statement statement) throws SQLException{
		List<IAutoScalingRule> result = new ArrayList<IAutoScalingRule>();
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		ResultSet rs = statement.executeQuery("select rule_id, Producer_per_exchange_limit, Throughput_per_exchange_limit" +
		" from AS_LogicalQueue_Rules;");
		while (rs.next()) {
			IAutoScalingRule rule = new LogicalQScalingRule(rs.getInt("Producer_per_exchange_limit"), rs.getInt("Throughput_per_exchange_limit"));
			rule.setID(rs.getInt("rule_id"));
			logger.debug("Reading rule: "+ rule.toString());
			result.add(rule);
		}
		statement.close();
		return result;
	}
	
	/**
	 * @param statement the SQL statement from the DB connexion
	 * @param ruleID the rule ID in DB
	 * @return  the logical Q  auto scaling rules.
	 * @throws SQLException  in case of SQL error
	 */
	public LogicalQScalingRule getQScalingRule(Statement statement, int ruleID) throws SQLException{
		logger.debug("Reading  Q Scaling rule with ID ="+ ruleID);
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		ResultSet rs = statement.executeQuery("select rule_id, Producer_per_exchange_limit, Throughput_per_exchange_limit" +
				" from AS_LogicalQueue_Rules where rule_id="+ruleID+";");
		if (rs.next()) {
			LogicalQScalingRule rule = new LogicalQScalingRule(rs.getInt("Producer_per_exchange_limit"), rs.getInt("Throughput_per_exchange_limit"));
			rule.setID(rs.getInt("rule_id"));
			logger.debug("Reading rule: "+ rule.toString());
			statement.close();
			return rule;
		}
		logger.debug("No rule were found with this ID");
		return null;
	}
	
	/**
	 * Get all the scaling rule related to host KPI.
	 * @return all the host auto scaling rules.
	 * @throws SQLException  in case of SQL error
	 */
	public List<IAutoScalingRule> getAllHostScalingRule(Statement statement) throws SQLException{
		List<IAutoScalingRule> result = new ArrayList<IAutoScalingRule>();
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		ResultSet rs = statement.executeQuery("select rule_id, CPU_Limit, RAM_Limit" +
		" from AS_Host_Rules;");
		while (rs.next()) {
			IAutoScalingRule rule = new HostScalingRule(rs.getInt("CPU_Limit"), rs.getInt("RAM_Limit"));
			rule.setID(rs.getInt("rule_id"));
			logger.debug("Reading rule: "+ rule.toString());
			result.add(rule);
		}
		statement.close();
		return result;
	}	
	
	/**
	 * @param statement the SQL statement from the DB connexion
	 * @param ruleID the rule ID in DB
	 * @return  the  Host auto scaling rules.
	 * @throws SQLException  in case of SQL error
	 */
	public HostScalingRule getHostScalingRule(Statement statement, int ruleID) throws SQLException{
		logger.debug("Reading  Host Scaling rule with ID ="+ ruleID);
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		ResultSet rs = statement.executeQuery("select rule_id, CPU_Limit, RAM_Limit" +
				" from AS_Host_Rules where rule_id="+ruleID+";");
		if (rs.next()) {
			HostScalingRule rule = new HostScalingRule(rs.getInt("RAM_Limit"), rs.getInt("CPU_Limit"));
			rule.setID(rs.getInt("rule_id"));
			logger.debug("Reading rule: "+ rule.toString());
			statement.close();
			return rule;
		}
		logger.debug("No rule were found with this ID");
		return null;
	}
	
	/**
	 * Add an autoscaling rule in the management DB.
	 * @param statement the SQL statement
	 * @param rule the auto scaling rule
	 */
	public int addExchangeRule(Statement statement, XchangeScalingRule rule) {
		logger.info("Inserting 1 new Exchange Auto scaling  configuration: " + rule.toString());
		try {
			// set timeout to 10 sec.
			statement.setQueryTimeout(10);
			statement.execute("insert into AS_Xchange_Rules  values(null, '" + rule.getEvent_Limit() + "',"
					+ rule.getTime_Limit() + ")");
			int id=  getLastID(statement, "AS_Xchange_Rules");
			statement.close();
			return id;
		} catch (Exception e) {
			logger.error("Error whil inserting new configuration", e);
		}
		return -1;
	}
	
	/**
	 * Return the last iD instered in the table. This method works only in case of thread safe access.
	 * @param statement the statement to execute the query
	 * @param table the table name from wich we need to get the last update
	 * @return the last id inserted.
	 * @throws SQLException 
	 */
	private int getLastID(Statement statement, String table) throws SQLException {
		ResultSet rs = statement.executeQuery("SELECT last_insert_rowid() from "+table+" ;");
		Integer id = null;
		if (rs.next()) {
			id = rs.getInt("last_insert_rowid()");
		}
		return (id == null) ? -1 : id.intValue();
	}

	/**
	 * Remove the specified auto scaling rule.
	 * @param statement the SQL statement from a SQL connection.
	 * @param ruleID the rule identifying the rule to remove.
	 * @throws SQLException  in case of SQL error during the removal
	 */
	public void removeXChangeRule(Statement statement, long ruleID) throws SQLException{
		logger.debug("Deleting the rule "+ ruleID);
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		statement.executeUpdate("DELETE  from AS_Xchange_Rules where rule_id="+ruleID+";");
		statement.close();
	}
	
	/**
	 * Add an autoscaling rule in the management DB. The auto scaling rule is define at the logical Q level.
	 * @param statement the SQL statement
	 * @param rule the auto scaling rule
	 */
	public int addQueueRule(Statement statement, LogicalQScalingRule rule){
		logger.info("Inserting 1 new Queue  Auto scaling  configuration: "+ rule.toString());
		try {
			// set timeout to 10 sec.
			statement.setQueryTimeout(10);
			statement.execute("insert into AS_LogicalQueue_Rules  values(null, '" + rule.getProducerNumber() + "'," + rule.getThrougputNumber() + ")");
			int id=  getLastID(statement, "AS_LogicalQueue_Rules");
			statement.close();
			return id;
		} catch (Exception e) {
			logger.error("Error whil inserting new configuration", e);
		}
		return -1;
	}
	
	/**
	 * Remove the specified auto scaling rule.
	 * @param statement the SQL statement from a SQL connection.
	 * @param ruleID the rule identifying the rule to remove.
	 * @throws SQLException  in case of SQL error during the removal
	 */
	public void removeQRule(Statement statement, long ruleID) throws SQLException{
		logger.debug("Deleting the rule "+ ruleID);
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		statement.executeUpdate("DELETE  from AS_LogicalQueue_Rules where rule_id="+ruleID+";");
		statement.close();
	}
	
	/**
	 * Add an autoscaling rule in the management DB. The auto scaling rule is define at the Physical host level.
	 * @param statement the SQL statement
	 * @param rule the auto scaling rule
	 */
	public int addHostRule(Statement statement, HostScalingRule rule){
		logger.info("Inserting 1 new Physical host Auto scaling  configuration: "+ rule.toString());
		try {
			// set timeout to 10 sec.
			statement.setQueryTimeout(10);
			statement.execute("insert into AS_Host_Rules  values(null, '" + rule.getCPU_Limit() + "'," + rule.getRAM_Limit() + ")");
			int id=  getLastID(statement, "AS_Host_Rules");
			statement.close();
			return id;
		} catch (Exception e) {
			logger.error("Error whil inserting new configuration", e);
		}
		return -1;
	}
	
	/**
	 * Remove the specified auto scaling rule.
	 * @param statement the SQL statement from a SQL connection.
	 * @param ruleID the rule identifying the rule to remove.
	 * @throws SQLException  in case of SQL error during the removal
	 */
	public void removeHostRule(Statement statement, long ruleID) throws SQLException{
		logger.debug("Deleting the rule "+ ruleID);
		// set timeout to 5 sec.
		statement.setQueryTimeout(5);
		statement.executeUpdate("DELETE  from AS_Host_Rules where rule_id="+ruleID+";");
		statement.close();
	}

}
