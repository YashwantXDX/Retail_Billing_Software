package in.yashwant.billingsoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.yashwant.billingsoftware.io.ItemRequest;
import in.yashwant.billingsoftware.io.ItemResponse;
import in.yashwant.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/items")
    public ItemResponse addItem(@RequestPart("item") String itemString, @RequestPart("file")MultipartFile file){

        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;

        try{
            itemRequest = objectMapper.readValue(itemString, ItemRequest.class);
            return itemService.add(itemRequest, file);
        }
        catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while processing the JSON");
        }

    }

    @GetMapping("/items")
    public List<ItemResponse> readItems(){
        return itemService.fetchItems();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/items/{itemId}")
    public void removeItem(@PathVariable String itemId){
        try{
            itemService.deleteItem(itemId);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }

}
