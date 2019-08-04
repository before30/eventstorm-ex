package cc.before30.home.ex.eventstorm.products.domain;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * ProductService
 *
 * @author before30
 * @since 2019-08-04
 */

@Service
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    public ProductService(@NonNull final ProductRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "eventTopic")
    public void onListener(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        log.info("#### listener : {}", message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProductRequired productRequired = null;

        try {
            productRequired = objectMapper.readValue(message, ProductRequired.class);

            if(productRequired.getType().equals(ProductRequired.class.getSimpleName())){

                List<Product> productList = repository.findByName(productRequired.getProductName());
                Product product = null;
                if(productList != null && productList.size() > 0){
                    product = productList.get(0);
                }

                if(product == null) {
                    product = new Product();
                    product.setName(productRequired.getProductName());
                    product.setPrice(10000);
                    product.setStock(1);
                }

                // product 의 수량을 10개씩 늘린다
                product.setStock(product.getStock() + 10);
                repository.save(product);

            }
        } catch (IOException e) {

        }

    }

}
