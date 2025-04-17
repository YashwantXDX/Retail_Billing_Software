package in.yashwant.billingsoftware.service;

import in.yashwant.billingsoftware.io.CategoryRequest;
import in.yashwant.billingsoftware.io.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse add(CategoryRequest request);

    List<CategoryResponse> read();

    void delete(String categoryId);
}
