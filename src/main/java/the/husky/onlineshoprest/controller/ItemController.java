package the.husky.onlineshoprest.controller;

import jakarta.validation.Valid;
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
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody @Valid ItemEntity itemEntity) {
        Item item = itemService.apendItem(itemEntity);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/find/by/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") long id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/filter/by/title/{title}")
    public ResponseEntity<?> getItemsByTitle(@PathVariable("title") String title) {
        List<Item> items = itemService.getItemsByTitle(title);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/filter/by/price/{price}")
    public ResponseEntity<?> getItemsByPrice(@PathVariable("price") double price) {
        List<Item> items = itemService.getItemsByPrice(price);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/filter/by/weight/{weight}")
    public ResponseEntity<?> getItemsByWeight(@PathVariable("weight") double weight) {
        List<Item> items = itemService.getItemsByWeight(weight);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editItem(@PathVariable("id") long id, @RequestBody @Valid ItemEntity itemEntity) {
        Item item = itemService.editItem(id, itemEntity);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item with id: " + id + " deleted.");
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllItems() {
        itemService.deleteAllItems();
        return ResponseEntity.ok("All items deleted");
    }
}
