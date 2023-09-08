package the.husky.onlineshoprest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import the.husky.onlineshoprest.entity.ItemEntity;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@DisplayName("ItemRepository Integration Tests")
class ItemRepositoryITest {
    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.save(createFirstItemEntity());
        itemRepository.save(createSecondItemEntity());
        itemRepository.save(createThirdItemEntity());
    }

    @DisplayName("Test: find all item-entities;")
    @Test
    public void testItemRepositoryFindAllReturnsExpectedItems() {
        List<ItemEntity> items = itemRepository.findAll();

        int expectedSize = 3;
        ItemEntity expectedFirstItem = createFirstItemEntity();
        ItemEntity expectedSecondItem = createSecondItemEntity();
        ItemEntity expectedThirdItem = createThirdItemEntity();

        int actualSize = items.size();
        ItemEntity actualFirstItem = items.get(0);
        ItemEntity actualSecondItem = items.get(1);
        ItemEntity actualThirdItem = items.get(2);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals(expectedSize, actualSize);

        assertEquals(expectedFirstItem, actualFirstItem);
        assertEquals(expectedSecondItem, actualSecondItem);
        assertEquals(expectedThirdItem, actualThirdItem);
    }

    @DisplayName("Test: save new item-entity;")
    @Test
    public void testAppendNewItemCheckItemStorage() {
        List<ItemEntity> items = itemRepository.findAll();

        int expectedSizeBeforeAdded = 3;
        int actualSizeBeforeAdded = items.size();

        ItemEntity expectedNewItem = createFourthItemEntity();
        ItemEntity actualNewItem = itemRepository.save(createFourthItemEntity());

        int expectedSizeAfterAdded = 4;
        int actualSizeAfterAdded = itemRepository.findAll().size();

        assertEquals(expectedSizeBeforeAdded, actualSizeBeforeAdded);
        assertEquals(expectedNewItem, actualNewItem);
        assertEquals(expectedSizeAfterAdded, actualSizeAfterAdded);
    }

    @DisplayName("Test: find item-entity by id;")
    @Test
    public void testFindItemByIdCheckExpectedAndActualObject() {
        ItemEntity expectedFirstItem = createFirstItemEntity();
        ItemEntity expectedSecondItem = createSecondItemEntity();
        ItemEntity expectedThirdItem = createThirdItemEntity();

        ItemEntity actualFirstItem = itemRepository.findById(1L).orElse(null);
        ItemEntity actualSecondItem = itemRepository.findById(2L).orElse(null);
        ItemEntity actualThirdItem = itemRepository.findById(3L).orElse(null);

        assert actualFirstItem != null;
        assertNotNull(actualFirstItem);
        assert actualSecondItem != null;
        assertNotNull(actualSecondItem);
        assert actualThirdItem != null;
        assertNotNull(actualThirdItem);

        assertEquals(expectedFirstItem, actualFirstItem);
        assertEquals(expectedSecondItem, actualSecondItem);
        assertEquals(expectedThirdItem, actualThirdItem);
    }

    @DisplayName("Test: delete item-entity by id;")
    @Test
    public void testDeleteItemCheckSizeOfStorageAfterDelete() {
        int expectedSizeBeforeDelete = 3;
        int actualSizeBeforeDelete = itemRepository.findAll().size();
        assertEquals(expectedSizeBeforeDelete, actualSizeBeforeDelete);

        itemRepository.deleteById(1L);
        int expectedSizeAfterDelete = 2;
        int actualSizeAfterDelete = itemRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete, actualSizeAfterDelete);

        itemRepository.deleteById(2L);
        int expectedSizeAfterDelete2 = 1;
        int actualSizeAfterDelete2 = itemRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete2, actualSizeAfterDelete2);

        itemRepository.deleteById(3L);
        int expectedSizeAfterDelete3 = 0;
        int actualSizeAfterDelete3 = itemRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete3, actualSizeAfterDelete3);

        List<ItemEntity> items = itemRepository.findAll();
        assertTrue(items.isEmpty());
    }

    @DisplayName("Test: delete all user-entities;")
    @Test
    public void testDeleteAllUsersFromDB() {
        int expectedSize = 3;
        int actualSize = itemRepository.findAll().size();
        assertEquals(expectedSize, actualSize);

        itemRepository.deleteAll();
        int expectedSizeAfterDelete = 0;
        int actualSizeAfterDelete = itemRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete, actualSizeAfterDelete);

        List<ItemEntity> users = itemRepository.findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByTitle() {
        List<ItemEntity> expectedFirstItemList = List.of(createFirstItemEntity());
        List<ItemEntity> expectedSecondItemList = List.of(createSecondItemEntity());
        List<ItemEntity> expectedThirdItemList = List.of(createThirdItemEntity());

        List<ItemEntity> actualFirstItemList = itemRepository.findAllByTitle("First Item").orElse(null);
        List<ItemEntity> actualSecondItemList = itemRepository.findAllByTitle("Second Item").orElse(null);
        List<ItemEntity> actualThirdItemList = itemRepository.findAllByTitle("Third Item").orElse(null);

        assert actualFirstItemList != null;
        assertNotNull(actualFirstItemList);
        assert actualSecondItemList != null;
        assertNotNull(actualSecondItemList);
        assert actualThirdItemList != null;
        assertNotNull(actualThirdItemList);

        assertEquals(expectedFirstItemList, actualFirstItemList);
        assertEquals(expectedSecondItemList, actualSecondItemList);
        assertEquals(expectedThirdItemList, actualThirdItemList);
    }

    @Test
    public void testFindByPrice() {
        List<ItemEntity> expectedFirstItemList = List.of(createFirstItemEntity());
        List<ItemEntity> expectedSecondItemList = List.of(createSecondItemEntity());
        List<ItemEntity> expectedThirdItemList = List.of(createThirdItemEntity());

        List<ItemEntity> actualFirstItemList = itemRepository.findAllByPrice(1.0).orElse(null);
        List<ItemEntity> actualSecondItemList = itemRepository.findAllByPrice(2.0).orElse(null);
        List<ItemEntity> actualThirdItemList = itemRepository.findAllByPrice(3.0).orElse(null);

        assert actualFirstItemList != null;
        assertNotNull(actualFirstItemList);
        assert actualSecondItemList != null;
        assertNotNull(actualSecondItemList);
        assert actualThirdItemList != null;
        assertNotNull(actualThirdItemList);

        assertEquals(expectedFirstItemList, actualFirstItemList);
        assertEquals(expectedSecondItemList, actualSecondItemList);
        assertEquals(expectedThirdItemList, actualThirdItemList);
    }

    @Test
    public void testFindByWeight() {
        List<ItemEntity> expectedFirstItemList = List.of(createFirstItemEntity());
        List<ItemEntity> expectedSecondItemList = List.of(createSecondItemEntity());
        List<ItemEntity> expectedThirdItemList = List.of(createThirdItemEntity());

        List<ItemEntity> actualFirstItemList = itemRepository.findAllByWeight(1.0).orElse(null);
        List<ItemEntity> actualSecondItemList = itemRepository.findAllByWeight(2.0).orElse(null);
        List<ItemEntity> actualThirdItemList = itemRepository.findAllByWeight(3.0).orElse(null);

        assert actualFirstItemList != null;
        assertNotNull(actualFirstItemList);
        assert actualSecondItemList != null;
        assertNotNull(actualSecondItemList);
        assert actualThirdItemList != null;
        assertNotNull(actualThirdItemList);

        assertEquals(expectedFirstItemList, actualFirstItemList);
        assertEquals(expectedSecondItemList, actualSecondItemList);
        assertEquals(expectedThirdItemList, actualThirdItemList);
    }

    private ItemEntity createFirstItemEntity() {
        return ItemEntity.builder()
                .itemId(1L)
                .title("First Item")
                .description("First Item Description")
                .price(1.0)
                .weight(1.0)
                .build();
    }

    private ItemEntity createSecondItemEntity() {
        return ItemEntity.builder()
                .itemId(2L)
                .title("Second Item")
                .description("Second Item Description")
                .price(2.0)
                .weight(2.0)
                .build();
    }

    private ItemEntity createThirdItemEntity() {
        return ItemEntity.builder()
                .itemId(3L)
                .title("Third Item")
                .description("Third Item Description")
                .price(3.0)
                .weight(3.0)
                .build();
    }

    private ItemEntity createFourthItemEntity() {
        return ItemEntity.builder()
                .itemId(4L)
                .title("Fourth Item")
                .description("Fourth Item Description")
                .price(4.0)
                .weight(4.0)
                .build();
    }
}