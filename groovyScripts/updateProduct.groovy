import java.sql.Timestamp
import org.apache.ofbiz.base.util.UtilDateTime
import org.apache.ofbiz.entity.GenericValue
import java.sql.Timestamp


def updateProduct(){
    Map<String, Object> existingProduct = dispatcher.runSync("findProductService",context)
    if(existingProduct.productList){
        logInfo("-----ProductFound---------${existingProduct.productList}")
    }

    Delegator delegator = (Delegator) context.delegator

    GenericValue productDetails = delegator.findOne("Product", ["productId": context.prodId], false)
    GenericValue existingPrice = delegator.findByAnd("ProductPrice", ["productId": context.prodId], null, false)?.get(0)

    if (productDetails && existingPrice) {
        if (context.prodName) {
            productDetails.set("productName", context.prodName)
        }

        delegator.store(productDetails)
        Timestamp currentTimestamp = UtilDateTime.nowTimestamp()
        existingPrice.set("thruDate", currentTimestamp)
        delegator.store(existingPrice)

        if (context.prodPrice) {
            GenericValue newPriceEntry = delegator.makeValue("ProductPrice")

            newPriceEntry.set("productId", context.prodId)
            newPriceEntry.set("productPriceTypeId", "DEFAULT_PRICE")
            newPriceEntry.set("productPricePurposeId", "PURCHASE")
            newPriceEntry.set("currencyUomId", "USD")
            newPriceEntry.set("productStoreGroupId", "_NA_")
            newPriceEntry.set("price", context.prodPrice)
            newPriceEntry.set("fromDate", currentTimestamp)
            delegator.create(newPriceEntry)
        }
    }
}

