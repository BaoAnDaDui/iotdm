package com.aibaixun.iotdm.controller;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.data.UpdateProductParam;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.data.ProductEntityInfo;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


/**
 * 产品 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{

    private  IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/page")
    public JsonResult<Page<ProductEntity>> pageQueryProducts(@RequestParam Integer page,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String productLabel) throws BaseException {
        checkPage(page,pageSize);
        Page<ProductEntity> productPage = productService.pageQueryByLabel(page, pageSize, productLabel);
        return JsonResult.success(productPage);
    }



    @GetMapping("/{id}")
    public JsonResult<ProductEntityInfo> queryById(@PathVariable String id){
        ProductEntityInfo productInfo = productService.queryProductInfoById(id);
        return JsonResult.success(productInfo);
    }


    @GetMapping("/list")
    public JsonResult<List<ProductEntity>> listQueryProducts (@RequestParam(required = false) Integer limit) {
        List<ProductEntity> productEntities = productService.queryProducts(limit);
        return JsonResult.success(productEntities);
    }


    @PostMapping
    public JsonResult<Boolean> createProduct(@RequestBody @Valid ProductEntity productEntity) throws BaseException {
        String productLabel = productEntity.getProductLabel();
        ProductEntity checkProductEntity = productService.queryProductByLabel(productLabel);
        if (Objects.nonNull(checkProductEntity)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        boolean saveResult = productService.save(productEntity);
        return JsonResult.success(saveResult);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeProduct (@PathVariable String id) throws BaseException {
        ProductEntity pr = productService.getById(id);
        if (Objects.isNull(pr)){
            throw new BaseException("产品不存在无法删除",BaseResultCode.GENERAL_ERROR);
        }
        if (!StringUtils.equals(pr.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("产品必须由创建人删除",BaseResultCode.GENERAL_ERROR);
        }
        boolean remove = productService.removeById(id);
        return JsonResult.success(remove);
    }


    @PutMapping
    public JsonResult<Boolean> updateProduct (@RequestBody @Valid UpdateProductParam updateProductParam) throws BaseException {
        ProductEntity pr = productService.getById(updateProductParam.getId());
        if (Objects.isNull(pr)){
            throw new BaseException("产品不存在无法更改",BaseResultCode.GENERAL_ERROR);
        }
        ProductEntity checkProductEntity = productService.queryProductByLabel(updateProductParam.getProductLabel());
        if (Objects.nonNull(checkProductEntity)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        Boolean aBoolean = productService.updateProduct(updateProductParam.getId(), updateProductParam.getProductLabel(), updateProductParam.getDescription());
        return JsonResult.success(aBoolean);
    }

    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
