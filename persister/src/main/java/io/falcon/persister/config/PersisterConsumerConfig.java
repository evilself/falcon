package io.falcon.persister.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class PersisterConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //factory.setConcurrency(15);
        factory.getContainerProperties().setConsumerTaskExecutor(execC());
//        factory.getContainerProperties().setListenerTaskExecutor(execL());
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    public AsyncListenableTaskExecutor execC() {
        ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
        tpte.setCorePoolSize(15);
        return tpte;
    }

    @Bean
    public AsyncListenableTaskExecutor execL() {
        ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
        tpte.setCorePoolSize(15);
        return tpte;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

//
//
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "kafka:9092");
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                "first");
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//
//
//    public ConsumerFactory<String, String> consumerFactory2() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "kafka:9092");
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                "foo");
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//
//
//    @Bean
//    @Qualifier
//    public ConcurrentKafkaListenerContainerFactory<String, String>
//    kafkaListenerContainerFactory1() {
//
//        ConcurrentKafkaListenerContainerFactory<String, String> factory
//                = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(15);
//        factory.getContainerProperties().setConsumerTaskExecutor(execC());
//        //factory.getContainerProperties().setPollTimeout(3000);
//        return factory;
//    }
//
//    @Bean
//    @Qualifier
//    public ConcurrentKafkaListenerContainerFactory<String, String>
//    kafkaListenerContainerFactory2() {
//
//        ConcurrentKafkaListenerContainerFactory<String, String> factory
//                = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory2());
//        factory.setConcurrency(15);
//        factory.getContainerProperties().setConsumerTaskExecutor(execC());
//        //factory.getContainerProperties().setPollTimeout(3000);
//        return factory;
//    }
//
//    @Bean
//    public AsyncListenableTaskExecutor execC() {
//        ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
//        tpte.setCorePoolSize(15);
//        return tpte;
//    }
//
//    @Bean
//    public AsyncListenableTaskExecutor execL() {
//        ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
//        tpte.setCorePoolSize(15);
//        return tpte;
//    }
}