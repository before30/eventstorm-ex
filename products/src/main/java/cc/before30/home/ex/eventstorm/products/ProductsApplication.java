package cc.before30.home.ex.eventstorm.products;

import cc.before30.home.ex.eventstorm.products.domain.ProductChanged;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * ProductsApplication
 *
 * @author before30
 * @since 2019-08-04
 */

@SpringBootApplication
public class ProductsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Autowired
    KafkaTemplate template;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        ProductChanged productChanged = new ProductChanged();
        productChanged.setProductId(1L);
        productChanged.setProductName("test");
        productChanged.setProductPrice(100);
        productChanged.setProductStock(100);
        try {
            json = objectMapper.writeValueAsString(productChanged);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        if( json != null ){
            ProducerRecord producerRecord = new ProducerRecord<>("eventTopic", json);
            template.send(producerRecord);
        }

        template.send("eventTopic", "{}");
    }
}
