package com.aibaixun.iotdm.service.impl;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.mapper.ProductModelMapper;
import com.aibaixun.iotdm.service.IModelCommandService;
import com.aibaixun.iotdm.service.IModelPropertyService;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 产品模型 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ProductModelServiceImpl extends ServiceImpl<ProductModelMapper, ProductModelEntity> implements IProductModelService {


    private IModelPropertyService modelPropertyService;


    private IModelCommandService modelCommandService;

    @Override
    public List<ProductModelEntityInfo> queryProductModelInfoByProductId(String productId) {
        return baseMapper.selectProductModelInfoByProductId(productId);
    }

    @Override
    public Boolean updateProductModel(ProductModelEntity productModelEntity) {
        if (Objects.isNull(productModelEntity)){
            return false;
        }
        LambdaUpdateWrapper<ProductModelEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(ProductModelEntity::getId, productModelEntity.getId());
        if (StringUtils.isNotBlank(productModelEntity.getModelLabel())){
            updateWrapper.set(ProductModelEntity::getModelLabel, productModelEntity.getModelLabel());
        }else if (StringUtils.isNotBlank(productModelEntity.getProductId())){
            updateWrapper.set(ProductModelEntity::getProductId, productModelEntity.getProductId());
        }else if (StringUtils.isNotBlank(productModelEntity.getDescription())){
            updateWrapper.set(ProductModelEntity::getDescription, productModelEntity.getDescription());
        }else if(StringUtils.isNotBlank(productModelEntity.getModelType())){
            updateWrapper.set(ProductModelEntity::getModelType, productModelEntity.getModelType());
        }else {
            return false;
        }
        return update(updateWrapper);
    }


    @Override
    public Boolean saveModelProperty(String productId, ModelPropertyEntity modelPropertyEntity) throws BaseException {
        String propertyLabel = modelPropertyEntity.getPropertyLabel();
        String productModelId = modelPropertyEntity.getProductModelId();
        Long countNum = modelPropertyService.countModelPropertyByLabel(propertyLabel, productModelId);
        if (countNum>0){
            throw new BaseException("模型下已经存在同名属性", BaseResultCode.GENERAL_ERROR);
        }
        return modelPropertyService.save(modelPropertyEntity);
    }

    @Override
    public Boolean updateModelProperty(String productId, ModelPropertyEntity modelPropertyEntity) throws BaseException {
        String propertyLabel = modelPropertyEntity.getPropertyLabel();
        String productModelId = modelPropertyEntity.getProductModelId();
        Long countNum = modelPropertyService.countModelPropertyByLabel(propertyLabel, productModelId);
        if (countNum>0){
            throw new BaseException("模型下已经存在同名属性", BaseResultCode.GENERAL_ERROR);
        }
        return modelPropertyService.updateById(modelPropertyEntity);
    }

    @Override
    public Boolean removeModelProperty(String productId, String propertyId) {
        return modelPropertyService.removeById(propertyId);
    }

    @Override
    public Boolean saveModelCommand(String productId, ModelCommandEntity modelCommandEntity) throws BaseException {
        String commandLabel = modelCommandEntity.getCommandLabel();
        String productModelId = modelCommandEntity.getProductModelId();
        Long countNum = modelCommandService.countModelCommandByLabel(productModelId, commandLabel);
        if (countNum>0){
            throw new BaseException("模型下已经存在同名命令", BaseResultCode.GENERAL_ERROR);
        }
        return modelCommandService.save(modelCommandEntity);
    }

    @Override
    public Boolean updateModelCommand(String productId, ModelCommandEntity modelCommandEntity) throws BaseException {
        String commandLabel = modelCommandEntity.getCommandLabel();
        String productModelId = modelCommandEntity.getProductModelId();
        Long countNum = modelCommandService.countModelCommandByLabel(productModelId, commandLabel);
        if (countNum>0){
            throw new BaseException("模型下已经存在同名属性", BaseResultCode.GENERAL_ERROR);
        }
        return modelCommandService.updateById(modelCommandEntity);
    }

    @Override
    public Boolean removeModelCommand(String productId, String commandId) {
        return modelCommandService.removeById(commandId);
    }


    @Override
    public ModelPropertyEntity getModelPropertyByPropertyId(String propertyId) {
        return modelPropertyService.getById(propertyId);
    }

    @Override
    public ModelCommandEntity getModelCommandByPropertyId(String commandId) {
        return modelCommandService.getById(commandId);
    }


    @Override
    public List<ModelPropertyEntity> queryModelPropertyByModelId(String modelId) {
        return modelPropertyService.listQueryByModelId(modelId);
    }

    @Override
    public List<ModelCommandEntity> queryModelCommandByModelId(String modelId) {
        return modelCommandService.listQueryByModelId(modelId);
    }




    @Autowired
    public void setModelPropertyService(IModelPropertyService modelPropertyService) {
        this.modelPropertyService = modelPropertyService;
    }
    @Autowired
    public void setModelCommandService(IModelCommandService modelCommandService) {
        this.modelCommandService = modelCommandService;
    }
}
