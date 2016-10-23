package pl.czakanski.thesis.common.config;

import com.jolbox.bonecp.BoneCPDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

public class DataSourceBuilder {
    
    private Environment env;

    private String idleConnectionTestPeriodInMinutes;
    private String idleMaxAgeInMinutes;
    private String maxConnectionsPerPartition;
    private String minConnectionsPerPartition;
    private String partitionCount;
    private String acquireIncrement;
    private String statementsCacheSize;

    public DataSourceBuilder(Environment env) {
        this.env = env;
    }

    public DataSourceBuilder idleConnectionTestPeriodInMinutes(String idleConnectionTestPeriodInMinutes) {
        this.idleConnectionTestPeriodInMinutes = idleConnectionTestPeriodInMinutes;
        return this;
    }

    public DataSourceBuilder idleMaxAgeInMinutes(String idleMaxAgeInMinutes) {
        this.idleMaxAgeInMinutes = idleMaxAgeInMinutes;
        return this;
    }

    public DataSourceBuilder maxConnectionsPerPartition(String maxConnectionsPerPartition) {
        this.maxConnectionsPerPartition = maxConnectionsPerPartition;
        return this;
    }

    public DataSourceBuilder minConnectionsPerPartition(String minConnectionsPerPartition) {
        this.minConnectionsPerPartition = minConnectionsPerPartition;
        return this;
    }

    public DataSourceBuilder partitionCount(String partitionCount) {
        this.partitionCount = partitionCount;
        return this;
    }

    public DataSourceBuilder statementsCacheSize(String statementsCacheSize) {
        this.statementsCacheSize = statementsCacheSize;
        return this;
    }

    public DataSourceBuilder acquireIncrement(String acquireIncrement) {
        this.acquireIncrement = acquireIncrement;
        return this;
    }

    public BoneCPDataSource build() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(SQLServerDriver.class.getName());
        dataSource.setJdbcUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        dataSource.setIdleConnectionTestPeriodInMinutes(Long.parseLong(env.getProperty("idleConnectionTestPeriodInMinutes", idleConnectionTestPeriodInMinutes)));
        dataSource.setIdleMaxAgeInMinutes(Long.parseLong(env.getProperty("idleMaxAgeInMinutes", idleMaxAgeInMinutes)));
        dataSource.setMaxConnectionsPerPartition(Integer.parseInt(env.getProperty("maxConnectionsPerPartition", maxConnectionsPerPartition)));
        dataSource.setMinConnectionsPerPartition(Integer.parseInt(env.getProperty("minConnectionsPerPartition", minConnectionsPerPartition)));
        dataSource.setPartitionCount(Integer.parseInt(env.getProperty("partitionCount", partitionCount)));
        dataSource.setAcquireIncrement(Integer.parseInt(env.getProperty("acquireIncrement", acquireIncrement)));
        dataSource.setStatementsCacheSize(Integer.parseInt(env.getProperty("statementsCacheSize", statementsCacheSize)));
        dataSource.setLogStatementsEnabled(true);
        return dataSource;
    }
}

