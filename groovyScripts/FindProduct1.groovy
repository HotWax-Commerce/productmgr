import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.condition.EntityOperator

logInfo("---------------FindProductService called-----------");

def findMethod() {
    condList=[]
    if(parameters.productId){
        cond= EntityCondition.makeCondition("productId",  EntityOperator.EQUALS, parameters.productId)
        condList.add(cond)
    }

    if(parameters.productName){
        cond= EntityCondition.makeCondition("productName",  EntityOperator.EQUALS, parameters.productName)
        condList.add(cond)
    }

    if(parameters.productCategoryId){
        cond= EntityCondition.makeCondition("productCategoryId",  EntityOperator.EQUALS, parameters.productCategoryId)
        condList.add(cond)
    }

    if(parameters.price){
        cond= EntityCondition.makeCondition("price",  EntityOperator.EQUALS, parameters.price)
        condList.add(cond)
    }
    if(parameters.productFeatureId){
        cond= EntityCondition.makeCondition("productFeatureId",  EntityOperator.EQUALS, parameters.productFeatureId)
        condList.add(cond)
    }

    productList= select("productId","productName", "price", "productFeatureId", "productCategoryId","productTypeId")
        .from("FindProductView")
        .where(condList)
        .orderBy("productId", "productFeatureId").distinct()
        .cursorScrollInsensitive()
        .cache(true)
        .queryList()
    logInfo("---------------productList----------- " + productList)
    logInfo("--------------- context----------- " + context)
    logInfo("--------------- parameters----------- " + parameters)
    context.put("productList",productList)
    logInfo("--------------- ListProduct----------- " + context.get("productList"))
    return ["productList": productList]
}
