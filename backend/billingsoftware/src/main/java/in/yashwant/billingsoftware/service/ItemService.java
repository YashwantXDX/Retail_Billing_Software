package in.yashwant.billingsoftware.service;

import in.yashwant.billingsoftware.io.ItemRequest;
import in.yashwant.billingsoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponse add(ItemRequest request, MultipartFile file);

    List<ItemResponse> fetchItems();

    void deleteItem(String itemId);

}
