import org.apache.ofbiz.entity.util.EntityUtilProperties
import java.sql.Timestamp
import org.apache.ofbiz.base.util.UtilDateTime
import org.apache.ofbiz.entity.Delegator
import org.apache.ofbiz.entity.GenericValue

def createProduct(){
  Delegator delegator = (Delegator) context.delegator
  logInfo("   ")
  logInfo("-----Context Obtained from Service Call ---------${context}")
  logInfo("-----Delegator Obtained from context ---------${context.delegator}")
  logInfo("   ")

  Map<String, Object> existingProduct = dispatcher.runSync("findProductService",context)
  if(existingProduct.productList){
    logInfo("-----ProductAlreadyExists---------${existingProduct.productList}")
    return;
  }

  logInfo("-----Product needs to be created-----------")
  logInfo("-----This parameter is passed in the service call - ${parameters}-----------")
  GenericValue newProduct = delegator.makeValue("Product", context)
  if (!parameters.productId) newProduct.productId = delegator.getNextSeqId("Product")
  else newProduct.productId = newProduct.productId
  Timestamp nowTimestamp = UtilDateTime.nowTimestamp()
  parameters.put("fromDate", nowTimestamp)
  parameters.put("productPriceTypeId","DEFAULT_PRICE")
  parameters.put("productPricePurposeId","PURCHASE")
  parameters.put("currencyUomId","USD")
  parameters.put("productStoreGroupId","_NA_")
  GenericValue newProductPrice = delegator.makeValue("ProductPrice", parameters)
  GenericValue newProductCategoryMember = delegator.makeValue("ProductCategoryMember", parameters)

  Map result = success()

  result.productId = newProduct.productId
  newProduct.create()
  logInfo("-----newProduct---------${newProduct}")
  newProductPrice.create()
  logInfo("-----newProductPrice---------${newProductPrice}")
  newProductCategoryMember.create()
  logInfo("-----newProductCategoryMember---------${newProductCategoryMember}")
  return result
}