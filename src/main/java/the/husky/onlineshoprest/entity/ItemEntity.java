package the.husky.onlineshoprest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@Builder//(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "items")
public class ItemEntity {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @Column(name = "title")
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @DecimalMin(value = "0.1", message = "Price should be greater than 0")
    private double price;

    @Column(name = "weight")
    @DecimalMin(value = "0.1", message = "Weight should be greater than 0")
    private double weight;

}
