package co.kr.lotteon.service.product;

import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.entity.product.*;
import co.kr.lotteon.mapper.*;
import co.kr.lotteon.repository.product.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository prodRepo;
    private final ProductMapper mapper;
    private final ProdCate1Repository prodCate1Repository;
    private final ProdCate2Repository prodCate2Repository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductMapper productMapper;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final PointMapper pointMapper;
    private final ModelMapper modelMapper;


    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT LIST
    ////////////////////////////////////////////////////////////////////

    // SELECT PRODUCTS
    public PageResponseDTO selectProductByCate1AndCate2(PageRequestDTO pageRequestDTO) {
        log.info("prodService here...1");
        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        log.info("prodService here...2");
        ///// 현재 페이지의 cate1
        int cate1 = pageRequestDTO.getProdCate1();
        log.info("cate1 : "+ cate1);
        ProdCate1Entity cate1Entity = null;
        ///// 현재 페이지가 전체 상품 리스트가 아닐때만 전체 cate1Entity를 가져온다
        if(!(cate1 == 1)){
            cate1Entity = prodCate1Repository.findById(cate1).orElse(null);
        }
        log.info("prodService here...3");
        ///// 현재 페이지 리스트의 분류 타입
        String type = pageRequestDTO.getType();
        ///// 현재 페이지의 cate2
        int cate2 = pageRequestDTO.getProdCate2();
        log.info("cate2 : "+ cate2);
        ///// ProductEntity 전체를 담을 result 생성
        Page<ProductEntity> result = null;
        log.info("prodService type : "+type);
        String nav = "히트 상품";

        ///// 현재 페이지가 전체 상품 리스트라면
        if (cate1 == 1 && cate2 == 1) {
            switch (type){
                case "hit":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByHitDesc(1, 1, pageable);
                    nav = "히트 상품";
                    break;
                case "recommend":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByScoreDesc(1, 1, pageable);
                    nav = "추천 상품";
                    break;
                case "latest":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByRdateAsc(1, 1, pageable);
                    nav = "최신 상품";
                    break;
                case "hot":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderBySoldDesc(1, 1, pageable);
                    nav = "인기 상품";
                    break;
                case "discount":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByDiscountDesc(1, 1, pageable);
                    nav = "할인 상품";
                    break;
            }
        ///// 현재 상품 페이지가 전체 상품 페이지가 아니라면
        }else if(!(cate1 == 1 && cate2 == 1)){
            switch (type){
                case "default":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEquals(cate1Entity, cate2, 1, 1, pageable);
                    break;
                case "sold":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderBySoldDesc(cate1Entity, cate2, 1, 1, pageable);
                    break;
                case "priceAsc":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByPriceAsc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "priceDesc":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByPriceDesc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "score":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByScoreDesc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "review":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByReviewDesc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "rdate":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByRdateAsc(cate1Entity, cate2,  1, 1, pageable);
                    break;
            }
        }

        log.info("prodService here...4");
        ///// 위에서 담아온 페이지 리스트를 stream으로 ProductDto리스트 생성
        List<ProductDTO> dto = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                .toList();
        ///// 불러온 ProductDto의 총 갯수
        int totalElement = (int) result.getTotalElements();
        log.info("prodService here...5");
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dto)
                .total(totalElement)
                .build();
    }


    // PRODUCT LIST NAV CONTROL
    public ProdCate2DTO selectAllProdCateByCate2(int cate1, int cate2){
        ProdCate1Entity cate1Entity = prodCate1Repository.findById(cate1).orElse(null);
        return prodCate2Repository.findByCate1AndCate2(cate1Entity, cate2).toDTO();
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT VIEW
    ////////////////////////////////////////////////////////////////////

    // SELECT PRODUCT
    public ProductDTO selectProductByProdNo(int prodNo) {
        ProductEntity entity = prodRepo.findById(prodNo).orElse(null);
        return entity.toDTO();
    }

    // IF NEVER BEFORE HIT INCREASE PRODUCT HIT
    public void addHitCount(int prodNo){
        mapper.increaseProductHitByProdNo(prodNo);
    }


    // SELECT REVIEWS
    public PageResponseDTO selectReviewByProdNo(PageRequestDTO pageRequestDTO){
        ProductEntity prodEntity = prodRepo.findById(pageRequestDTO.getProdNo()).orElse(null);
        pageRequestDTO.setSize(5);
        Pageable pageable = pageRequestDTO.getPageable("revNo");
        Page<ReviewEntity> result = reviewRepository.findByProdNoOrderByRdateAsc(prodEntity, pageable);
        List<ReviewDTO> dto = result.getContent()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ReviewDTO.class))
                                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .reviewList(dto)
                .prodNo(pageRequestDTO.getProdNo())
                .total(totalElement)
                .build();
    }




    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT ORDER
    ////////////////////////////////////////////////////////////////////

    public int insertOrder(OrderDTO order){

        int result = 0;

        String uid = loginStatus();
        log.info("insertOrder here...1");
        order.setOrdUid(uid);
        result = orderMapper.insertOrder(order);
        if(result > 0){
            result = orderMapper.selectLatestOrdNo(uid);
        }
        log.info("insertOrder result: "+result);
        log.info("insertOrder here...2");

        return result;
    }

    public List<ItemDTO> selectProductForOrder(String chk, String uid){

        List<ItemDTO> itemDTOList = new ArrayList<>();
        ItemDTO itemDTO = new ItemDTO();

        String[] chkNos = chk.split("/");

        log.info("chkNos.length: " +chkNos.length);

        if(chkNos[1].equals("cart")){
            for(int i = 2; i < chkNos.length; i++){
                int cartNo = Integer.parseInt(chkNos[i]);
                itemDTO = cartMapper.selectProductsForOrder(cartNo, uid);
                itemDTOList.add(itemDTO);
            }

        }else if(chkNos[1].equals("order")){
            log.info("chkNos[0]: " +chkNos[0]);
            log.info("chkNos[1]: " +chkNos[1]);
            log.info("chkNos[2]: " +chkNos[2]);
            log.info("chkNos[3]: " +chkNos[3]);
            int prodNo = Integer.parseInt(chkNos[2]);
            int count = Integer.parseInt(chkNos[3]);
            log.info("prodNo: " +prodNo);
            log.info("count: " +count);
            itemDTO = productMapper.selectProductForOrder(prodNo);
            itemDTO.setCount(count);
            itemDTOList.add(itemDTO);
        }

        for(int i = 0; i < itemDTOList.size(); i++){

        }

        log.info("selectProductForOrder: "+itemDTOList.toString());
        return itemDTOList;
    }

    public OrderDTO setTotalOrder(Model model, List<ItemDTO> itemDTOList){

        OrderDTO totalOrder = new OrderDTO();

        int totOrgPrice = 0;
        int totDiscount = 0;
        int totDelivery = 0;
        int totPrice = 0;
        int totSavePoint = 0;

        for(int i = 0; i < itemDTOList.size(); i++){
            totOrgPrice += itemDTOList.get(i).getPrice() * itemDTOList.get(i).getCount();
            totDiscount += itemDTOList.get(i).getPrice() - itemDTOList.get(i).discountingPrice();
            totDelivery += itemDTOList.get(i).getDelivery();
            totPrice += itemDTOList.get(i).totalPrice();
            totSavePoint += itemDTOList.get(i).savePoint();
        }

        totalOrder.setOrdPrice(totOrgPrice);
        totalOrder.setOrdDiscount(totDiscount);
        totalOrder.setOrdDelivery(totDelivery);
        totalOrder.setOrdTotPrice(totPrice);
        totalOrder.setSavePoint(totSavePoint);

        return totalOrder;
    }


    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT COMPLETE
    ////////////////////////////////////////////////////////////////////

    public int selectLatestOrdNo(){

        int result = 0;

        String ordUid = loginStatus();

        result = productMapper.selectLatestOrdNo(ordUid);
        log.info(result);

        return result;
    }

    public OrderDTO selectOrder(int ordNo){

        log.info("selectOrder here...1");
        OrderDTO orderDTO = orderRepository.findById(ordNo).orElse(null).toDTO();
        log.info("selectOrder here...2");

        return orderDTO;
    }

    public List<ItemDTO> selectOrderItems(int ordNo){
        log.info("selectOrderItems Service here...1");
        log.info("ordNo: "+ordNo);
        List<ItemDTO> itemDTO = orderItemMapper.selectOrderItemsByOrdNo(ordNo);
        log.info("orderItemDTO: "+ itemDTO.toString());
        log.info("selectOrderItems Service here...4");
        return itemDTO;
    }

    public int insertOrderItems(List<ItemDTO> itemDTOS, int ordNo){

        int result = 0;
        String uid = loginStatus();
        log.info("insertOrderItems Service here...1");
        log.info(itemDTOS.toString());
        for(ItemDTO itemDTO : itemDTOS){
            int prodNo = itemDTO.getProdNo();
            ProductDTO productDTO = prodRepo.findById(prodNo).orElse(null).toDTO();
            log.info(productDTO.toString());
            ItemDTO item = new ItemDTO();
            item.setProdNo(itemDTO.getProdNo());
            item.setProdCate1(productDTO.getProdCate1().getCate1());
            item.setProdCate2(productDTO.getProdCate2());
            item.setOrdNo(ordNo);
            item.setCount(itemDTO.getCount());
            item.setPrice(productDTO.getPrice());
            item.setDiscount(productDTO.getDiscount());
            item.setPoint(productDTO.getPoint());
            item.setDelivery(productDTO.getDelivery());
            item.setTotal(itemDTO.getTotal());

            cartMapper.deleteCartProductByProdNoAndUid(uid, itemDTO.getProdNo());
            /*productMapper.minusStock(orderItem.getProdNo(), orderItem.getCount());*/
            result = orderItemMapper.insertOrderItem(item);

        }
        log.info("insertOrderItems Service here...2");

        return result;
    }

    public void insertPoint(int ordNo, int point){
        String ordUid = loginStatus();

        pointMapper.insertPoint(ordUid, ordNo, point);
    }


    public void minusMemberPoint(int usedPoint){
        String uid = loginStatus();
        pointMapper.minusMemberPoint(uid, usedPoint);
    }





    ////////////////////////////////////////////////////////////////////
    ///////////////// CATEGORY
    ////////////////////////////////////////////////////////////////////


    // SELECT ALL PRODUCT CATE 1
    public List<ProdCate1DTO> selectAllProdCate1(){
        List<ProdCate1Entity> entity = prodCate1Repository.findAll();

        List<ProdCate1DTO> dto = new ArrayList<>();

        for(ProdCate1Entity toEntity : entity){
            ProdCate1DTO toDto = toEntity.toDTO();
            dto.add(toDto);
        }
        return dto;
    }


    // SELECT PRODUCT CATE 2 WITH CATE 1
    public List<ProdCate2DTO> selectAllProdCate1AndProdCate2() {
        List<ProdCate2Entity> entity = prodCate2Repository.findAll();

        List<ProdCate2DTO> dto = new ArrayList<>();

        for(ProdCate2Entity toEntity : entity){
            ProdCate2DTO toDto = toEntity.toDTO();
            dto.add(toDto);
        }

        return dto;
    }



    ////////////////////////////////////////////////////////////////////
    ///////////////// ETC
    ////////////////////////////////////////////////////////////////////

    // GET PATH
    public String getPath(Model model, HttpServletRequest request){
        String path = request.getContextPath();
        model.addAttribute("path", path);
        log.info("path : " + path);
        return path;
    }

    // GET LOGIN MEMBER UID
    public String loginStatus() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("username : " + SecurityContextHolder.getContext().getAuthentication().getName());
        return username;
    }
}
