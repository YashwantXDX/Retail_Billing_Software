package in.yashwant.billingsoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.yashwant.billingsoftware.io.CategoryRequest;
import in.yashwant.billingsoftware.io.CategoryResponse;
import in.yashwant.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString, @RequestPart("file")MultipartFile file){

        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest request = null;

        try{
            request = objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.add(request, file);
        }
        catch (JsonProcessingException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occurred while passing the json: " + ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping
    public List<CategoryResponse> fetchCategories(){
        return categoryService.read();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void remove(@PathVariable String categoryId){

        try{
            categoryService.delete((categoryId));
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}
