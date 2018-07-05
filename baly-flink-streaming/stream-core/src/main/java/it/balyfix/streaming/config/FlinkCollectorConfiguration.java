package it.balyfix.streaming.config;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class FlinkCollectorConfiguration implements InitializingBean
{

    private static final Logger log = LoggerFactory.getLogger(FlinkCollectorConfiguration.class);

    private boolean flinkClusterEnable;

    private String flinkClusterZookeeperAddress;

    private int flinkClusterSessionTimeout;

    private int flinkRetryInterval;

    private int flinkClusterTcpPort;

    private String flinkStreamExecutionEnvironment;

    private int flinkSourceFunctionParallel;

    public boolean isFlinkClusterEnable()
    {
        return flinkClusterEnable;
    }

    public String getFlinkClusterZookeeperAddress()
    {
        return flinkClusterZookeeperAddress;
    }

    public int getFlinkClusterTcpPort()
    {
        return flinkClusterTcpPort;
    }

    public int getFlinkClusterSessionTimeout()
    {
        return flinkClusterSessionTimeout;
    }

    public int getFlinkRetryInterval()
    {
        return flinkRetryInterval;
    }

    public int getFlinkSourceFunctionParallel()
    {
        return flinkSourceFunctionParallel;
    }

    public boolean isLocalforFlinkStreamExecutionEnvironment()
    {
        return "local".equals(flinkStreamExecutionEnvironment) ? true : false;
    }

    private Properties properties;


    protected void readPropertyValues(Properties properties)
    {
        log.info("baly-flink-stream.properties read.");
        this.properties = properties;
        this.flinkClusterEnable = readBoolean(properties, "flink.cluster.enable");
        this.flinkClusterZookeeperAddress = readString(properties, "flink.cluster.zookeeper.address", "");
        this.flinkClusterSessionTimeout = readInt(properties, "flink.cluster.zookeeper.sessiontimeout", -1);
        this.flinkRetryInterval = readInt(properties, "flink.cluster.zookeeper.retry.interval", 60000);
        this.flinkClusterTcpPort = readInt(properties, "flink.cluster.tcp.port", 19994);
        this.flinkStreamExecutionEnvironment = readString(properties, "flink.StreamExecutionEnvironment", "server");
        this.flinkSourceFunctionParallel = readInt(properties, "flink.sourceFunction.Parallel", 1);
    }


    protected static String readString(Properties properties, String propertyName, String defaultValue)
    {
        final String result = properties.getProperty(propertyName, defaultValue);
        log.info("{}={}", propertyName, result);
        return result;
    }

    protected static int readInt(Properties properties, String propertyName, int defaultValue)
    {
        final String value = properties.getProperty(propertyName);
        final int result = NumberUtils.toInt(value, defaultValue);
        log.info("{}={}", propertyName, result);
        return result;
    }

    protected static long readLong(Properties properties, String propertyName, long defaultValue)
    {
        final String value = properties.getProperty(propertyName);
        final long result = NumberUtils.toLong(value, defaultValue);
        log.info("{}={}", propertyName, result);
        return result;
    }


    protected static boolean readBoolean(Properties properties, String propertyName)
    {
        final String value = properties.getProperty(propertyName);
        final boolean result = Boolean.valueOf(value);
        log.info("{}={}", propertyName, result);
        return result;
    }


    @Override
    public void afterPropertiesSet() throws Exception
    {
        log.info("To be implemented");
        //final Properties properties = Objects.requireNonNull(this.properties, "properties must not be null");
        //readPropertyValues(properties);

    }
}
