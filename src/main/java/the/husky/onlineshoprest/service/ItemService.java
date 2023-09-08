package the.husky.onlineshoprest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import the.husky.onlineshoprest.entity.ItemEntity;
import the.husky.onlineshoprest.exception.item.ItemException;
import the.husky.onlineshoprest.exception.item.ItemNotFoundException;
import the.husky.onlineshoprest.model.Item;
import the.husky.onlineshoprest.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getAllItems() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        return itemEntities.stream()
                .map(Item::toModel)
                .toList();
    }

    public Item getItemById(Long id) {
        ItemEntity itemEntity = itemRepository.findById(id).orElse(null);
        if (itemEntity == null) {
            String errorMessage = String.format("Item with id: %s not found", id);
            log.error("Error during getting item by id: {}", errorMessage);
            throw new ItemNotFoundException(errorMessage);
        }
        return Item.toModel(itemEntity);
    }

    public Item apendItem(ItemEntity itemEntity) {
        try {
            ItemEntity item = itemRepository.save(itemEntity);
            return Item.toModel(item);
        } catch (Exception e) {
            String errorMessage = "Error during adding item";
            log.error("Error during adding item: {}", errorMessage);
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public Item editItem(long id, ItemEntity itemEntity) {
        Optional<ItemEntity> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            ItemEntity currentItem = itemOptional.get();
            currentItem.setTitle(itemEntity.getTitle());
            currentItem.setDescription(itemEntity.getDescription());
            currentItem.setPrice(itemEntity.getPrice());
            currentItem.setWeight(itemEntity.getWeight());
            itemRepository.save(currentItem);
            return Item.toModel(currentItem);
        }
        String errorMessage = String.format("Item with id: %s not found", id);
        log.error("Error during editing item: {}", errorMessage);
        throw new ItemNotFoundException(errorMessage);
    }

    public Item deleteItem(long id) {
        Optional<ItemEntity> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            ItemEntity currentItem = itemOptional.get();
            itemRepository.delete(currentItem);
            return Item.toModel(currentItem);
        }
        String errorMessage = String.format("Item with id: %s not found", id);
        log.error("Error during deleting item: {}", errorMessage);
        throw new ItemNotFoundException(errorMessage);
    }

    public void deleteAllItems() {
        try {
            itemRepository.deleteAll();
        } catch (Exception e) {
            String errorMessage = "Error during deleting all items";
            log.error("Error during deleting all items: {}", errorMessage);
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public List<Item> getItemsByTitle(String title) {
        Optional<List<ItemEntity>> itemEntities = itemRepository.findAllByTitle(title);
        if (itemEntities.isPresent()) {
            return itemEntities.get().stream()
                    .map(Item::toModel)
                    .toList();
        }
        String errorMessage = String.format("Items with title: %s not found", title);
        log.error("Error during getting items by title: {}", errorMessage);
        throw new ItemNotFoundException(errorMessage);
    }

    public List<Item> getItemsByPrice(double price) {
        Optional<List<ItemEntity>> itemEntities = itemRepository.findAllByPrice(price);
        if (itemEntities.isPresent()) {
            return itemEntities.get().stream()
                    .map(Item::toModel)
                    .toList();
        }
        String errorMessage = String.format("Items with price: %s not found", price);
        throw new ItemNotFoundException(errorMessage);
    }

    public List<Item> getItemsByWeight(double weight) {
        Optional<List<ItemEntity>> itemEntities = itemRepository.findAllByWeight(weight);
        if (itemEntities.isPresent()) {
            return itemEntities.get().stream()
                    .map(Item::toModel)
                    .toList();
        }
        String errorMessage = String.format("Items with weight: %s not found", weight);
        throw new ItemNotFoundException(errorMessage);
    }
}
