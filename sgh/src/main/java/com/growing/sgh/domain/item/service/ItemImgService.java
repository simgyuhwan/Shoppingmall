package com.growing.sgh.domain.item.service;

import com.growing.sgh.common.prop.FileProperties;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemImg;
import com.growing.sgh.domain.item.repository.ItemImgRepository;
import com.growing.sgh.exception.item.ItemImgFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    private final ItemImgRepository itemImgRepository;
    private final FileProperties fileProperties;
    private final FileService fileService;

    public void itemImgRegister(Item item, List<MultipartFile> itemImgFiles) throws IOException {
        ItemImg itemImg = new ItemImg(item);
        for(int i=0; i<itemImgFiles.size(); i++){
            if( i == 0)
                itemImg.RepImg();
            else
                itemImg.SubImg();
            saveItemImg(itemImg, itemImgFiles.get(i));
        }
    }

    public List<ItemImg> itemImgUpdate(Item item, List<MultipartFile> itemImgFiles) throws IOException {
        List<ItemImg> itemImgs = itemImgRepository.findAllByItemId(item.getId());
        List<ItemImg> updateItemImgs = new ArrayList<>();

        for(int i=0; i<itemImgFiles.size(); i++) {
            updateItemImgs.add(updateItemImg(item, itemImgs.get(i), itemImgFiles.get(i)));
        }
        return updateItemImgs;
    }

    @Transactional(readOnly = true)
    public List<ItemImg> getItemImgs(Long itemId) {
        return itemImgRepository.findAllByItemId(itemId);
    }

    private ItemImg updateItemImg(Item item, ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        validateItemImgFile(itemImgFile);
        clearPreviousItemImgFile(itemImg);

        if(Objects.isNull(itemImg)){
            itemImg = saveItemImg(item);
        }

        nameExtractionInformationRegistration(itemImg, itemImgFile);
        return itemImg;
    }

    private ItemImg saveItemImg(Item item) {
        return itemImgRepository.save(new ItemImg(item));
    }

    private void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        validateItemImgFile(itemImgFile);
        nameExtractionInformationRegistration(itemImg, itemImgFile);
        itemImgRepository.save(itemImg);
    }

    private void validateItemImgFile(MultipartFile itemImgFile) {
        if(itemImgFile.isEmpty())
            throw new ItemImgFileNotFoundException();
    }

    private void clearPreviousItemImgFile(ItemImg itemImg) {
        if(!StringUtils.isEmpty(itemImg.getImgName())){
            fileService.deleteFile(fileProperties.getItemImgLocation()
            + "/" + itemImg.getImgName());
        }
    }

    private String uploadItemImgFile(MultipartFile itemImgFile, String oriImgName) throws IOException {
        return fileService.uploadFile(fileProperties.getItemImgLocation()
        , oriImgName, itemImgFile.getBytes());
    }

    private void nameExtractionInformationRegistration(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = uploadItemImgFile(itemImgFile, oriImgName);
        String imgUrl = "/images/item/" + imgName;
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
    }
}
