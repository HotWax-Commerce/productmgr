import java.sql.Timestamp
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.base.util.UtilDateTime
import org.apache.ofbiz.entity.GenericValue
import java.sql.Timestamp
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.Delegator
import org.apache.ofbiz.entity.condition.*

def createAssoc(){
    Delegator delegator = (Delegator) context.delegator
    GenericValue existingProduct = delegator.findOne("Product", ["productId": context.productId], false)
    GenericValue existingVirtualProduct = delegator.findOne("Product", ["productId": context.virtualProductId], false)

    if(!existingProduct || !existingVirtualProduct){
        logInfo("No such product found ${productId} or ${virtualProductId}")
        return
    }
    logInfo("//////////  Both Products exists  /////")

    Timestamp currentTimeStamp = UtilDateTime.nowTimestamp()
    GenericValue newProductAssoc = delegator.makeValue("ProductAssoc", [
            "productId": context.virtualProductId,
            "productIdTo" : context.productId,
            "productAssocTypeId" : "PRODUCT_VARIANT",
            "fromDate" : currentTimeStamp,
            "thruDate" : null
    ])

    logInfo("-------------New Product Assoc created----------")
    logInfo("${newProductAssoc}")
    Map result = success()
    newProductAssoc.create()
    result.productId= newProductAssoc.productId
    result.productId= newProductAssoc.productIdTo
}

def updateAssoc(){
    logInfo("------------Updating Product Assoc---------------")
    Delegator delegator = (Delegator) context.delegator
    GenericValue existingProduct = delegator.findOne("Product", ["productId": context.productId], false)
    GenericValue existingVirtualProduct = delegator.findOne("Product", ["productId": context.virtualProductId], false)
    GenericValue existingNewVariantProduct = delegator.findOne("Product", ["productId": context.newVariantProductId], false)
    logInfo("----------Product-${existingProduct.productName}-----------")
    logInfo("----------VirtualProduct-${existingVirtualProduct.productName}-----------")
    logInfo("----------newVariantProduct-${existingNewVariantProduct.productName}-----------")

    if(!existingProduct){
        logInfo("----------No such Product Exists-----------")
        return
    }
    if(!existingVirtualProduct){
        logInfo("----------No such Virtual Product Exists-----------")
        return
    }
    if(!existingNewVariantProduct){
        logInfo("----------No such Product Variant Exists-----------")
        return
    }

    def condList=[]
    condList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, context.virtualProductId))
    condList.add(EntityCondition.makeCondition("productIdTo", EntityOperator.EQUALS, context.productId))
    condList.add(EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "PRODUCT_VARIANT"))

    List<GenericValue> existingAssocList = from("ProductAssoc").where(condList).queryList()
    GenericValue validExistingAssoc = EntityUtil.getFirst(EntityUtil.filterByDate(existingAssocList))

    if(!validExistingAssoc){
        logInfo("----------No such Product Association Exists-----------")
        return
    }

    Timestamp currentTimeStamp = UtilDateTime.nowTimestamp()
    validExistingAssoc.set("thruDate", currentTimeStamp)
    delegator.store(validExistingAssoc)
    logInfo(" ")
    logInfo("-----------  Old ProductAssoc Record: ${validExistingAssoc}")
    logInfo(" ")

    GenericValue newAssoc = delegator.makeValue("ProductAssoc", [
            "productId": context.virtualProductId,
            "productIdTo" : context.newVariantProductId,
            "productAssocTypeId" : "PRODUCT_VARIANT",
            "fromDate" : currentTimeStamp,
            "thruDate" : null
    ])

    logInfo("-------------- Product Assoc Updated  ----------")
    logInfo("----------  New ProductAssoc Record: ${newAssoc}")
    newAssoc.create()
    Map result = success()
    result.productId= newAssoc.productId
    result.productId= newAssoc.productIdTo
}