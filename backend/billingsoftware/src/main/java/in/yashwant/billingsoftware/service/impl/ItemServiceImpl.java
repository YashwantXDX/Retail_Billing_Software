package in.yashwant.billingsoftware.service.impl;

import in.yashwant.billingsoftware.entity.CategoryEntity;
import in.yashwant.billingsoftware.entity.ItemEntity;
import in.yashwant.billingsoftware.io.ItemRequest;
import in.yashwant.billingsoftware.io.ItemResponse;
import in.yashwant.billingsoftware.repository.CategoryRepository;
import in.yashwant.billingsoftware.repository.ItemRepository;
import in.yashwant.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    private final String BASE_UPLOAD_URL = "http://localhost:8080/api/v1.0/uploads/";
    private final Path UPLOAD_DIR = Paths.get("uploads").toAbsolutePath().normalize();

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {

        try{
            Files.createDirectories(UPLOAD_DIR);
            String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
            Path targetLocation = UPLOAD_DIR.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String imgUrl = BASE_UPLOAD_URL + fileName;

            ItemEntity newItem = convertToEntity(request);
            CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: "+request.getCategoryId()));
            newItem.setCategory(existingCategory);
            newItem.setImgUrl(imgUrl);
            itemRepository.save(newItem);

            return convertToResponse(newItem);


        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not store file: "+e.getMessage());
        }

    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemEntity -> convertToResponse(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {

        ItemEntity existingItem = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found: "+ itemId));

        try{
            String imgUrl = existingItem.getImgUrl();
            if (imgUrl != null && imgUrl.contains("/uploads/")){
                String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
                Path filePath = UPLOAD_DIR.resolve(fileName);
                Files.deleteIfExists(filePath);
                itemRepository.delete(existingItem);
            }
        }
        catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete image");
        }

    }
}
