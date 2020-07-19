package net.mbs.itemcatalogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class ItemCatalogueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemCatalogueApplication.class, args);
    }

}
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
class Item{
    public Item(String name){
        this.name=name;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
@RepositoryRestResource
interface ItemRepository extends JpaRepository<Item,Long>{

}
@Component
class ItemInitializer implements CommandLineRunner{
    private ItemRepository itemRepository;

    public ItemInitializer(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("lining","Puma","Bad body", "Air Jordan",
                "Nike","Addidas","Reebok").forEach(item->itemRepository.save(new Item(item)));
        itemRepository.findAll().forEach(System.out::println);
    }
}