package the.husky.onlineshoprest.dto;

import lombok.Builder;
import lombok.Data;
import the.husky.onlineshoprest.entity.ItemEntity;

@Data
@Builder
public class Item {
    private Long itemId;
    private String title;
    private String description;
    private double price;
    private double weight;

    public static Item toDto(ItemEntity itemEntity) {
        long itemId = itemEntity.getItemId();
        String title = itemEntity.getTitle();
        String description = itemEntity.getDescription();
        double price = itemEntity.getPrice();
        double weight = itemEntity.getWeight();
        return Item.builder()
                .itemId(itemId)
                .title(title)
                .description(description)
                .price(price)
                .weight(weight)
                .build();
    }
}
