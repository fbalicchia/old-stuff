package it.balyfix.streaming;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(StreamProperties.class)
public class BuilderFlinkStreamingJob
{

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(BuilderFlinkStreamingJob.class).run(args).getBean(FlinkStreamJob.class).doRun();
    }

}