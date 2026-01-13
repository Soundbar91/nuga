package com.soundbar91.domain.shop.service;

import com.soundbar91.common.exception.NotFoundException;
import com.soundbar91.domain.shop.entity.Shop;
import com.soundbar91.domain.shop.repository.ShopRepository;
import com.soundbar91.domain.shop.vo.ShopCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상점 서비스
 */
@Service
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /**
     * 상점 생성
     */
    @Transactional
    public Shop createShop(String name, ShopCategory category, String description, String address, String phoneNumber, Long ownerId) {
        Shop shop = new Shop(name, category, description, address, phoneNumber, ownerId);
        return shopRepository.save(shop);
    }

    /**
     * 상점 조회
     */
    public Shop getShopById(Long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("상점을 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 모든 상점 조회
     */
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    /**
     * 소유자별 상점 조회
     */
    public List<Shop> getShopsByOwnerId(Long ownerId) {
        return shopRepository.findByOwnerId(ownerId);
    }

    /**
     * 카테고리별 상점 조회
     */
    public List<Shop> getShopsByCategory(ShopCategory category) {
        return shopRepository.findByCategory(category);
    }

    /**
     * 활성 상점 조회
     */
    public List<Shop> getActiveShops() {
        return shopRepository.findByIsActive(true);
    }

    /**
     * 상점 정보 수정
     */
    @Transactional
    public Shop updateShopInfo(Long id, String name, String description, String address, String phoneNumber) {
        Shop shop = getShopById(id);
        shop.updateInfo(name, description, address, phoneNumber);
        return shop;
    }

    /**
     * 상점 카테고리 변경
     */
    @Transactional
    public Shop updateShopCategory(Long id, ShopCategory category) {
        Shop shop = getShopById(id);
        shop.updateCategory(category);
        return shop;
    }

    /**
     * 상점 활성화
     */
    @Transactional
    public Shop activateShop(Long id) {
        Shop shop = getShopById(id);
        shop.activate();
        return shop;
    }

    /**
     * 상점 비활성화
     */
    @Transactional
    public Shop deactivateShop(Long id) {
        Shop shop = getShopById(id);
        shop.deactivate();
        return shop;
    }

    /**
     * 상점 삭제
     */
    @Transactional
    public void deleteShop(Long id) {
        Shop shop = getShopById(id);
        shopRepository.delete(shop);
    }
}
