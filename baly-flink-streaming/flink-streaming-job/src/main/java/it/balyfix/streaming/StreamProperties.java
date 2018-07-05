package it.balyfix.streaming;

import it.balyfix.streaming.sink.InfluxDBConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;



@ConfigurationProperties("it.balyfix.streaming")
public class StreamProperties
{

    @Value("${url:http://localhost:8086}")
    private String url;

    @Value("${username:root}")
    private String username;

    @Value("${password:root}")
    private String password;

    @Value("${database:db_flink_test}")
    private String database;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDatabase()
    {
        return database;
    }

    public void setDatabase(String database)
    {
        this.database = database;
    }



    public InfluxDBConfig retrieveDbConfig()
    {

        return InfluxDBConfig.builder(getUrl(), getUsername(), getPassword(), getDatabase())
        .batchActions(1000)
        .flushDuration(100, TimeUnit.MILLISECONDS)
        .enableGzip(true)
        .build();
    }

}
