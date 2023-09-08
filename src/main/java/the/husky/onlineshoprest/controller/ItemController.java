package the.husky.onlineshoprest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the.husky.onlineshoprest.entity.ItemEntity;
import the.husky.onlineshoprest.model.Item;
import the.husky.onlineshoprest.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting all items: %s", e.getMessage());
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody ItemEntity itemEntity) {
        try {
            Item item = itemService.apendItem(itemEntity);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            String errorMessage = String.format("Error during adding item: %s", e.getMessage());
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/find/by/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") long id) {
        try {
            Item item = itemService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting item by id: %s", id);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/filter/by/title/{title}")
    public ResponseEntity<?> getItemsByTitle(@PathVariable("title") String title) {
        try {
            List<Item> items = itemService.getItemsByTitle(title);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting items by title: %s", title);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/filter/by/price/{price}")
    public ResponseEntity<?> getItemsByPrice(@PathVariable("price") double price) {
        try {
            List<Item> items = itemService.getItemsByPrice(price);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting items by price: %s", price);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/filter/by/weight/{weight}")
    public ResponseEntity<?> getItemsByWeight(@PathVariable("weight") double weight) {
        try {
            List<Item> items = itemService.getItemsByWeight(weight);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting items by weight: %s", weight);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editItem(@PathVariable("id") long id, @RequestBody ItemEntity itemEntity) {
        try {
            Item item = itemService.editItem(id, itemEntity);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            String errorMessage = String.format("Error during editing item by id: %s", id);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok("Item deleted");
        } catch (Exception e) {
            String errorMessage = String.format("Error during deleting item by id: %s", id);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllItems() {
        try {
            itemService.deleteAllItems();
            return ResponseEntity.ok("All items deleted");
        } catch (Exception e) {
            String errorMessage = String.format("Error during deleting all items: %s", e.getMessage());
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
