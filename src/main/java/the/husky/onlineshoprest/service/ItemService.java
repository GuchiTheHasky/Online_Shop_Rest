package the.husky.onlineshoprest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import the.husky.onlineshoprest.entity.ItemEntity;
import the.husky.onlineshoprest.exception.item.ItemException;
import the.husky.onlineshoprest.exception.item.ItemNotFoundException;
import the.husky.onlineshoprest.model.Item;
import the.husky.onlineshoprest.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
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
        try {
            ItemEntity itemEntity = itemRepository.findById(id).orElse(null);
            assert itemEntity != null;
            return Item.toModel(itemEntity);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting item by id: %s", id);
            throw new ItemNotFoundException(errorMessage, e.getCause());

        }
    }

    public Item apendItem(ItemEntity itemEntity) {
        try {
            ItemEntity item = itemRepository.save(itemEntity);
            return Item.toModel(item);
        } catch (Exception e) {
            String errorMessage = String.format("Error during adding item: %s", itemEntity.getTitle());
            throw new ItemNotFoundException(errorMessage, e.getCause());
        }
    }

    public Item editItem(long id, ItemEntity itemEntity) {
        try {
            Optional<ItemEntity> itemOptional = itemRepository.findById(id);
            if (itemOptional.isPresent()) {
                ItemEntity currentItem = itemOptional.get();
                currentItem.setTitle(itemEntity.getTitle());
                currentItem.setDescription(itemEntity.getDescription());
                currentItem.setPrice(itemEntity.getPrice());
                currentItem.setWeight(itemEntity.getWeight());
                itemRepository.save(currentItem);
                return Item.toModel(currentItem);
            } else {
                String errorMessage = String.format("Item with id: %s not found", id);
                throw new ItemNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during editing item: %s", itemEntity.getTitle());
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public Item deleteItem(long id) {
        try {
            Optional<ItemEntity> itemOptional = itemRepository.findById(id);
            if (itemOptional.isPresent()) {
                ItemEntity currentItem = itemOptional.get();
                itemRepository.delete(currentItem);
                return Item.toModel(currentItem);
            } else {
                String errorMessage = String.format("Item with id: %s not found", id);
                throw new ItemNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during deleting item with id: %s", id);
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public void deleteAllItems() {
        try {
            itemRepository.deleteAll();
        } catch (Exception e) {
            String errorMessage = "Error during deleting all items";
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public List<Item> getItemsByTitle(String title) {
        try {
            Optional<List<ItemEntity>> itemEntities = itemRepository.findAllByTitle(title);
            if (itemEntities.isPresent()) {
                return itemEntities.get().stream()
                        .map(Item::toModel)
                        .toList();
            } else {
                String errorMessage = String.format("Items with title: %s not found", title);
                throw new ItemNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting items by title: %s", title);
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public List<Item> getItemsByPrice(double price) {
        try {
            Optional<List<ItemEntity>> itemEntities = itemRepository.findAllByPrice(price);
            if (itemEntities.isPresent()) {
                return itemEntities.get().stream()
                        .map(Item::toModel)
                        .toList();
            } else {
                String errorMessage = String.format("Items with price: %s not found", price);
                throw new ItemNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting items by price: %s", price);
            throw new ItemException(errorMessage, e.getCause());
        }
    }

    public List<Item> getItemsByWeight(double weight) {
        try {
            Optional<List<ItemEntity>> itemEntities = itemRepository.findAllByWeight(weight);
            if (itemEntities.isPresent()) {
                return itemEntities.get().stream()
                        .map(Item::toModel)
                        .toList();
            } else {
                String errorMessage = String.format("Items with weight: %s not found", weight);
                throw new ItemNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting items by weight: %s", weight);
            throw new ItemException(errorMessage, e.getCause());
        }
    }
}
