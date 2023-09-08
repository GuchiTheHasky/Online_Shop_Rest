package the.husky.onlineshoprest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the.husky.onlineshoprest.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<List<ItemEntity>> findAllByTitle(String title);

    Optional<List<ItemEntity>> findAllByPrice(double price);

    Optional<List<ItemEntity>> findAllByWeight(double weight);
}
