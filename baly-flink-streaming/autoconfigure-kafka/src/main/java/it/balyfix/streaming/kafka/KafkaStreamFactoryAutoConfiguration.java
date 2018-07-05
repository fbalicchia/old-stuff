package it.balyfix.streaming.kafka;

import it.balyfix.streaming.commons.StreamFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;


@Configuration
@EnableConfigurationProperties(KafkaStreamFactoryProperties.class)
@Conditional(KafkaStreamFactoryAutoConfiguration.KafkaServersSetCondition.class)
public class KafkaStreamFactoryAutoConfiguration
{

    @Bean
    StreamFactory kafkaStream(KafkaStreamFactoryProperties properties)
    {
        return properties.build();
    }

    static final class KafkaServersSetCondition extends SpringBootCondition
    {

        static final String BOOTSTRAP = "it.balyfix.streaming.kafka.bootstrap-servers";


        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata a)
        {

          //  String bootstrap = context.getEnvironment().getProperty(BOOTSTRAP);
//            return (bootstrap == null || bootstrap.isEmpty()) && (connect == null || connect.isEmpty()) ?
//                ConditionOutcome.noMatch("neither " + BOOTSTRAP + " nor " + CONNECT + " are set") :
//                ConditionOutcome.match();

            return  ConditionOutcome.match();
        }
    }
}
