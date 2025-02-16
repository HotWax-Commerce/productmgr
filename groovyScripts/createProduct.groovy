import org.apache.ofbiz.entity.util.EntityUtilProperties

import java.sql.Timestamp
import org.apache.ofbiz.base.util.UtilDateTime
import org.apache.ofbiz.entity.GenericValue

def createProduct(){
  Map<String, Object> existingProduct = dispatcher.runSync("findProductService",context)
  if(existingProduct.productList){
    logInfo("-----ProductAlreadyExists---------${existingProduct.productList}")
    return;
  }

  logInfo("-----Product needs to be created-----------")
  logInfo("-----This parameter is passed in the service call - ${parameters}-----------")
  GenericValue newProduct = makeValue("Product", parameters)
  if (!parameters.productId) newProduct.productId = delegator.getNextSeqId("Product")
  else newProduct.productId = newProduct.productId
  Timestamp nowTimestamp = UtilDateTime.nowTimestamp()
  parameters.put("fromDate", nowTimestamp)
  parameters.put("productPriceTypeId","DEFAULT_PRICE")
  parameters.put("productPricePurposeId","PURCHASE")
  parameters.put("currencyUomId","USD")
  parameters.put("productStoreGroupId","_NA_")
  GenericValue newProductPrice = makeValue("ProductPrice", parameters)
  GenericValue newProductCategoryMember = makeValue("ProductCategoryMember", parameters)

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