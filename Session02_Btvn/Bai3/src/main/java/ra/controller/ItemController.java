package ra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final List<Item> inventory = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ItemController() {
        inventory.add(new Item(idGenerator.getAndIncrement(), "Mỳ tôm Hảo Hảo", 500, 3500.0));
        inventory.add(new Item(idGenerator.getAndIncrement(), "Sữa tươi Vinamilk", 200, 8000.0));
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> foundItem = inventory.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        return foundItem
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Item> createItem(@RequestBody Item newItem) {
        newItem.setId(idGenerator.getAndIncrement());
        inventory.add(newItem);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Optional<Item> existingItemOpt = inventory.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            Item existingItem = existingItemOpt.get();
            existingItem.setName(updatedItem.getName());
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setPrice(updatedItem.getPrice());
            return new ResponseEntity<>(existingItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Optional<Item> itemToDelete = inventory.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (itemToDelete.isPresent()) {
            inventory.remove(itemToDelete.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}