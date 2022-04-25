package com.growing.sgh.domain.item.service;

import com.growing.sgh.common.prop.FileProperties;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemImg;
import com.growing.sgh.domain.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    private final ItemImgRepository itemImgRepository;
    private final FileProperties fileProperties;
    private final FileService fileService;

    public void itemImgRegister(Item item, List<MultipartFile> itemImgList) throws IOException {
        ItemImg itemImg = new ItemImg(item);
        for(int i=0; i<itemImgList.size(); i++){
            if( i == 0)
                itemImg.RepImg();
            else
                itemImg.SubImg();

            saveItemImg(itemImg, itemImgList.get(i));
        }

    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws IOException {
        if(!itemImgFile.isEmpty()){
            ItemImg itemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(itemImg.getImgName())){
                fileService.deleteFile(fileProperties.getItemImgLocation()
                + "/" + itemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = createImgName(itemImgFile, oriImgName);
            String imgUrl = "/images/item/" + imgName;

            itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

    private void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = createImgName(itemImgFile, oriImgName);
            imgUrl = "/images/item/" + imgName;
        }

        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    private String createImgName(MultipartFile itemImgFile, String oriImgName) throws IOException {
        return fileService.uploadFile(fileProperties.getItemImgLocation()
        , oriImgName, itemImgFile.getBytes());
    }

}