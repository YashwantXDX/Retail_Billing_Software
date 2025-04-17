package in.yashwant.billingsoftware.service.impl;

import in.yashwant.billingsoftware.entity.CategoryEntity;
import in.yashwant.billingsoftware.io.CategoryRequest;
import in.yashwant.billingsoftware.io.CategoryResponse;
import in.yashwant.billingsoftware.repository.CategoryRepository;
import in.yashwant.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse add(CategoryRequest request) {

        CategoryEntity newCategory = convertToEntity(request);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);


    }

    @Override
    public List<CategoryResponse> read() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());

    }

    @Override
    public void delete(String categoryId) {

        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found" + categoryId));

        categoryRepository.delete(existingCategory);

    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {

        return CategoryResponse.builder()
                .categoryId((newCategory.getCategoryId()))
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();

    }

    private CategoryEntity convertToEntity(CategoryRequest request) {

       return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();

    }
}
